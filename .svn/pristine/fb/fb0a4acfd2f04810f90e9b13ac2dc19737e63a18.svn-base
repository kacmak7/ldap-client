package org.parafia.webapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.OtherWoFamily;
import org.parafia.model.Person;
import org.parafia.service.PersonManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

public class PrintBaptismPreparationController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialitalic11;
	private Font arialbold11;
	private Font arialbold13;
	
	private PersonManager personManager;
	
	//private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public PrintBaptismPreparationController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial11 = new Font(font,11);
			arialbold11 = new Font(font,11,Font.BOLD);
			arialbold13 = new Font(font,13,Font.BOLD);
			arialitalic11 = new Font(font,11,Font.ITALIC);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Person person = null;
		if (StringUtils.isNotEmpty(request.getParameter("personId")) && StringUtils.isNumeric(request.getParameter("personId")))
			person = personManager.get(Long.valueOf(request.getParameter("personId")));
		else
			person = new OtherWoFamily();
		
		if (person.getRemaining().getMothersFullName() == null || person.getRemaining().getFathersFullName() == null ||
				person.getRemaining().getBirthPlace() == null) {
			saveError(request, getText("errors.baptismknowact", request.getLocale()));
			return new ModelAndView(new RedirectView("/print/personform.html", true))
				.addObject("personId", request.getParameter("personId"));
		}
		
		if (person.getRemaining().getBaptismPreparation() == null) {
			saveError(request, getText("errors.baptismknowact", request.getLocale()));
			return new ModelAndView(new RedirectView("baptismPreparationEdit.html")).addObject("familyId", request.getParameter("familyId"))
				.addObject("personId", request.getParameter("personId"));
		}
		
		prepareResponse(request, response, person);
		
		return null;
	}
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, Person person) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + person.getFirstName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			document.add(prepareHeaderTable(request, document));
			document.add(prepareTextTable(request, document, person));
			document.add(prepareEndingTable(request, document));
			document.add(prepareStampTable(request, document));

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
		}			
	}
	
	private PdfPTable prepareHeaderTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(1);
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.parish.full-name2", request.getLocale()), arial11));
		cell1.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-preparation.header1", request.getLocale()), arialbold13));
		cell2.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell2.setPaddingLeft(250f);
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setPaddingBottom(50f);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.baptism-preparation.header2", request.getLocale()), arialbold11));
		cell3.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell3.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell3);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
		
		return table;
	}
	
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, Person person) {
		PdfPTable table = new PdfPTable(1);
		
		/*PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-preparation.text1",
				new Object[]{
					person.getFullName(),
					person.getRemaining().getMothersFullName() + " i " + person.getRemaining().getFathersFullName(),
					person.getBirthDate(),
					person.getRemaining().getBirthPlace(),
					person.getRemaining().getBaptismPreparation().getPriestFullName()
				},
				request.getLocale()), arial11));*/
		Phrase p1 = new Phrase();
		p1.add(new Chunk(getText("print.baptism-preparation.text1", request.getLocale()), arial11));
	    p1.add(new Chunk(person.getFullName(), arialbold11));
	    p1.add(new Chunk(getText("print.baptism-preparation.text1_2", request.getLocale()), arial11));
	    p1.add(new Chunk(person.getRemaining().getMothersMaidenFullName() + " i " + person.getRemaining().getFathersFullName(), arialbold11));
	    p1.add(new Chunk(getText("print.baptism-preparation.text1_3", request.getLocale()), arial11));
	    p1.add(new Chunk(person.getBirthDate(), arialbold11));
	    p1.add(new Chunk(getText("print.baptism-preparation.text1_4", request.getLocale()), arial11));
	    p1.add(new Chunk(person.getRemaining().getBirthPlace(), arialbold11));
	    p1.add(new Chunk(getText("print.baptism-preparation.text1_5", request.getLocale()), arial11));
	    p1.add(new Chunk(person.getRemaining().getBaptismPreparation().getPreparedBy(), arialbold11));
		
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setLeading(0f, 1.7f);
	    cell1.setIndent(10f);
	    table.addCell(cell1);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareEndingTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(1);
		table.setSpacingBefore(40f);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-preparation.text2", request.getLocale()), arialbold11));
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell1);

	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-preparation.text3", request.getLocale()), arial11));
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell2);
	    
	    PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.baptism-preparation.text4", request.getLocale()), arialitalic11));
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell3);
	    
	    PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.baptism-preparation.text5", request.getLocale()), arial11));
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell4);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareStampTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(40f);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.stamp", request.getLocale()), arial11));
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell1);

	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.rector", request.getLocale()), arial11));
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell2);
	    	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
}