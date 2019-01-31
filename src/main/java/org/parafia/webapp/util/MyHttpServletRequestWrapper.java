package org.parafia.webapp.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.parafia.Constants;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public MyHttpServletRequestWrapper (HttpServletRequest request) {
		super(request);
	}

	public String getParameter(String paramName) {
		if ("sort".equals (paramName)) {
			String paramValue = super.getParameter(paramName);
			if (null == paramValue) {
				if (null == getSession().getAttribute(Constants.SORTING_FAMILY_FIELD))
					return "surname";
				else
					return (String)getSession().getAttribute(Constants.SORTING_FAMILY_FIELD);
			} else {
				getSession().setAttribute(Constants.SORTING_FAMILY_FIELD, paramValue);
				return paramValue;
			}
		} else if ("dir".equals (paramName)) {
			String paramValue = super.getParameter(paramName);
			if (null == paramValue) {
				if (null == getSession().getAttribute(Constants.SORTING_FAMILY_DIR))
					return "asc";
				else
					return (String)getSession().getAttribute(Constants.SORTING_FAMILY_DIR);
			} else {
				getSession().setAttribute(Constants.SORTING_FAMILY_DIR, paramValue);
				return paramValue;
			}
		} else
			return super.getParameter(paramName);
	}
}
