package org.parafia.webapp.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.printcards.Baptism;
import org.parafia.model.printcards.BaptismChanges;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class BaptismChangesController
{
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private PersonManager personManager;
	@Autowired
	private BaseControllerTemp base;

	@ModelAttribute("successMessages")
	public Messages<String> getMessages()
	{
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors()
	{
		return new Errors<>();
	}

	@RequestMapping(value = "/printbaptismchanges", method = RequestMethod.GET)
	protected ModelAndView showForm(
				@ModelAttribute("person") Person person,
				@ModelAttribute("errors") Errors<String> err, 
				@RequestParam(value = "personId", required = true) Long personId) throws Exception
	{
		
		person = personManager.get(personId);
		
		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}
		if (person.getRemaining().getBaptism() == null)
		{
			person.getRemaining().setBaptism(new Baptism());
		}
		if (person.getRemaining().getBaptismChanges() == null)
			person.getRemaining().setBaptismChanges(new BaptismChanges());

		ModelAndView mv = new ModelAndView("print/baptismChangesEdit");
		mv.addObject("person", person);
		return mv;
	}

	@RequestMapping(value = "/baptismchangesEdit", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute("person") Person person, 
				@RequestParam(value = "personId", required=true) Long personId,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		Person loadedPerson = personManager.get(personId);
		
		if(loadedPerson.getRemaining() == null)
		{
			loadedPerson.setRemaining(new Remaining());
		}
		
		loadedPerson.getRemaining().setBaptismChanges(person.getRemaining().getBaptismChanges());
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try
		{	
			personManager.savePerson(loadedPerson);
			msg.add(base.getText("person.saved", locale));
			return new ModelAndView(new RedirectView("printbaptismchanges.html")).addObject(Constants.PERSON_ID,
					personId);
		}
		catch (AccessDeniedException ade)
		{
			// thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
			log.warn(ade.getMessage());
			throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
		}
		catch (ObjectExistsException e)
		{
			err.add(base.getText("errors.existing.person", locale));
			return new ModelAndView("print/baptismChangesEdit");
		}
	}
}
