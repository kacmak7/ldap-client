package org.parafia.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parafia.Constants;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageResources {
	private static Log log = LogFactory.getLog(DateUtil.class);
	
	public static String getMessage(String message) {
		Locale locale = LocaleContextHolder.getLocale();
	    try {
	        return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
	            .getString(message);
	    } catch (MissingResourceException mse) {
	        log.error("Can not find a message '" + message + "' in the project's resources");
	        return null;
	    }
	}
}
