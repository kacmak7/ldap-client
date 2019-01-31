package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.parafia.Constants;
import org.parafia.model.FianceePair;
import org.parafia.model.OtherWoFamily;
import org.parafia.model.Person;
import org.parafia.model.User;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.FianceeManager;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class PersonFormController extends BaseFormController {
	private PersonManager personManager;
	private FianceeManager fianceeManager;

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PersonFormController() {
        setCommandName("person");
        setCommandClass(Person.class);
        //setSessionForm(true);
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		Person person;
		
		if (StringUtils.isNotBlank(request.getParameter("personId")) && StringUtils.isNumeric(request.getParameter("personId"))) {
			person = personManager.getPerson(Long.valueOf(request.getParameter("personId")));
		} else {
			person = new OtherWoFamily();
		}
		if (person.getRemaining() == null)
			person.setRemaining(new Remaining());
		
		FianceePair fp = fianceeManager.getFianceePairForPerson(person.getId());
		if (fp != null)
			request.setAttribute(Constants.FIANCEE_PAIR_ID, fp.getId());
		
	    return person;
	}
	

	@Override
	public ModelAndView processFormSubmission(HttpServletRequest request,
	                                          HttpServletResponse response,
	                                          Object command,
	                                          BindException errors)
			throws Exception {
	    if (request.getParameter("cancel") != null) {
	        return new ModelAndView(getCancelView());
	    } else if (request.getParameter("delete") != null) {
	    	String code = request.getParameter("code");
	    	log.debug("Kod zabezpieczajacy: " + code);
	    	User user = getUserManager().loadUserByUsername(request.getRemoteUser());
	    	if (user.getDeleteCode() != null && user.getDeleteCode().equals(code)) {
		    	Person person = (Person)command;
		    	try {
		    		personManager.remove(person.getId());
		    	} catch (DataAccessException ex) {
		    		saveError(request, getText("person.not-deleted", person.getFullName(), request.getLocale()));
		    		return new ModelAndView(new RedirectView(getCancelView(), true));
		    	}
		        saveMessage(request, getText("person.deleted", person.getFullName(), request.getLocale()));
		        return new ModelAndView(new RedirectView(getCancelView(), true));
	    	} else {
	    		saveError(request, getText("errors.invaliddeletecode", request.getLocale()));
	    		return showForm(request, response, errors);
	    	}
	    }
	    return super.processFormSubmission(request, response, command, errors);
	}


	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
	                             HttpServletResponse response, Object command,
	                             BindException errors)
	throws Exception {
	    log.debug("entering 'onSubmit' method...");
	
	    Person person = (Person) command;
	
        try {
        	person = personManager.savePerson(person);
            saveMessage(request, getText("person.saved", person.getFullName(), request.getLocale()));
            return new ModelAndView(getSuccessView()).addObject(Constants.PERSON_ID, person.getId());
            //return new ModelAndView(new RedirectView(getSuccessView())).addObject("personId", person.getId());
            //return showForm(request, response, errors);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ObjectExistsException e) {
            errors.rejectValue("surname", "errors.existing.person",
                               new Object[]{person.getFullName()}, "Osoba juz istnieje");
            return showForm(request, response, errors);
        }
	}
}
