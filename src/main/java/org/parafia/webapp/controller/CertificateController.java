package org.parafia.webapp.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.model.Errors;
import org.parafia.model.FianceePair;
import org.parafia.model.Messages;
import org.parafia.model.printfiancees.Certificate;
import org.parafia.service.FianceeManager;
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

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class CertificateController
{
	private Logger log = Logger.getLogger(CertificateController.class);
	@Autowired
	private BaseControllerTemp base;
	@Autowired
	private FianceeManager fianceeManager;
	
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

	@RequestMapping(value = "/certificate", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(@RequestParam("fianceePairId") String fianceePairId) throws Exception
	{

		FianceePair fp = null;

		if (StringUtils.isNotEmpty(fianceePairId) && StringUtils.isNumeric(fianceePairId))
		{
			fp = fianceeManager.get(Long.valueOf(fianceePairId));
		}
		else
		{
			fp = new FianceePair();
		}

		if (fp.getCertificate() == null)
			fp.setCertificate(new Certificate());

		ModelAndView mv = new ModelAndView("print/certificateEdit");
		mv.addObject("fianceePair", fp);
		mv.addObject("fianceePairId", fianceePairId);
		return mv;
	}

	@RequestMapping(value = "/certificate", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute("fianceePair") FianceePair fp, @RequestParam("fianceePairId") Long fianceePairId,
			@ModelAttribute("successMessages") Messages<String> msg, BindingResult errors) throws Exception
	{
		Certificate ce = fp.getCertificate();
		fp = fianceeManager.get(fianceePairId);
		fp.setCertificate(ce);
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			fianceeManager.saveFianceePair(fp);
			msg.add(base.getText("fiancee.fianceepair.saved", fp.getFullNumber(), locale));
		}
		catch (AccessDeniedException ade)
		{
			// thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
			log.warn(ade.getMessage());
			throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
		}
		catch (ObjectExistsException e)
		{
			errors.rejectValue("surname", "errors.existing.fiancee-pair", new Object[] { fp.getFullNumber() },
					"Narzeczeni już istnieja w bazie danych");
		}
		return formBackingObject(String.valueOf(fianceePairId));
	}
}
