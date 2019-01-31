package org.parafia.webapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;

public class PrintLicenseAssistanceController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialbold11;
	private Font arialbold13;
	
	private FianceeManager fianceeManager;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintLicenseAssistanceController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial11 = new Font(font,11);
			arialbold11 = new Font(font,11,Font.BOLD);
			arialbold13 = new Font(font,13,Font.BOLD);
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
		
		if (!validate(fp)) {
			saveError(request, getText("errors.licence", request.getLocale()));
					
			return new ModelAndView(new RedirectView("print/fianceesform.html", true)).addObject(Constants.FIANCEE_PAIR_ID, fp.getId());
		} else {
			prepareResponse(request, response, fp);
			return null;
		}
	}
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, FianceePair fp) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + fp.getFianceeHe().getFullName() + fp.getFianceeShe().getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			document.add(prepareHeaderTable(request, document, fp));
			document.add(prepareTextTable(request, document, fp));
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
	
	
	private PdfPTable prepareHeaderTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(2);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.stamp", request.getLocale()), arial11));
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.license-assistance.header", request.getLocale()), arial11));
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setLeading(0, 1.6f);
	    table.addCell(cell2);
	    
	    PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.license-assistance.header1", request.getLocale()), arialbold13));
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell3.setColspan(2);
		cell3.setPaddingTop(20f);
	    table.addCell(cell3);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(1);
		
	    PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.license-assistance.text1", request.getLocale()), arial11));
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setLeading(0, 1.6f);
	    cell1.setIndent(20f);
	    table.addCell(cell1);
	    
	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.license-assistance.fiancee-he",
	    		new Object[] {fp.getFianceeHe().getFullName()},
	    		request.getLocale()), arialbold11));
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setLeading(0, 1.6f);
	    table.addCell(cell2);
	    
	    /*PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.license-assistance.text2",
	    		new Object[] { "wolny",
	    			fp.getFianceeHe().getReligion(),
	    			fp.getFianceeHe().getAddress().getFullString(),
	    			df.format(fp.getFianceeHe().getBirthDate()),
	    			fp.getFianceeHe().getBirthPlace(),
	    			fp.getFianceeHe().getBaptismDate(),
	    			fp.getFianceeHe().getBaptismChurchIn(),
	    			fp.getFianceeHe().getFathersName() + " " + fp.getFianceeHe().getMothersName(),
	    			fp.getFianceeHe().getBaptismChurch(),
	    			fp.getFianceeHe().getBaptismNumber()
	    		},
	    		request.getLocale()), arial11));*/
	    Phrase p1 = new Phrase();
		p1.add(new Chunk(getText("print.license-assistance.text2", request.getLocale()), arial11));
		p1.add(new Chunk("wolny", arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_2", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getReligion(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_3", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getAddress().getFullString(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_4", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getBirthDate() + " " + fp.getFianceeHe().getRemaining().getBirthPlace(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_5", request.getLocale()), arial11));
		p1.add(new Chunk((fp.getFianceeHe().getRemaining().getMarriage().getBaptismDate()) + " " + fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurchIn(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_6", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getFathersFullName() + " " + fp.getFianceeHe().getRemaining().getMothersFullName(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_7", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurch(), arialbold11));
		p1.add(new Chunk(getText("print.license-assistance.text2_8", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismNumber(), arialbold11));
		
		PdfPCell cell3 = new PdfPCell(p1);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setLeading(0, 1.6f);
	    table.addCell(cell3);
	    
	    PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.license-assistance.fiancee-she",
	    		new Object[] {fp.getFianceeShe().getFullName()},
	    		request.getLocale()), arialbold11));
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    cell4.setLeading(0, 1.6f);
	    table.addCell(cell4);
	    
	    Phrase p2 = new Phrase();
	    p2.add(new Chunk(getText("print.license-assistance.text2", request.getLocale()), arial11));
	    p2.add(new Chunk("wolna", arialbold11));
	    p2.add(new Chunk(getText("print.license-assistance.text2_2", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getReligion(), arialbold11));
	    p2.add(new Chunk(getText("print.license-assistance.text2_3", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getAddress().getFullString(), arialbold11));
	    p2.add(new Chunk(getText("print.license-assistance.text2_4", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getBirthDate() + " " + fp.getFianceeShe().getRemaining().getBirthPlace(), arialbold11));
	    p2.add(new Chunk(getText("print.license-assistance.text2_5", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismDate() + " " + fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurchIn(), arialbold11));
	    p2.add(new Chunk(getText("print.license-assistance.text2_6", request.getLocale()), arial11));
		p2.add(new Chunk(fp.getFianceeShe().getRemaining().getFathersFullName() + " " + fp.getFianceeShe().getRemaining().getMothersFullName(), arialbold11));
		p2.add(new Chunk(getText("print.license-assistance.text2_7", request.getLocale()), arial11));
		p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurch(), arialbold11));
		p2.add(new Chunk(getText("print.license-assistance.text2_8", request.getLocale()), arial11));
		p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismNumber(), arialbold11));

		PdfPCell cell5 = new PdfPCell(p2);
	    cell5.setBorder(PdfPCell.NO_BORDER);
	    cell5.setLeading(0, 1.6f);
	    table.addCell(cell5);
	    
	    PdfPCell cell6 = new PdfPCell(new Phrase(getText("print.license-assistance.text3",
	    		request.getLocale()), arial11));
	    cell6.setBorder(PdfPCell.NO_BORDER);
	    cell6.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell6.setLeading(0, 1.6f);
	    table.addCell(cell6);
	    
	    PdfPCell cell7 = new PdfPCell(new Phrase(getText("print.license-assistance.text4",
	    		new Object[] {df.format(new Date())},
	    		request.getLocale()), arial11));
	    cell7.setBorder(PdfPCell.NO_BORDER);
	    cell7.setPaddingTop(20f);
	    cell7.setLeading(0, 1.6f);
	    table.addCell(cell7);
	    
	    PdfPCell cell8 = new PdfPCell(new Phrase(getText("print.license-assistance.notices",
	    		new Object[] {fp.getLicenseAssistance().getRemarks()},
	    		request.getLocale()), arial11));
	    cell8.setBorder(PdfPCell.NO_BORDER);
	    cell8.setPaddingTop(20f);
	    cell8.setLeading(0, 1.6f);
	    table.addCell(cell8);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    
	    return table;
	}
	
	private PdfPTable prepareEndingTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);

		PdfPCell cell1 = new PdfPCell(new Phrase());
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.dots-rector", request.getLocale()), arial11));
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell2);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    return table;
	}
	
	private boolean validate(FianceePair fp) {
		if (fp.getFianceeHe() == null || fp.getFianceeHe().getRemaining() == null || fp.getFianceeHe().getRemaining().getMarriage() == null ||
				fp.getFianceeHe().getRemaining().getMarriage().getBaptismDate() == null || fp.getFianceeHe().getRemaining().getMarriage().getConfirmationDate() == null)
			return false;
		
		if (fp.getFianceeShe() == null || fp.getFianceeShe().getRemaining() == null || fp.getFianceeShe().getRemaining().getMarriage() == null ||
				fp.getFianceeShe().getRemaining().getMarriage().getBaptismDate() == null || fp.getFianceeShe().getRemaining().getMarriage().getConfirmationDate() == null)
			return false;
		
		return true;
	}
}