package org.parafia.webapp.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.printcards.BaptismPreparation;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class BaptismPreparationEditController
{

	private final Logger log = Logger.getLogger(getClass());
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

	@RequestMapping(value = "/baptismPreparationEdit", method = RequestMethod.GET)
	protected ModelAndView showForm(
				@RequestParam(value="personId", required = true) Long personId,
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		Person person;

		person = personManager.get(personId);

		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}
		if (person.getRemaining().getBaptismPreparation() == null)
			person.getRemaining().setBaptismPreparation(new BaptismPreparation());

		ModelAndView mv = new ModelAndView("/print/baptismPreparationEdit");
		mv.addObject("person", person);
		return mv;
	}

	@RequestMapping(value = "/baptismPreparationEdit", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("person") Person person,
				@RequestParam(value="personId") Long personId,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView(new RedirectView("baptismPreparationEdit.html")).addObject(Constants.PERSON_ID, personId); 
		try
		{
			BaptismPreparation baptismPreparation = person.getRemaining().getBaptismPreparation();
			person = personManager.get(personId);
			person.getRemaining().setBaptismPreparation(baptismPreparation);
			personManager.savePerson(person);
			msg.add(base.getText("person.saved", locale));
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
			return mv;
		}
	}
}
