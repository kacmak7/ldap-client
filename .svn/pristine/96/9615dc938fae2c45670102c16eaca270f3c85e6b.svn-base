package org.parafia.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.User;
import org.parafia.model.printcards.Baptism;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.service.UserManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.webapp.controller.commands.BapConDeaFindFilter;
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
@SessionAttributes(value = { "successMessages", "errors", "filter" })
public class BaptismEditController
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
	
	@ModelAttribute("filter")
	public BapConDeaFindFilter getBapConDeaFindFilter()
	{
		return new BapConDeaFindFilter();
	}
	
	@RequestMapping(value = "/editBaptism", method = RequestMethod.GET)
	protected ModelAndView showForm(
				@ModelAttribute("errors") Errors<String> err,
				@RequestParam("personId") Long personId) throws Exception
	{
		
		Person person = personManager.get(personId);
	
		newRemainingAndBaptism(person);

		if (person.getRemaining() != null && person.getRemaining().getBaptism() != null
				&& person.getRemaining().getBaptism().getDate() != null)
		{
			person.getRemaining().getBaptism().setDate(person.getRemaining().getBaptism().getDateDaysFirst());
		}

		ModelAndView mv = new ModelAndView("print/baptism");
		mv.addObject("person", person);
		return mv;
	}

	@RequestMapping(value = "/baptism", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("person") Person person,
				@RequestParam(value = "personId", required = true) Long personId,
				@RequestParam(value = "delete", required = false) String delete,
				@ModelAttribute("successMessages") Messages<String> msg,
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		
		log.debug("entering 'onSubmit' method...");
		ModelAndView mv = new ModelAndView(new RedirectView("editBaptism.html")).addObject(Constants.PERSON_ID, personId);
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			if (person.getRemaining() != null && person.getRemaining().getBaptism() != null
					&& person.getRemaining().getBaptism().getDate() != null)
			{
				Baptism baptism = person.getRemaining().getBaptism();
				person = personManager.get(personId);
				
				newRemainingAndBaptism(person);
				
				person.getRemaining().setBaptism(baptism);
				person.getRemaining().getBaptism().setDate(person.getRemaining().getBaptism().getDateYearFirst());
				
				personManager.savePerson(person);
			}
			
			msg.add(base.getText("person.saved", locale));

			if (delete != null)
				return new ModelAndView(new RedirectView("editBaptism.html")).addObject(Constants.PERSON_ID,
						person.getId());

			return mv;
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
			return new ModelAndView(new RedirectView("print/personform.html")).addObject(Constants.PERSON_ID,
					personId);
		}
	}
	
	private void newRemainingAndBaptism(Person person) {
		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}

		if (person.getRemaining().getBaptism() == null)
		{
			person.getRemaining().setBaptism(new Baptism());
		}
	}
}