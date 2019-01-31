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
import org.parafia.model.OtherWoFamily;
import org.parafia.model.Person;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.PersonManager;
import org.parafia.util.DateUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

public class PrintBaptismKnowActController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialitalic11;
	private Font arial9;
	private Font arialbold13;
	
	private PersonManager personManager;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public PrintBaptismKnowActController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial11 = new Font(font,11);
			arial9 = new Font(font,9);
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
		
		if(person.getRemaining() == null) {
			Remaining remaining = new Remaining();
			person.setRemaining(remaining);
		}
		
		if (person.getRemaining().getBaptism() == null || person.getRemaining().getBaptism().getDate() == null) {
			saveError(request, getText("errors.baptismchanges", request.getLocale()));
			return new ModelAndView(new RedirectView("baptismKnowActEdit.html"))
				.addObject("personId", request.getParameter("personId"));
		}
		
		if (person.getRemaining().getMothersFullName() == null || person.getRemaining().getFathersFullName() == null ||
				person.getRemaining().getBirthPlace() == null) {
			saveError(request, getText("errors.baptismknowact", request.getLocale()));
			return new ModelAndView(new RedirectView("/print/personform.html", true))
				.addObject("personId", request.getParameter("personId"));
		}
		
		if (person.getRemaining().getBaptismKnowAct() == null) {
			saveError(request, getText("errors.baptismknowact", request.getLocale()));
			return new ModelAndView(new RedirectView("baptismKnowActEdit.html"))
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
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-act-know.stamp", request.getLocale()), arial9));
		cell1.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-act-know.act-number", new Object[]{"???"}, request.getLocale()), arial9));
		cell2.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setPaddingBottom(30f);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.baptism-act-know.header1", request.getLocale()), arialbold13));
		cell3.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		cell3.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.baptism-act-know.header2", request.getLocale()), arial11));
		cell4.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		cell4.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell4);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
		
		return table;
	}
	
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, Person person) {
		PdfPTable table = new PdfPTable(1);
		
		ObjectTraverser.fillStrings(person, null);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-act-know.text1",
				new Object[]{
					df.format(person.getRemaining().getBaptismKnowAct().getDate()),
					person.getRemaining().getBaptismKnowAct().getWitness().getFullName(),
					person.getRemaining().getBaptismKnowAct().getWitness().getMothersFullName() + " i " + person.getRemaining().getBaptismKnowAct().getWitness().getFathersFullName(),
					DateUtil.calculateDifference(person.getRemaining().getBaptismKnowAct().getWitness().getBirthDate(), new Date()),
					person.getRemaining().getBaptismKnowAct().getWitness().getJob(),
					person.getRemaining().getBaptismKnowAct().getWitness().getReligion(),
					person.getRemaining().getBaptismKnowAct().getWitness().getPractising().getName().toLowerCase(),
					person.getRemaining().getBaptismKnowAct().getWitness().getAddress(),
					person.getRemaining().getBaptismKnowAct().getPriestFullName(),
					person.getFullName()
				},
				request.getLocale()), arial11));
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setLeading(0f, 1.7f);
	    cell1.setIndent(10f);
	    table.addCell(cell1);

	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-act-know.text2",
	    		new Object[] {
	    			person.getRemaining().getBaptismKnowAct().getWitness().getFullName()
	    		},
	    		request.getLocale()), arialitalic11));
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setLeading(0f, 1.7f);
	    cell2.setIndent(10f);
	    table.addCell(cell2);
	    
	    PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.baptism-act-know.text3",
	    		new Object[] {
	    			person.getFullName(),
	    			person.getRemaining().getBaptismKnowAct().getAcquaintanceFrom(),
	    			person.getRemaining().getBaptismKnowAct().getRelationship(),
	    			person.getBirthDate(),
	    			person.getRemaining().getBirthPlace(),
	    			person.getRemaining().getMothersFullName() + " i " + person.getRemaining().getFathersFullName(),
	    			person.getRemaining().getBaptismKnowAct().getBaptismPlace().getSecondName(),
	    			person.getRemaining().getBaptismKnowAct().getBaptismPlaceAdditional(),
	    			person.getRemaining().getBaptism().getDate(),
	    			person.getRemaining().getBaptismKnowAct().getBaptismGiver(),
	    			person.getRemaining().getBaptismKnowAct().getGodfather() + " i " + person.getRemaining().getBaptismKnowAct().getGodmother(),
	    			person.getRemaining().getBaptismKnowAct().getBaptismKnow(),
	    			person.getRemaining().getBaptismKnowAct().getReasonNotAct(),
	    			person.getRemaining().getBaptismKnowAct().getAdditional()
	    		},
	    		request.getLocale()), arial11));
	    cell3.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setLeading(0f, 1.7f);
	    cell3.setIndent(10f);
	    table.addCell(cell3);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareEndingTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(40f);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-act-know.witness-signature", request.getLocale()), arial9));
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell1);

	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-act-know.ending", request.getLocale()), arial11));
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell2);
	    
	    PdfPCell cell3 = new PdfPCell();
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell3);
	    
	    PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.baptism-act-know.rector", request.getLocale()), arial9));
	    cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    cell4.setPaddingTop(30f);
	    table.addCell(cell4);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
}

