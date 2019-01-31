package org.parafia.webapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.FianceePair;
import org.parafia.model.InvestigationProtocole;
import org.parafia.model.printcards.Marriage;
import org.parafia.service.FianceeManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class InvestigationController extends BaseFormController {
	private Logger log = Logger.getLogger(getClass());
	
	private FianceeManager fianceeManager;
	
	public InvestigationController() {
        setCommandName("fianceePair");
        setCommandClass(FianceePair.class);
    }
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		
		FianceePair fp = null;
		
		if (StringUtils.isNotBlank(request.getParameter("fianceePairId"))) {
			//fp = fianceeManager.get(Long.valueOf(request.getParameter("fianceePairId")));
			fp = fianceeManager.getFianceePair(Long.valueOf(request.getParameter("fianceePairId")));
	    } else {
	    	//fp = new FianceePair();
	    }
		
		if (fp.getFianceeHe().getRemaining().getMarriage() == null) {
			fp.getFianceeHe().getRemaining().setMarriage(new Marriage());
		}
		if (fp.getFianceeShe().getRemaining().getMarriage() == null) {
			fp.getFianceeShe().getRemaining().setMarriage(new Marriage());
		}
		
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
		
		if (fp.getFianceeHe().getRemaining().getMarriage().getProtocole() == null) {
			InvestigationProtocole fianceeHeProtocole = new InvestigationProtocole();
			fianceeHeProtocole.getAnswers()[0] = fp.getFianceeHe().getFullName() + " " + StringUtils.defaultString(fp.getFianceeHe().getJob()); 
			fianceeHeProtocole.getAnswers()[1] = fp.getFianceeHe().getRemaining().getFathersFullName() + "\n" + fp.getFianceeHe().getRemaining().getMothersMaidenFullName();
			
			String birthBaptismConfirmation = "";
			birthBaptismConfirmation += StringUtils.defaultString(fp.getFianceeHe().getBirthDate()) + " " + StringUtils.defaultString(fp.getFianceeHe().getRemaining().getBirthPlace()) + "\n";
			if (fp.getFianceeHe().getBaptismDate() != null)
				birthBaptismConfirmation += fp.getFianceeHe().getBaptismDate() + " " + StringUtils.defaultString(fp.getFianceeHe().getBaptismPlace()) + "\n";
			if (fp.getFianceeHe().getRemaining().getMarriage() != null && fp.getFianceeHe().getRemaining().getMarriage().getConfirmationDate() != null)
				birthBaptismConfirmation += df.format(fp.getFianceeHe().getRemaining().getMarriage().getConfirmationDate()) + " " + StringUtils.defaultString(fp.getFianceeHe().getRemaining().getMarriage().getConfirmationChurch());
			fianceeHeProtocole.getAnswers()[2] = birthBaptismConfirmation;
			
			fianceeHeProtocole.getAnswers()[3] = StringUtils.defaultString(fp.getFianceeHe().getRemaining().getMarriage().getReligion());
			fp.getFianceeHe().getRemaining().getMarriage().setProtocole(fianceeHeProtocole);
		} if (fp.getFianceeShe().getRemaining().getMarriage().getProtocole() == null) {
			InvestigationProtocole fianceeSheProtocole = new InvestigationProtocole();
			fianceeSheProtocole.getAnswers()[0] = fp.getFianceeShe().getFullName() + " " + StringUtils.defaultString(fp.getFianceeShe().getJob()); 
			fianceeSheProtocole.getAnswers()[1] = fp.getFianceeShe().getRemaining().getFathersFullName() + "\n" + fp.getFianceeShe().getRemaining().getMothersMaidenFullName();
			
			String birthBaptismConfirmation = "";
			birthBaptismConfirmation += StringUtils.defaultString(fp.getFianceeShe().getBirthDate()) + " " + StringUtils.defaultString(fp.getFianceeShe().getRemaining().getBirthPlace()) + "\n";
			if (fp.getFianceeShe().getBaptismDate() != null)
				birthBaptismConfirmation += fp.getFianceeShe().getBaptismDate() + " " + StringUtils.defaultString(fp.getFianceeShe().getBaptismPlace()) + "\n";
			if (fp.getFianceeShe().getRemaining().getMarriage() != null && fp.getFianceeShe().getRemaining().getMarriage().getConfirmationDate() != null)
				birthBaptismConfirmation += df.format(fp.getFianceeShe().getRemaining().getMarriage().getConfirmationDate()) + " " + StringUtils.defaultString(fp.getFianceeShe().getRemaining().getMarriage().getConfirmationChurch());
			fianceeSheProtocole.getAnswers()[2] = birthBaptismConfirmation;
			
			fianceeSheProtocole.getAnswers()[3] = StringUtils.defaultString(fp.getFianceeShe().getRemaining().getMarriage().getReligion());
			fp.getFianceeShe().getRemaining().getMarriage().setProtocole(fianceeSheProtocole);
		}
		request.setAttribute("numberOfQuestions", Constants.NUMBER_OF_QUESTIONS);
		
		return fp;
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
