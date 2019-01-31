package org.parafia.webapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.ObjectTraverser;
import org.parafia.model.Person;
import org.parafia.service.PersonManager;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Chunk;
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

public class PrintMoralityCertificateController extends BaseController {
	private PersonManager personManager;
	private final String BAPTISM = "baptism";
	private final String CONFIRMATION = "confirmation";
	private final float LINE_SPACING = 1.5f;
	
	private final Logger log = Logger.getLogger(getClass());
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	private Font arial12;
	private Font arialitalic10;
	private Font arialbold12;
	private Font arialbold16;
	
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public PrintMoralityCertificateController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			//BaseFont times = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arialitalic10 = new Font(font,10, Font.ITALIC);
			arial12 = new Font(font,12);
			arialbold12 = new Font(font,12,Font.BOLD);
			arialbold16 = new Font(font,16,Font.BOLD);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Person person = null;
		if (StringUtils.isNumeric(request.getParameter("personId")) && !"".equals(request.getParameter("personId")))
			person = personManager.get(Long.valueOf(request.getParameter("personId")));
		else
			return null;
		
		if (request.getParameter("sort") != null) {
			if (BAPTISM.equals(request.getParameter("sort")))
				prepareResponse(request, response, person, BAPTISM);
			else if (CONFIRMATION.equals(request.getParameter("sort")))
				prepareResponse(request, response, person, CONFIRMATION);
		}
		
		return null;
	}
	
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, Person person,
			String sort) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + person.getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		//Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		Document document = new Document(PageSize.A5.rotate(), 20, 20, 10, 10);
		
		try {
			PdfWriter.getInstance(document,	response.getOutputStream());
			document.open();
			
			document.add(prepareHeaderTable(request, document, sort));
			document.add(prepareTextTable(request, document, person, sort));
			document.add(prepareEndTable(request, document));
				
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
		}
	}
	
	private PdfPTable prepareHeaderTable(HttpServletRequest request, Document document, String sort) {
		PdfPTable table = new PdfPTable(2);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.morality.stamp", request.getLocale()), arialitalic10));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		table.addCell(cell1);
				
		PdfPCell cell2 = new PdfPCell();
		if (BAPTISM.equals(sort)) {
			cell2.addElement(new Phrase(getText("print.morality.intro-text", request.getLocale()), arialitalic10));
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		}
		cell2.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell2);
		
	    PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.morality.intro", request.getLocale()), arialbold16));
	    cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setPaddingTop(60f);
	    cell3.setColspan(2);
	    table.addCell(cell3);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(50f);
	    return table;
	}
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, Person person, String sort) throws DocumentException {
		PdfPTable table = new PdfPTable(1);
		Phrase p1 = new Phrase();
		
		person = (Person) ObjectTraverser.fillStrings(person, null);
		if (BAPTISM.equals(sort)) {
			//cell1 = new PdfPCell(new Phrase(getText("print.morality.baptism-text", new Object[]{person.getFullName(), person.getAddress().getFullString()}, request.getLocale()), arial12));
			p1.add(new Chunk(getText("print.morality.baptism-text", request.getLocale()), arial12));
		    p1.add(new Chunk(person.getFullName(), arialbold12));
		    p1.add(new Chunk(getText("print.morality.baptism-text_2", request.getLocale()), arial12));
		    p1.add(new Chunk(person.getAddress().getFullString(), arialbold12));
		    p1.add(new Chunk(getText("print.morality.baptism-text_3", request.getLocale()), arial12));
		} else if (CONFIRMATION.equals(sort)) {
			//cell1 = new PdfPCell(new Phrase(getText("print.morality.confirmation-text", new Object[]{person.getFullName(), person.getAddress().getFullString()}, request.getLocale()), arial12));
			p1.add(new Chunk(getText("print.morality.confirmation-text", request.getLocale()), arial12));
		    p1.add(new Chunk(person.getFullName(), arialbold12));
		    p1.add(new Chunk(getText("print.morality.confirmation-text_2", request.getLocale()), arial12));
		    p1.add(new Chunk(person.getAddress().getFullString(), arialbold12));
		    p1.add(new Chunk(getText("print.morality.confirmation-text_3", request.getLocale()), arial12));
		}
		PdfPCell cell1 = new PdfPCell(p1);
		
		cell1.setExtraParagraphSpace(40f);
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		table.addCell(cell1);
		
		return table;
	}
	
	
	private PdfPTable prepareEndTable(HttpServletRequest request, Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.morality.date", new Object[]{df.format(new Date())}, request.getLocale()), arial12));
		
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.morality.rector", request.getLocale()), arial12));
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell2);
		
		return table;
	}
}
