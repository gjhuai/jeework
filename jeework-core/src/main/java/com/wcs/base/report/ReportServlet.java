package com.wcs.base.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class ReportServlet extends HttpServlet implements Servlet {

	private DataSource ds;

	private Map reportsProps = new HashMap();

	// Class for reading the output that is generated
	// by the Velocity engine; this ouput is sent
	// to the Jasper XML template compiler
	private class TemplateCompiler implements Runnable {

		private PipedInputStream inStream;

		private JasperReport jasperReport;

		public TemplateCompiler(PipedInputStream pipedStream) {
			this.inStream = pipedStream;
		}

		/*
		 * This method should be called after the thread that is executing this
		 * Runnable instance has finished.
		 */
		public JasperReport getJasperReport() {
			return jasperReport;
		}

		public void run() {
			try {
				// If no input stream was provided, return
				if (inStream == null) {
					return;
				}

				jasperReport = JasperCompileManager.compileReport(inStream);
			} catch (Exception io) {
				io.printStackTrace();
				//log it
			}
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			ServletContext context = getServletContext();

			// Set class path for compiling XML templates
			System.setProperty("jasper.reports.compile.class.path", context
					.getRealPath("/WEB-INF/lib/jasperreports-0.6.5.jar")
					+ System.getProperty("path.separator")
					+ context.getRealPath("/WEB-INF/classes/"));

			// Init velocity
			initVelocity(config);
			String files = config.getInitParameter("reports.properties");
			StringTokenizer tokens = new StringTokenizer(files, ";");
			while (tokens.hasMoreTokens()) {
				ReportProperties props = new ReportProperties(tokens
						.nextToken());
				reportsProps.put(props.getReportName(), props);
			}
		} catch (Exception e) {
			throw new ServletException("Error initializing ReportServlet!", e);
		}
	}

	private void initVelocity(ServletConfig config) throws Exception {

		String propsFile = config.getInitParameter("velocity.properties");
		Properties props = new Properties();
		String path = getServletContext().getRealPath(propsFile);
		props.load(new FileInputStream(path));
		Velocity.init(props);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// Populate context
			VelocityContext context = new VelocityContext();
			addDataToContext(context, req, resp);

			// Get template
			Template template = Velocity.getTemplate("BasicReport.vm");
			JasperReport jasperReport = compileTemplate(template, context);

			// Create JasperPrint object using the fillReport() method in
			// JasperFillManager class
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, getParameters(req), getConnection());

			generateHtmlOutput(jasperPrint, req, resp);
		} catch (Exception e) {
			req.setAttribute("exception", e);
			getServletContext().getRequestDispatcher("/jsp/exception.jsp")
					.forward(req, resp);
		}
	}

	private void addDataToContext(VelocityContext context,
			HttpServletRequest req, HttpServletResponse resp) {

		String reportName = req.getParameter("reportName");
		ReportProperties props = (ReportProperties) reportsProps
				.get(reportName);

		String columns[] = req.getParameterValues("columns");
		List columnList = new ArrayList();
		List fieldList = new ArrayList();

		for (int i = 0; i < columns.length; i++) {
			String column = columns[i];
			Column col = props.getColumn(column);
			columnList.add(col);
			String columnExpression = col.getExpression();

			// Get fields from selected columns
			Pattern pattern = Pattern.compile("\\$F\\{[\\d\\w]+\\}");
			Matcher matcher = pattern.matcher(columnExpression);
			while (matcher.find()) {
				int beginIndex = matcher.start() + 3;
				int endIndex = matcher.end() - 1;
				String field = columnExpression.substring(beginIndex, endIndex);
				Field f = props.getField(field);
				if (!fieldList.contains(f)) {
					fieldList.add(f);
				}
			}
		}

		// add title, fields, columns, sql query, header string to
		// VelocityContext
		context.put("title", props.getTitle());
		context.put("fieldList", fieldList);
		context.put("columnList", columnList);
		context.put("sql", props.getSql());
		context.put("headerString", props.getHeader());
	}

	private Map getParameters(HttpServletRequest req) {
		// get corresponfing properties for report
		String reportName = req.getParameter("reportName");
		ReportProperties props = (ReportProperties) reportsProps
				.get(reportName);

		Map parameters = new HashMap();
		parameters.put("BaseDir", new File(getServletContext().getRealPath(
				"/images/")));
		parameters.put("ImageFile", props.getImage());
		return parameters;
	}

	private JasperReport compileTemplate(Template template, Context context)
			throws Exception {

		PipedInputStream inStream = null;
		try {
			BufferedWriter writer = null;
			Thread thread = null;
			TemplateCompiler compiler = null;
			try {
				PipedOutputStream outStream = new PipedOutputStream();
				writer = new BufferedWriter(new OutputStreamWriter(outStream));
				// Connect input stream to output stream
				inStream = new PipedInputStream(outStream);
				compiler = new TemplateCompiler(inStream);
				thread = new Thread(compiler);
				thread.start();
				template.merge(context, writer);
			} catch (Exception e) {
				//log it
				throw e;
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
			// Wait for thread to finish executing
			thread.join();
			// Get compiled report
			return (compiler.getJasperReport());
		} finally {
			// InputStream cannot be closed before thread
			// is finished executing
			if (inStream != null) {
				inStream.close();
			}
		}
	}

	private void generateHtmlOutput(JasperPrint jasperPrint,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, JRException {

		Map imagesMap = new HashMap();
		req.getSession().setAttribute("IMAGES_MAP", imagesMap);
		JRHtmlExporter exporter = new JRHtmlExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, resp
				.getWriter());

		exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				"image?image=");

		exporter.exportReport();
	}

	private DataSource getDataSource() throws NamingException {
		if (ds == null) {
			javax.naming.Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("jdbc/reportDB");
		}
		return ds;
	}

	private Connection getConnection() throws SQLException, NamingException {
		return getDataSource().getConnection();
	}

}