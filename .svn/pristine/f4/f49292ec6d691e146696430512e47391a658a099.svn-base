package org.parafia.webapp.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseFormControllerWithReqUtil extends BaseFormController
{
	protected int countSubmittedParams(List<String> params, String regex)
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
}