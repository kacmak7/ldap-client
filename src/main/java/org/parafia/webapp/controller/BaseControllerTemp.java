package org.parafia.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parafia.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
public class BaseControllerTemp implements MessageSourceAware
{
	protected final transient Log log = LogFactory.getLog(getClass());
	private UserManager userManager = null;
	protected String templateName = null;
	protected String cancelView;
	@Autowired
	private MessageSource messageSource;

	public void bind(WebDataBinder binder)
	 {
		Locale locale = LocaleContextHolder.getLocale();
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format", locale));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
	
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}

	public UserManager getUserManager()
	{
		return this.userManager;
	}

	/*@SuppressWarnings("unchecked")
	public void saveError(HttpServletRequest request, String error)
	{
		if (e == null)
			e = new Errors<>();
		e.add(error);
	}

	@SuppressWarnings("unchecked")
	public void saveMessage(String msg)
	{
		if (m == null)
			m = new Messages<>();
		m.add(msg);
	}*/

	public String getText(String msgKey, Locale locale)
	{
		return messageSource.getMessage(msgKey, null, locale);
	}

	public String getText(String msgKey, String arg, Locale locale)
	{
		return getText(msgKey, new Object[] { arg }, locale);
	}

	public String getText(String msgKey, Object[] args, Locale locale)
	{
		return messageSource.getMessage(msgKey, args, locale);
	}

	/*public Map getConfiguration()
	{
		Map config = (HashMap) getServletContext().getAttribute(Constants.CONFIG);

		// so unit tests don't puke when nothing's been set
		if (config == null)
		{
			return new HashMap();
		}

		return config;
	}

	public ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception
	{
		if (request.getParameter("cancel") != null)
		{
			return new ModelAndView(getCancelView());
		}

		return super.processFormSubmission(request, response, command, errors);
	}

	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	{
		binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format", request.getLocale()));
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}*/

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public final void setCancelView(String cancelView)
	{
		this.cancelView = cancelView;
	}

	public int countSubmittedParams(List<String> params, String regex)
	{
		Pattern p = Pattern.compile(regex);
		int paramCount = 0;

		for (String paramName : params)
		{
			Matcher m = p.matcher(paramName);
			if (m.matches())
			{
				paramCount++;
			}
		}

		return paramCount;
	}

	@Override
	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	

	/*public final String getCancelView()
	{
		// Default to successView if cancelView is invalid
		if (this.cancelView == null || this.cancelView.length() == 0)
		{
			return getSuccessView();
		}
		return this.cancelView;
	}*/

}
