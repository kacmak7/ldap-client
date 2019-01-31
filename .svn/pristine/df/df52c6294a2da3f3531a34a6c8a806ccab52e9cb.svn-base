package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.parafia.Constants;
import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class MarriageEditController extends BaseFormController {
	//private PersonManager personManager;
	private FianceeManager fianceeManager;
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}
	
	/*public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}*/
	
	public MarriageEditController() {
        setCommandName("fianceePair");
        setCommandClass(FianceePair.class);
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		
		FianceePair fianceePair;
		
		if (!"".equals(request.getParameter(Constants.FIANCEE_PAIR_ID)) && StringUtils.isNumeric(request.getParameter(Constants.FIANCEE_PAIR_ID))) {
			//person = personManager.get(Long.valueOf(request.getParameter("personId")));
			fianceePair = fianceeManager.get(Long.valueOf(request.getParameter(Constants.FIANCEE_PAIR_ID)));
		} else {
			saveError(request, getText("errors.parameternotfound", request.getLocale()));
			//person = new OtherWOFamily();
			fianceePair = new FianceePair();
		}
		
		return fianceePair;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
	                             HttpServletResponse response, Object command,
	                             BindException errors)
	throws Exception {
	    log.debug("entering 'onSubmit' method...");

	    FianceePair fianceePair = (FianceePair) command;
	    
	    try {
	    	//rsonManager.savePerson(person);
	    	fianceeManager.saveFianceePair(fianceePair);
            saveMessage(request, getText("fiancee.fianceepair.saved", request.getLocale()));
            return showForm(request, response, errors);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ObjectExistsException e) {
        	saveError(request, "errors.existing.fianceepair");
            return showForm(request, response, errors);
        }
	}
}