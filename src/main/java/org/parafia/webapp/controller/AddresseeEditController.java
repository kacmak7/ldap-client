package org.parafia.webapp.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.model.Addressee;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.service.AddresseeManager;
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

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class AddresseeEditController
{
	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private AddresseeManager addresseeManager;
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
	
	@ModelAttribute("addressee")
	public Addressee getAdressee(
			@RequestParam(value = "id", required = false) Long id)
	
	{
		Addressee addressee;
		if (id == null || id == 0)
		{
			addressee = new Addressee();
		}
		else
		{
			addressee = addresseeManager.get(id);
		}
		return addressee;
	}

	@RequestMapping(value = "/mail/addresseeEdit", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(
				@ModelAttribute("successMessages") Messages<String> msg,
				@RequestParam(value = "id", required = false) String id) throws Exception
	{
		ModelAndView mv = new ModelAndView("/mail/addresseeEdit");
		if (id != null && !"".equals(id))
			mv.addObject("adressee", addresseeManager.get(Long.valueOf(id)));
		return mv;
	}

	@RequestMapping(value = "/mail/addresseeEdit/delete", method = RequestMethod.POST)
	public ModelAndView delete(
				@ModelAttribute("successMessages") Messages<String> msg,
				@ModelAttribute("addressee") Addressee addressee) throws Exception
	{
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			addresseeManager.removeAddressee(addressee.getId());
			msg.add(base.getText("mail.addressee.deleted", addressee.getFullName(), locale));
			return new ModelAndView("common/closeReloadParent");
		}
		catch (Exception ex)
		{
			msg.add(base.getText("mail.addressee.not-removed", addressee.getFullName(), locale));
			ModelAndView mv = new ModelAndView("/mail/addresseeEdit");
			mv.addObject(addressee);
			return mv;
		}
	}

	@RequestMapping(value = "/mail/addresseeEdit", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("addressee") Addressee addressee,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err,
				BindingResult errors) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			addresseeManager.saveAddressee(addressee);
			msg.add(base.getText("addressee.saved", addressee.getFullName(), locale));
			return new ModelAndView("common/closeReloadParent");
		}
		catch (AccessDeniedException ade)
		{
			log.warn(ade.getMessage());
			throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
		}
		catch (ObjectExistsException e)
		{
			errors.rejectValue("name", "errors.existing.addressee",
					new Object[] { addressee.getFullName(), addressee.getInstitution() },
					"Użytkownik już istnieje w bazie");
			return new ModelAndView("mail/addresseeEdit");
		}
	}
}
