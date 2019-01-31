package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.FianceeManager;
import org.springframework.web.servlet.ModelAndView;

public class FianceeListController extends BaseController {
	private Logger log = Logger.getLogger(getClass());
	
	private FianceeManager fianceeManager;
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }
		
		return new ModelAndView().addObject(Constants.FIANCEES_LIST, fianceeManager.getAll());
	}
}
