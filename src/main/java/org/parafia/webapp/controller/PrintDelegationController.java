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

public class PrintDelegationController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialbold11;
	private Font arialbold13;
	
	private FianceeManager fianceeManager;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintDelegationController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial11 = new Font(font,11);
			arialbold11 = new Font(font,11, Font.BOLD);
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
		
		prepareResponse(request, response, fp);
		
		return null;
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
			document.add(prepareTextTable2(request, document));
			document.add(prepareEndingTable(request, document, fp));

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
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.stamp", request.getLocale()), arial11));
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell = new PdfPCell(new Phrase());
		cell.setBorder(PdfPCell.NO_BORDER);
		for (int i = 0; i < 15; i++) {
			table.addCell(cell);
		}
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.delegation.header", request.getLocale()), arialbold13));
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setPaddingTop(30f);
	    table.addCell(cell2);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, FianceePair fp) {
		Date now = new Date();
		/*Phrase p1 = new Phrase(getText("print.delegation.text1",
			new Object[] {
				fp.getAssistanceDelegation().getPriestFullName(),
				fp.getFianceeHe().getFullName() + " i " + fp.getFianceeShe().getFullName()
			},
			request.getLocale()), arial11);*/
		Phrase p1 = new Phrase();
		p1.add(new Chunk(getText("print.delegation.text1", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getAssistanceDelegation().getPriestFullName(), arialbold11));
		p1.add(new Chunk(getText("print.delegation.text1_2", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getFullName() + " i " + fp.getFianceeShe().getFullName(), arialbold11));
		p1.add(new Chunk(getText("print.delegation.text1_3", request.getLocale()), arial11));
		
	    PdfPTable table = new PdfPTable(1);
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setLeading(0, 1.6f);
	    cell1.setIndent(20f);
	    table.addCell(cell1);
	    
	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.delegation.text2",
	    		new Object[] {
					df.format(now)
				},
	    		request.getLocale()), arial11));
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setPaddingTop(30f);
	    table.addCell(cell2);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    
	    return table;
	}
	
	private PdfPTable prepareTextTable2(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(emptyCell);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.dots-rector", request.getLocale()), arial11));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell1);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(40f);
		
	    return table;
	}
	
	private PdfPTable prepareEndingTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(2);

		/*PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.delegation.text3",
				new Object[] {
					df.format(fp.getMarriageDate()),
					fp.getMarriageTime()
				},
				request.getLocale()), arial11));*/
		Phrase p1 = new Phrase();
		p1.add(new Chunk(getText("print.delegation.text3", request.getLocale()), arial11));
		p1.add(new Chunk(df.format(fp.getMarriageDate()), arialbold11));
		p1.add(new Chunk(getText("print.delegation.text3_2", request.getLocale()), arial11));
		p1.add(new Chunk(StringUtils.defaultString(fp.getMarriageTime()), arialbold11));
		p1.add(new Chunk(getText("print.delegation.text3_3", request.getLocale()), arial11));
		
		PdfPCell cell1 = new PdfPCell(p1);
		
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setPaddingBottom(40f);
		cell1.setLeading(0, 1.6f);
		table.addCell(cell1);
		
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(emptyCell);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.delegation.signature", request.getLocale()), arial11));
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell2);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    return table;
	}
}