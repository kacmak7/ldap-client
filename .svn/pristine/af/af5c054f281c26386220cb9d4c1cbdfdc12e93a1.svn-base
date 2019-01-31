package org.parafia.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Person;
import org.parafia.service.PersonManager;
import org.parafia.webapp.controller.commands.PersonFindFilter;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class PersonListController extends BaseFormController {
	private final static String PERSON_1_ID = "person1Id";
	private final static String PERSON_2_ID = "person2Id";
	private PersonManager personManager;
	private final Logger log = Logger.getLogger(getClass());

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public PersonListController() {
        setCommandName("filter");
        setCommandClass(PersonFindFilter.class);
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }
        
        PersonFindFilter filter = (PersonFindFilter)request.getSession().getAttribute(Constants.PERSON_FILTER);
        
        if (request.getParameter(Constants.PERSON_ID) != null) {		//wybieramy narzeczonego dla danej osoby, czyli musimy ja usunac z tej listy
        	Long id = Long.valueOf(request.getParameter(Constants.PERSON_ID));
        	
        	if (filter != null) {
            	request.setAttribute(Constants.PERSONS_LIST, personManager.getPersonsWithoutId(filter.getFirstName(), filter.getSurname(), id));
            	return filter;
            } else
            	request.setAttribute(Constants.PERSONS_LIST, personManager.getAllWithoutId(id));        	
        } else {
        	if (filter != null) {
            	request.setAttribute(Constants.PERSONS_LIST, personManager.getPersons(filter.getFirstName(), filter.getSurname()));
            	return filter;
            } else
            	request.setAttribute(Constants.PERSONS_LIST, personManager.getPersons());
        }
		
		return new PersonFindFilter();
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
	                             HttpServletResponse response, Object command,
	                             BindException errors)
			throws Exception {
		log.debug("entering 'onSubmit' method...");
		
		PersonFindFilter filter = (PersonFindFilter)command;
		
		List<Person> persons = null;
		if (filter.isNotBlank()) {
			persons = personManager.getPersons(filter.getFirstName(), filter.getSurname());
			request.getSession().setAttribute(Constants.PERSON_FILTER, filter);
		} else {
			persons = personManager.getAll();
			request.getSession().removeAttribute(Constants.PERSON_FILTER);
		}
		
		ModelAndView mav = new ModelAndView(getSuccessView()).addObject("filter", filter).addObject(Constants.PERSONS_LIST, persons);
		if (request.getParameter(PERSON_1_ID) != null)
			mav.addObject(PERSON_1_ID, request.getParameter(PERSON_1_ID));
		if (request.getParameter(PERSON_2_ID) != null)
			mav.addObject(PERSON_2_ID, request.getParameter(PERSON_2_ID));
			
		return mav;
	}
}
