package org.parafia.util.decorator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;
import org.parafia.Constants;

public class DisplaytagDateDecorator implements DisplaytagColumnDecorator {
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
		throws DecoratorException {
		
		if (columnValue instanceof Date) {
			DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
			return df.format(columnValue);
		} else {
			return columnValue;
		}
	}
}
