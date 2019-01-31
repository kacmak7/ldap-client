package org.parafia.webapp.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.FianceePair;
import org.parafia.model.Messages;
import org.parafia.model.printfiancees.Dispensation;
import org.parafia.service.FianceeManager;
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

@Controller("dispensationController")
@SessionAttributes(value = { "successMessages" })
public class DispensationEditController
{
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private FianceeManager fianceeManager;
	@Autowired
	private BaseControllerTemp base;

	@ModelAttribute("successMessages")
	public Messages<String> getMessages()
	{
		return new Messages<>();
	}

	@RequestMapping(value = "/dispensation", method = RequestMethod.GET)
	public ModelAndView showForm(
				@RequestParam("fianceePairId") Long fianceePairId) throws Exception
	{

		FianceePair fianceePair = fianceeManager.get(fianceePairId);

		if (fianceePair.getDispensation() == null)
			fianceePair.setDispensation(new Dispensation());

		ModelAndView mv = new ModelAndView("print/dispensationEdit");
		mv.addObject("fianceePair", fianceePair);
		return mv;
	}

	@RequestMapping(value = "/dispensation", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("fianceePair") FianceePair fianceePair,	
				@RequestParam("fianceePairId") Long fianceePairId,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView(new RedirectView("dispensation.html")).addObject(Constants.FIANCEE_PAIR_ID, fianceePairId);

		try
		{
			Dispensation dispensation = fianceePair.getDispensation();
			fianceePair = fianceeManager.get(fianceePairId);
			fianceePair.setDispensation(dispensation);
			fianceeManager.saveFianceePair(fianceePair);
			msg.add(base.getText("fiancee.fianceepair.saved", locale));
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
			msg.add(base.getText("errors.existing.fianceepair", locale));
			return mv;
		}
	}
}
