package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.model.Mail;
import org.parafia.service.FileManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class UploadDisplayController implements Controller {
	private final Logger log = Logger.getLogger(getClass());
	
	private FileManager fileManager;

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
		throws Exception {
		
		Mail f = null;
		if (request.getParameter("mailId") == null) {
			log.error("There is no mailId parameter");
			return new ModelAndView("common/closeReloadParent");
		} else {
			f = fileManager.get(Long.valueOf(request.getParameter("mailId")));
		}
		return new ModelAndView("mail/uploadDisplay").addObject("file", f);
	}
}
