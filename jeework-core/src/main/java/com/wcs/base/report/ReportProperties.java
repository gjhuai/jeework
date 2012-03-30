/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.wcs.base.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.wcs.base.report.xml.XmlProperties;
import com.wcs.base.report.xml.XmlPropertiesException;

/**
 * @author olivieri
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReportProperties extends XmlProperties {

	private Map columnMap = new HashMap();
	private Map fieldMap = new HashMap();
	private String sql;
	private String reportName;
	private String header;
	private String title;
	private String image;

	/**
	 * @param fileName
	 * @throws XmlPropertiesException
	 */
	public ReportProperties(String fileName) throws XmlPropertiesException {
		super(fileName);
		init();
		// release reference to jdom document
		xmlProperties = null;
		root = null;
	}

	private void init() {
		
		// get column values
		List columns = root.getChild("columns").getChildren();
		Iterator iterator = columns.iterator();
		while (iterator.hasNext()) {
			Element column = (Element) iterator.next();
			Column col = new Column();
			String key = column.getChildTextTrim("name");
			col.setName(key);
			col.setAlignment(column.getChildTextTrim("alignment"));
			col.setWidth(Integer
					.parseInt(column.getChildTextTrim("width")));
			col.setClazzType(column.getChildTextTrim("clazzType"));
			col.setExpression(column.getChildTextTrim("expression"));
			columnMap.put(key, col);
		}
		
		// get field values
		List fields = root.getChild("fields").getChildren();
		iterator = fields.iterator();
		while (iterator.hasNext()) {
			Element field = (Element) iterator.next();
			Field f = new Field();
			String key = field.getChildTextTrim("name");
			f.setName(key);
			f.setClazzName(field.getChildTextTrim("clazzType"));
			f.setExpression(field.getChildTextTrim("expression"));
			fieldMap.put(key, f);
		}
		
		// get sql query
		sql = root.getChildTextTrim("sql");
		// get report name
		reportName = root.getChildTextTrim("name");
		// get header string
		header = root.getChildTextTrim("header");
		// get title
		title = root.getChildTextTrim("title");
		// get image file name
		image = root.getChildTextTrim("image");
	}
	
	public Column getColumn(String name) {
		return (Column) columnMap.get(name);
	}
	
	public Field getField(String name) {
		return (Field) fieldMap.get(name);
	}
	
	public String getSql() {
		return sql;
	}
	
	public String getReportName() {
		return reportName;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getImage() {
		return image;
	}
}