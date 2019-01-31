package org.parafia.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.Constants;

public class JSONBuilder extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = -4305568865548956891L;

	private final Logger log = Logger.getLogger(getClass());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("JSONController action begins");
			
		PrintWriter out = response.getWriter();
		
		log.debug("filePath = " + request.getParameter("filePath"));
		String filePath;
		if (request.getParameter("filePath")  == null) {
			//TODO: poprawic parafiaFiles z configu
			filePath = Constants.USER_HOME + "parafiaFiles";
		} else {
			filePath = request.getParameter("filePath");
		}
		
		String json = org.parafia.util.JSONBuilder.buildJson(filePath, request.getContextPath());
		
		log.debug(json);
		
		out.write(json.toString());
		
		out.flush();
		out.close();
	}
}
