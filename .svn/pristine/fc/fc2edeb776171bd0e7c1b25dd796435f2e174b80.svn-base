package org.parafia.webapp.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.printcards.Death;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.util.DateUtil;
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
public class DeathEditController
{
	private final Logger log = Logger.getLogger(getClass());
	@Autowired
	private BaseControllerTemp base;
	@Autowired
	private PersonManager personManager;

	
	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value)
			{
				if ("".equals(value))
					setValue(null);
				else if (DateUtil.checkDateFormat(value, DateUtil.getDatePattern()))
				{
					try
					{
						setValue(DateUtil.convertStringToDate(DateUtil.getDatePattern(), value));
					}
					catch (ParseException e)
					{
						e.printStackTrace();
						setValue(null);
					}
				}
				else
				{
					Calendar calendar = Calendar.getInstance();
					// TODO: value is 1484128800000,1484128800000
					long realValue = Long.valueOf(value.split(",")[0]);
					calendar.setTimeInMillis(realValue);

					setValue(calendar.getTime());
				}
			}

			public String getAsText()
			{
				if (getValue() == null)
					return null;
				else
				{
					SimpleDateFormat df = new SimpleDateFormat(DateUtil.getDatePattern());
					Date d = ((Date) getValue());
					return df.format(d);
				}
			}
		});
	}
	
	
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

	@RequestMapping(value = "/editDeath", method = RequestMethod.GET)
	protected ModelAndView showForm(
			@RequestParam(value = "personId") Long personId,
			@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		Person person;
		person = personManager.get(personId);

		newRemainingAndDeath(person);
		
		ModelAndView mv = new ModelAndView("print/death");
		mv.addObject("person", person);
		return mv;
	}

	@RequestMapping(value = "/editDeath", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute(value = "person") Person person, 
				@RequestParam(value="personId") Long personId,
				@ModelAttribute("errors") Errors<String> err,
				@ModelAttribute("successMessages") Messages<String> msg) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView(new RedirectView("editDeath.html")).addObject(Constants.PERSON_ID, personId);
		try
		{	
			if(person.getRemaining().getDeath().getDate() != null) {
				Death death = person.getRemaining().getDeath();
				person = personManager.get(personId); 
				
				newRemainingAndDeath(person);
				
				person.getRemaining().setDeath(death);
				person = personManager.savePerson(person);
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
	
	private void newRemainingAndDeath(Person person) {
		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}
		
		if (person.getRemaining().getDeath() == null)
		{
			person.getRemaining().setDeath(new Death());
		}
	}
}