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
import org.parafia.util.DateUtil;
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

import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;

public class PrintDispensationController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialbold11;
	private Font arialbold13;
	
	private FianceeManager fianceeManager;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintDispensationController(String fontFileLocation) {
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
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.certificate.header-date", new Object[]{df.format(new Date())}, request.getLocale()), arial11));
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.parish.full-name2", request.getLocale()), arial11));
		cell2.setLeading(0, 1.6f);
		cell2.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell2);
	    
	    PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.dispensation.header1", request.getLocale()), arialbold13));
	    cell3.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
	    cell3.setPaddingLeft(250f);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setPaddingBottom(50f);
		table.addCell(cell3);
		
		PdfPCell cell4 = null;
		if (fp.getDispensation().isUnderAge())
			cell4 = new PdfPCell(new Phrase(getText("print.dispensation.text1-underage", request.getLocale()), arial11));
		else
			cell4 = new PdfPCell(new Phrase(getText("print.dispensation.text1", request.getLocale()), arial11));
		cell4.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
		cell4.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell4);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, FianceePair fp) {
		Date now = new Date();
		Phrase p1 = new Phrase();
		p1.add(new Chunk(getText("print.dispensation.text2", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getFullName(), arialbold11));
		p1.add(new Chunk(getText("print.dispensation.text2_2", request.getLocale()), arial11));
		
		p1.add(new Chunk(String.valueOf(DateUtil.calculateDifference(fp.getFianceeHe().getBirthDate(), now)), arialbold11));
		
		p1.add(new Chunk(getText("print.dispensation.text2_3", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getReligion(), arialbold11));
		p1.add(new Chunk(getText("print.dispensation.text2_4", request.getLocale()), arial11));
		p1.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getParish(), arialbold11));
		
		/*getText("print.dispensation.text2",
			new Object[] {fp.getFianceeHe().getFullName(),
						DateUtil.calculateDifference(fp.getFianceeHe().getBirthDate(), now),
						fp.getFianceeHe().getReligion(),
						fp.getFianceeHe().getParish()
			},
			request.getLocale()), arial11);*/
	    PdfPTable table = new PdfPTable(1);
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setLeading(0, 1.6f);
	    cell1.setIndent(20f);
	    table.addCell(cell1);
	    
	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.dispensation.text3", request.getLocale()), arial11));
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell2.setLeading(0, 1.6f);
	    cell2.setIndent(20f);
	    table.addCell(cell2);
	    
	    /*Phrase p2 = new Phrase(getText("print.dispensation.text4",
				new Object[] {fp.getFianceeShe().getFullName(),
							DateUtil.calculateDifference(fp.getFianceeShe().getBirthDate(), now),
							fp.getFianceeShe().getReligion(),
							fp.getFianceeShe().getParish()
				},
				request.getLocale()), arial11);*/
	    
	    Phrase p2 = new Phrase();
	    p2.add(new Chunk(getText("print.dispensation.text4", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getFullName(), arialbold11));
	    p2.add(new Chunk(getText("print.dispensation.text4_2", request.getLocale()), arial11));
	    
	    p2.add(new Chunk(String.valueOf(DateUtil.calculateDifference(fp.getFianceeShe().getBirthDate(), now)), arialbold11));
	    
	    p2.add(new Chunk(getText("print.dispensation.text4_3", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getReligion(), arialbold11));
	    p2.add(new Chunk(getText("print.dispensation.text4_4", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getParish(), arialbold11));
	    
	    PdfPCell cell3 = new PdfPCell(p2);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setLeading(0, 1.6f);
	    cell3.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell3.setIndent(20f);
	    table.addCell(cell3);
	    
	    PdfPCell cell4 = null;
	    
	    if (fp.getDispensation().isUnderAge())
	    	cell4 = new PdfPCell(new Phrase(getText("print.dispensation.text5-underage",
	    		request.getLocale()), arial11));
	    else
	    	cell4 = new PdfPCell(new Phrase(getText("print.dispensation.text5",
	    		new Object[]{fp.getDispensation().getSacrament()}, request.getLocale()), arial11));
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    cell4.setLeading(0, 1.6f);
	    cell4.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell4.setIndent(20f);
	    table.addCell(cell4);
	    
	    Phrase p3 = new Phrase();
	    p3.add(new Chunk(getText("print.dispensation.text6", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getDispensation().getReason(), arialbold11));
	    
	    /*PdfPCell cell5 = new PdfPCell(new Phrase(getText("print.dispensation.text6",
	    		new Object[]{fp.getDispensation().getReason()}, request.getLocale()), arial11));*/
	    PdfPCell cell5 = new PdfPCell(p3);
	    
	    cell5.setBorder(PdfPCell.NO_BORDER);
	    cell5.setLeading(0, 1.6f);
	    cell5.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell5.setIndent(20f);
	    table.addCell(cell5);
	    
	    PdfPCell cell6 = null;
	    if (fp.getDispensation().isUnderAge())
	    	cell6 = new PdfPCell(new Phrase(getText("print.dispensation.text7-underage",
	    		new Object[]{fp.getDispensation().getReason()}, request.getLocale()), arial11));
	    else
	    	cell6 = new PdfPCell(new Phrase(getText("print.dispensation.text7",
		    	new Object[]{fp.getDispensation().getReason()}, request.getLocale()), arial11));

	    cell6.setBorder(PdfPCell.NO_BORDER);
	    cell6.setLeading(0, 1.6f);
	    cell6.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell6.setIndent(20f);
	    cell6.setPaddingBottom(20f);
	    table.addCell(cell6);
	    
	    if (fp.getDispensation().isUnderAge()) {
	    	PdfPCell cell7 = new PdfPCell(new Phrase(getText("print.dispensation.attachments",
		    		request.getLocale()), arialbold11));
	    	cell7.setBorder(PdfPCell.NO_BORDER);
	    	cell7.setLeading(0, 1.6f);
		    table.addCell(cell7);
		    
		    PdfPCell cell8 = new PdfPCell(new Phrase(getText("print.dispensation.attachments1",
		    		request.getLocale()), arial11));
	    	cell8.setBorder(PdfPCell.NO_BORDER);
	    	cell8.setLeading(0, 1.6f);
		    table.addCell(cell8);
	    }
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    
	    return table;
	}
	
	private PdfPTable prepareEndingTable(HttpServletRequest request, Document document) {
		PdfPTable table = new PdfPTable(2);

		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.stamp", request.getLocale()), arial11));
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell1.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.rector", request.getLocale()), arial11));
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell2);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    return table;
	}
}