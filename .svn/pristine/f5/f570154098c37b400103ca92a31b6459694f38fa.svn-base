package org.parafia.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.parafia.Constants;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.Controller;

public abstract class BaseController implements Controller {
	private MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }
    
    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(Constants.MESSAGES_KEY);

        if (messages == null) {
            messages = new ArrayList();
        }

        messages.add(msg);
        request.getSession().setAttribute(Constants.MESSAGES_KEY, messages);
    }
    
    public String getText(String msgKey, Object[] args, Locale locale) {
        return messageSource.getMessage(msgKey, args, locale);
    }
    
    public String getText(String msgKey, Locale locale) {
        return messageSource.getMessage(msgKey, new Object[0], locale);
    }
}
