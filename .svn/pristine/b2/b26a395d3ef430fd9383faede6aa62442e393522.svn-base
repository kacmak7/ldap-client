package org.parafia.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

@Component
public class PrintUtil implements InitializingBean {
	private final Logger log = Logger.getLogger(getClass());
	
	@Value("${font.ttf.location}")
	private String fontFileLocation;
	
	private BaseFont font;
	
	public void afterPropertiesSet() {
		try {
			FontFactory.register(fontFileLocation);
			font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
		} catch (IOException ioex) {
			ioex.printStackTrace();
			log.error("Failed to run PrintUtil", ioex);
			System.exit(-1);
		} catch (DocumentException dex) {
			dex.printStackTrace();
			log.error("Failed to run PrintUtil", dex);
			System.exit(-1);
		}
	}
	
	public BaseFont getBaseFont() {
		return font;
	}
}
