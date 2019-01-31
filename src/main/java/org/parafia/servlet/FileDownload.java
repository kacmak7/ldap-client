package org.parafia.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Mail;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.MailFile;
import org.parafia.service.FileManager;
import org.parafia.service.GraveyardManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: FileDownload
 *
 */
public class FileDownload extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(FileDownload.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public FileDownload() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Servlet action begins");
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		FileManager fileManager = (FileManager)context.getBean("fileManager");
		GraveyardManager graveyardManager = (GraveyardManager)context.getBean("graveyardManager");
		
		String path = null;
		String contentType = null;
		
		if (request.getParameter("id") != null) {
			MailFile file = (MailFile)fileManager.getMailFile(Long.valueOf(request.getParameter("id")));
			path = file.getMail().getPath()+Constants.FILE_SEP+file.getName();
			contentType = file.getContentType();
		} else {
			if (request.getParameter("graveFileId") == null) {
				log.debug("Brak parametru graveFileId lub id");
				return;
			} else {
				log.debug("Bierzemy graveFile " + request.getParameter("graveFileId"));
				//Grave grave = graveyardManager.get(Long.valueOf(request.getParameter("graveId")));
				GraveFile file = graveyardManager.getFile(Long.valueOf(request.getParameter("graveFileId")));
				path = request.getParameter("uploadDir") + Constants.FILE_SEP + file.getPath(); 
				contentType = file.getContentType();
			}
		}
		
		FileInputStream fileToDownload = new FileInputStream(path);
		ServletOutputStream out = response.getOutputStream();

		response.setContentType(contentType);
		
		response.setHeader("Content-Disposition","attachment;");
		response.setContentLength(fileToDownload.available());
		
		log.debug("File path = " + path);
		log.debug("ContentType = " + contentType);

		byte[] outputByte = new byte[4096];
		//copy binary contect to output stream
		while(fileToDownload.read(outputByte, 0, 4096) != -1) {
			out.write(outputByte, 0, 4096);
		}

		fileToDownload.close();
		out.flush();
		out.close();
	}
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}