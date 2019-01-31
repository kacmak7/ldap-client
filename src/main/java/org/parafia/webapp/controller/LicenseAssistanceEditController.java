package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.apache.commons.lang.StringUtils;
import org.parafia.model.FianceePair;
import org.parafia.model.printfiancees.LicenseAssistance;
import org.parafia.service.FianceeManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class LicenseAssistanceEditController extends BaseFormController {
	private FianceeManager fianceeManager;

	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public LicenseAssistanceEditController() {
		setCommandName("fianceePair");
        setCommandClass(FianceePair.class);
    }
	
    @Override
    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView()).addObject("fianceePairId", ((FianceePair)command).getId());
        }

        return super.processFormSubmission(request, response, command, errors);
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		
		FianceePair fp = null;
		
		if (StringUtils.isNotEmpty(request.getParameter("fianceePairId")) && StringUtils.isNumeric(request.getParameter("fianceePairId"))) {
			fp = fianceeManager.get(Long.valueOf(request.getParameter("fianceePairId")));
		} else {
			fp = new FianceePair();
		}
		
		if (fp.getLicenseAssistance() == null)
			fp.setLicenseAssistance(new LicenseAssistance());
				
		return fp;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
	                             HttpServletResponse response, Object command,
	                             BindException errors)
			throws Exception {
	    log.debug("entering 'onSubmit' method...");
	    
	    FianceePair fp = (FianceePair) command;
	    
	    try {
	    	fianceeManager.saveFianceePair(fp);
            saveMessage(request, getText("fiancee.fianceepair.saved", fp.getFullNumber(), request.getLocale()));
            return showForm(request, response, errors);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ObjectExistsException e) {
            errors.rejectValue("surname", "errors.existing.fiancee-pair",
                               new Object[] {fp.getFullNumber()}, "Narzeczeni już istnieja w bazie danych");
            return showForm(request, response, errors);
        }
	}
}
