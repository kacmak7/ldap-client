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

public class PrintInvestigationController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private final int NUMBER_OF_DETAILS_QUESTIONS = 12;
	private final int NUMBER_OF_PREVENTS_QUESTIONS = 15;
	
	private Font arial11;
	private Font arial9;
	private Font arialbold16;
	private Font arialbold11;
	
	private FianceeManager fianceeManager;
	
	private DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintInvestigationController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial9 = new Font(font,9);
			arial11 = new Font(font,11);
			arialbold16 = new Font(font,16,Font.BOLD);
			arialbold11 = new Font(font,11,Font.BOLD);
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
		
		if (fp.getProtocoleNumber() == null) {
			DateFormat df = new SimpleDateFormat("yyyy");
			int year = Integer.valueOf(df.format(new Date()));
			int number = fianceeManager.getMaxProtocoleNumber(year) + 1;
			fp.setProtocoleNumber(number);
			fp.setProtocoleDate(new Date());
			fp = fianceeManager.save(fp);
		}
		
		prepareResponse(request, response, fp);
		
		return null;
	}
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, FianceePair fp) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + fp.getFianceeHe().getFullName() + fp.getFianceeShe().getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			//writer.setPageEvent(new HeaderFooter(request, document, family));
			document.open();
			document.add(prepareHeader(request, document));
			document.add(prepareIntroTable(request, document, fp));

			Paragraph p1 = new Paragraph(new Phrase(getText("print.investigation.personal-details", request.getLocale()), arialbold11));
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p1.setSpacingAfter(20f);
			document.add(p1);

			document.add(preparePersonalDetailsTable(request, document, fp));
			document.add(prepareStarTable(request, document));
			
			document.newPage();
			
			Paragraph p2 = new Paragraph(new Phrase(getText("print.investigation.prevents", request.getLocale()), arialbold11));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingAfter(15f);
			document.add(p2);
			
			document.add(preparePreventsTable(request, document, fp));
			document.add(prepareSignaturesTable(request, document));
			
			Paragraph p3 = new Paragraph(new Phrase(getText("print.investigation.documents", request.getLocale()), arialbold11));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingAfter(20f);
			document.add(p3);
			
			document.add(prepareDocumentsTable(request, document, fp));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
		}			
	}
	
	
	private PdfPTable prepareHeader(HttpServletRequest request, Document document) {
		Phrase p1 = new Phrase(getText("print.investigation.header", new Object[]{}, request.getLocale()), arialbold16);
	    PdfPTable table = new PdfPTable(1);
	    PdfPCell cell = new PdfPCell(p1);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareIntroTable(HttpServletRequest request, Document document, FianceePair fp) {
		Phrase p1 = new Phrase(getText("print.investigation.intro",
			new Object[]{df.format(fp.getProtocoleDate()), fp.getFianceeHe().getFullName(), fp.getFianceeHe().getRemaining().getMarriage().getParish(), 
				fp.getFianceeShe().getFullName(), fp.getFianceeShe().getRemaining().getMarriage().getParish()},
			request.getLocale()), arial11);
	    PdfPTable table = new PdfPTable(1);
	    PdfPCell cell = new PdfPCell(p1);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable preparePersonalDetailsTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(3);
		
		PdfPCell emptyCell = new PdfPCell();
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.investigation.questions", request.getLocale()), arialbold11));
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.investigation.answers", request.getLocale()), arialbold11));
		cell2.setColspan(2);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.investigation.fianceeHe", request.getLocale()), arial11));
		cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		PdfPCell cell5 = new PdfPCell(new Phrase(getText("print.investigation.fianceeShe", request.getLocale()), arial11));
		cell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(emptyCell);
		table.addCell(cell4);
		table.addCell(cell5);
		
		
		if (fp.getFianceeHe().getRemaining().getMarriage().getProtocole() != null && fp.getFianceeShe().getRemaining().getMarriage().getProtocole() != null) {
			for (int i = 0; i < NUMBER_OF_DETAILS_QUESTIONS; i++) {
				table.addCell(new PdfPCell(new Phrase(getText("print.investigation.question" + (i+1), request.getLocale()), arial9)));
				table.addCell(new PdfPCell(new Phrase(fp.getFianceeHe().getRemaining().getMarriage().getProtocole().getAnswers()[i], arial9)));
				table.addCell(new PdfPCell(new Phrase(fp.getFianceeShe().getRemaining().getMarriage().getProtocole().getAnswers()[i], arial9)));
			}
		} else {
			for (int i = 0; i < NUMBER_OF_DETAILS_QUESTIONS; i++) {
				table.addCell(new PdfPCell(new Phrase(getText("print.investigation.question" + (i+1), request.getLocale()), arial9)));
				table.addCell(emptyCell);
				table.addCell(emptyCell);
			}
		}
		
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	
	private PdfPTable prepareStarTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.investigation.remark1", request.getLocale()), arial9));
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.investigation.remark2", request.getLocale()), arial9));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell2.setBorder(PdfPCell.NO_BORDER);
		
		table.addCell(cell1);
		table.addCell(cell2);
		
		return table;
	}
	
	private PdfPTable preparePreventsTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(3);
		
		PdfPCell emptyCell = new PdfPCell();
		
		/*for (int i = 13; i <= (NUMBER_OF_DETAILS_QUESTIONS + NUMBER_OF_PREVENTS_QUESTIONS); i++) {
			table.addCell(new PdfPCell(new Phrase(getText("print.investigation.question" + i, request.getLocale()), arial9)));
			table.addCell(emptyCell);
			table.addCell(emptyCell);
		}*/
		
		if (fp.getFianceeHe().getRemaining().getMarriage().getProtocole() != null && fp.getFianceeShe().getRemaining().getMarriage().getProtocole() != null) {
			for (int i = 12; i < (NUMBER_OF_DETAILS_QUESTIONS + NUMBER_OF_PREVENTS_QUESTIONS); i++) {
				table.addCell(new PdfPCell(new Phrase(getText("print.investigation.question" + (i+1), request.getLocale()), arial9)));
				table.addCell(new PdfPCell(new Phrase(fp.getFianceeHe().getRemaining().getMarriage().getProtocole().getAnswers()[i], arial9)));
				table.addCell(new PdfPCell(new Phrase(fp.getFianceeShe().getRemaining().getMarriage().getProtocole().getAnswers()[i], arial9)));
			}
		} else {
			for (int i = 12; i < (NUMBER_OF_DETAILS_QUESTIONS + NUMBER_OF_PREVENTS_QUESTIONS); i++) {
				table.addCell(new PdfPCell(new Phrase(getText("print.investigation.question" + (i+1), request.getLocale()), arial9)));
				table.addCell(emptyCell);
				table.addCell(emptyCell);
			}
		}
		
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(10f);
	    return table;
	}
	
	
	private PdfPTable prepareSignaturesTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);
		
		float height = 18f;
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.investigation.signatures", request.getLocale()), arialbold11));
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.investigation.priest", request.getLocale()), arialbold11));
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.empty-space", request.getLocale()), arialbold11));
		PdfPCell cell4 = new PdfPCell();
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setFixedHeight(height);
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setFixedHeight(height);
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setFixedHeight(height);
		cell4.setBorder(PdfPCell.NO_BORDER);
		cell4.setFixedHeight(height);
						
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell3);
		table.addCell(cell3);
				
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	
	private PdfPTable prepareDocumentsTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(3);
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.submit", request.getLocale()), arialbold11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.fianceehe.fullname", request.getLocale()), arialbold11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.fianceeshe.fullname", request.getLocale()), arialbold11)));
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.baptism-certificate", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.baptism-text", new Object[]{fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurch(), fp.getFianceeHe().getRemaining().getMarriage().getBaptismNumber(), df.format(fp.getFianceeHe().getRemaining().getMarriage().getBaptismDate())}, request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.baptism-text", new Object[]{fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurch(), fp.getFianceeShe().getRemaining().getMarriage().getBaptismNumber(), df.format(fp.getFianceeShe().getRemaining().getMarriage().getBaptismDate())}, request.getLocale()), arial11)));
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.death-certificate", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.death-text", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.death-text", request.getLocale()), arial11)));
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.banns", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.banns-text", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.banns-text", request.getLocale()), arial11)));
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.dispensation", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.dispensation-text", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.dispensation-text", request.getLocale()), arial11)));
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.investigation.marriage", new Object[] {"..........................", "........................."}, request.getLocale()), arial11));
		cell1.setColspan(3);
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.investigation.licence", new Object[] {"..........................", "........................."}, request.getLocale()), arial11));
		cell2.setColspan(3);
		table.addCell(cell1);
		table.addCell(cell2);
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-send", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-send-text", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-send-text", request.getLocale()), arial11)));
		
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-received", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-received-text", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.investigation.temere-received-text", request.getLocale()), arial11)));
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.investigation.notification", new Object[] {".........................."}, request.getLocale()), arial11));
		cell3.setColspan(3);
		table.addCell(cell3);
		
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
}
