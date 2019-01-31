package org.parafia.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PrintCertificateOnlyPriestController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private FianceeManager fianceeManager;
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintCertificateOnlyPriestController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial11 = new Font(font,11,Font.BOLD);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FianceePair fp = null;
		if (StringUtils.isNotEmpty(request.getParameter("fianceePairId")) && StringUtils.isNumeric(request.getParameter("fianceePairId")))
			fp = fianceeManager.get(Long.valueOf(request.getParameter("fianceePairId")));
		else
			fp = new FianceePair();
		
		prepareResponse(request, response, fp);
		
		return null;
	}
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, FianceePair fp) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + fp.getFianceeHe().getFullName() + fp.getFianceeShe().getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document = new Document(PageSize.A4, 20, 20, 134, 40);			//margin-top to trzecia pozycja
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			Phrase p2 = null;
		    if ("".equals(fp.getCertificate().getPriestFullName()) || "".equals(fp.getCertificate().getPriestPosition()))
		    	p2 = new Phrase(" ", arial11);
		    else	
		    	p2 = new Phrase(fp.getCertificate().getPriestFullName() + " - " + fp.getCertificate().getPriestPosition(), arial11);
		    
		    Paragraph p1 = new Paragraph(p2);
		    p1.setAlignment(Paragraph.ALIGN_CENTER);
		    document.add(p1);

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
		}			
	}
}