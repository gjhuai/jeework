package com.wcs.commons.security.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.commons.conf.WebappConfig;

@WebServlet("/menuloc")
public class MenuLocator extends HttpServlet{
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resCode = (String)req.getParameter("resCode");
		req.getSession().setAttribute(WebappConfig.SESSION_NEXT_DISPLAY_RES_CODE, resCode);
	}
 
}
