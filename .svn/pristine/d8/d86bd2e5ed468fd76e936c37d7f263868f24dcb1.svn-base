package org.parafia.webapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;
import org.parafia.util.DateUtil;
import org.springframework.web.servlet.ModelAndView;

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

public class PrintCertificateController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	//private Font arial9;
	//private Font arialunderline9;
	//private Font arialbold11;
	
	//private String dots;
	
	private FianceeManager fianceeManager;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintCertificateController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			//arial9 = new Font(font,9);
			//arialunderline9 = new Font(font,9,Font.UNDERLINE);
			arial11 = new Font(font,11,Font.BOLD);
			//arialbold11 = new Font(font,11,Font.BOLD);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//dots = getText("print.empty-space", request.getLocale());

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
		
		Document document = new Document(PageSize.A4, 20, 20, 3, 40);			//margin-top to trzecia pozycja
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			/*Paragraph p1 = new Paragraph(new Phrase(getText("print.certificate.header-date", new Object[]{df.format(new Date())}, request.getLocale()), arial11));
			p1.setAlignment(Paragraph.ALIGN_RIGHT);
			p1.setSpacingAfter(20f);
			document.add(p1);*/
			
			document.add(prepareHeaderTable(request, document, fp));
			document.add(prepareIntroTable(request, document, fp));
			document.add(prepareWitnessTable(request, document, fp));
			//document.add(prepareWitnessSignatureTable(request, document));
			document.add(prepareMarriageBookTable(request, document, fp));
			//document.add(prepareRemarksTable(request, document, fp));

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
		PdfPTable table = new PdfPTable(new float[]{65f, 35f});
		
		PdfPCell cell1 = new PdfPCell(new Phrase("    " + fp.getCertificate().getNumber(), arial11));
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell1);
	    
	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.certificate.header-date", new Object[]{df.format(fp.getMarriageDate())}, request.getLocale()), arial11));
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell2);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(82f);
	    return table;
	}
	
	private PdfPTable prepareIntroTable(HttpServletRequest request, Document document, FianceePair fp) {
		/*Phrase p1 = new Phrase(getText("print.certificate.intro",
			new Object[]{df.format(new Date()), fp.getCertificate().getPriestFullName(), fp.getFianceeHe().getFullName(), fp.getFianceeShe().getFullName()},
			request.getLocale()), arial11);*/
		PdfPTable table = new PdfPTable(1);
		float extraParagraphSpace = 19.5f;

		Phrase p1 = new Phrase("                  " + DateUtil.getPolishFormatDate(fp.getMarriageDate()), arial11);
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setExtraParagraphSpace(23f);
	    table.addCell(cell1);
	    
	    Phrase p2 = null;
	    if ("".equals(fp.getCertificate().getPriestFullName()) || "".equals(fp.getCertificate().getPriestPosition()))
	    	p2 = new Phrase(" ", arial11);
	    else	
	    	p2 = new Phrase(fp.getCertificate().getPriestFullName() + " - " + fp.getCertificate().getPriestPosition(), arial11);
	    
	    PdfPCell cell2 = new PdfPCell(p2);
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setExtraParagraphSpace(18.5f);
	    table.addCell(cell2);
	    
	    Phrase p3 = new Phrase(getText("print.certificate.place", request.getLocale()), arial11);
	    PdfPCell cell3 = new PdfPCell(p3);
	    cell3.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setExtraParagraphSpace(18f);
	    table.addCell(cell3);
	    
	    Phrase p4 = new Phrase(fp.getFianceeHe().getFullName(), arial11);
	    PdfPCell cell4 = new PdfPCell(p4);
	    cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    cell4.setExtraParagraphSpace(18f);
	    table.addCell(cell4);
	    
	    Phrase p5 = new Phrase(fp.getFianceeShe().getFullName(), arial11);
	    PdfPCell cell5 = new PdfPCell(p5);
	    cell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell5.setBorder(PdfPCell.NO_BORDER);
	    cell5.setExtraParagraphSpace(18f);
	    table.addCell(cell5);
	    
	    Phrase p6 = new Phrase("                          " + getText("print.certificate.church", request.getLocale()), arial11);
	    PdfPCell cell6 = new PdfPCell(p6);
	    cell6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell6.setBorder(PdfPCell.NO_BORDER);
	    cell6.setExtraParagraphSpace(extraParagraphSpace);
	    table.addCell(cell6);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(67f);
	    return table;
	}
	
	private PdfPTable prepareWitnessTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(1);
		
	    Phrase p1 = new Phrase("                             " + fp.getCertificate().getFirstWitnessFullName(), arial11);
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setExtraParagraphSpace(5f);
	    table.addCell(cell1);
	    
	    Phrase p2 = new Phrase("                             " + fp.getCertificate().getSecondWitnessFullName(), arial11);
	    PdfPCell cell2 = new PdfPCell(p2);
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setExtraParagraphSpace(8f);
	    table.addCell(cell2);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(83f);
	    return table;
	}
	
	
	private PdfPTable prepareMarriageBookTable(HttpServletRequest request, Document document, FianceePair fp) {
		PdfPTable table = new PdfPTable(1);
		
		Phrase p1 = new Phrase("                                                                                " + fp.getCertificate().getMarriageNumber(), arial11);
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setExtraParagraphSpace(25f);
	    table.addCell(cell1);
		
	    Phrase p2 = new Phrase("                                       " + getText("print.certificate.place", request.getLocale()), arial11);
	    PdfPCell cell2 = new PdfPCell(p2);
	    cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setExtraParagraphSpace(8f);
	    table.addCell(cell2);
		
		return table;
	}
}