package org.parafia.webapp.controller;

import java.util.Locale;

import org.parafia.util.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;

@Component
public class BasePrintController {
	//protected Font arialbold11;
	//protected Font arial11;
	//protected Font arialbold9;
	//protected Font arial9;
	//protected Font arialbold16;
	//protected Font arial16;
	
	//private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private PrintUtil printUtil;
	
	@Autowired
	private MessageSource messageSource;
	
    public String getText(String msgKey, Object[] args) {
    	Locale l1 = Locale.forLanguageTag("pl-PL");
    	/*Locale l2 = Locale.getDefault();
    	
    	String str = messageSource.getMessage(msgKey, args, l1);*/
    	
    	return messageSource.getMessage(msgKey, args, l1);
    }
    
    public String getText(String msgKey) {
    	Locale l1 = Locale.forLanguageTag("pl-PL");
    	/*Locale l2 = Locale.getDefault();
    	
    	String str = messageSource.getMessage(msgKey, new Object[0], l1);*/
    	
    	/*for (Locale l: Locale.getAvailableLocales())
    		System.out.println(l.toLanguageTag());*/
    	
        return messageSource.getMessage(msgKey, new Object[0], l1);
    }
    
    protected Document getDocument() {
    	return new Document(PageSize.A4, 20, 20, 40, 40);
    }
    
    protected BaseFont getBaseFont() {
    	return printUtil.getBaseFont();
    }
}
