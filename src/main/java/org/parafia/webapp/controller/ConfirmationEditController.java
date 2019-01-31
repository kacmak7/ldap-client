package org.parafia.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.OtherWoFamily;
import org.parafia.model.Person;
import org.parafia.model.User;
import org.parafia.model.printcards.Confirmation;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.service.UserManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class ConfirmationEditController
{
	private Logger log = Logger.getLogger(ConfirmationEditController.class);
	@Autowired
	private BaseControllerTemp base;
	@Autowired
	private PersonManager personManager;
	@Autowired
	private UserManager usrMgr;

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

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		base.bind(binder);
	}
	
	@RequestMapping(value = "/editConfirmation", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(@ModelAttribute("errors") Errors<String> err,
			@RequestParam("personId") String personId) throws Exception
	{
		Locale locale = LocaleContextHolder.getLocale();
		Person person;

		if (!"".equals(personId) && StringUtils.isNumeric(personId))
		{
			person = personManager.get(Long.valueOf(personId));
		}
		else
		{
			err.add(base.getText("errors.parameternotfound", locale));
			person = new OtherWoFamily();
		}

		newRemainingAndConfirmation(person);
		
		ModelAndView mv = new ModelAndView("print/confirmation");
		mv.addObject("person", person);
		return mv;
	}

	@RequestMapping(value = "/editConfirmation", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("person") Person person, 
				@RequestParam("personId") Long perId,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		ModelAndView mv = new ModelAndView(new RedirectView("editConfirmation.html", true)).addObject(Constants.PERSON_ID, perId);
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			if(person.getRemaining().getConfirmation().getDate() != null) {
			Confirmation confirmation = person.getRemaining().getConfirmation();
			person = personManager.get(perId);
			
			newRemainingAndConfirmation(person);
			
			person.getRemaining().setConfirmation(confirmation);
			personManager.savePerson(person);
			msg.add(base.getText("person.saved", locale));
			} else {
				msg.add(base.getText("errors.parameternotfound", locale));
			}
			
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
			err.add("errors.existing.person");
			return mv;
		}
	}
	
	private void newRemainingAndConfirmation(Person person){
		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}

		if (person.getRemaining().getConfirmation() == null)
		{
			person.getRemaining().setConfirmation(new Confirmation());
		}
	}
}