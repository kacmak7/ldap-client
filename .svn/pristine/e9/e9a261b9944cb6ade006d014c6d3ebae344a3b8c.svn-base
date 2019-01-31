package org.parafia.util.decorator;

import java.text.NumberFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DisplaytagCurrencyDecorator implements DisplaytagColumnDecorator {
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media)
		throws DecoratorException {
		
		if (columnValue instanceof Float) {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String moneyString = formatter.format(columnValue);
			return moneyString;
		} else {
			return columnValue;
		}
	}
}
