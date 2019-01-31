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
import org.parafia.model.FianceePair;
import org.parafia.service.FianceeManager;
import org.parafia.util.DateUtil;
import org.parafia.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PrintLicenceController extends BaseController {
	private final Logger log = Logger.getLogger(getClass());
	
	private Font arial11;
	private Font arialitalic7;
	private Font arialbold11;
	
	private String dots;
	private String longerDots;
	private String shorterDots;
	
	private FianceeManager fianceeManager;
	
	private final float SPACE_MULTIPLER = 2.67f;
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintLicenceController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arialitalic7 = new Font(font,7,Font.ITALIC);
			arial11 = new Font(font,11);
			arialbold11 = new Font(font,11,Font.BOLD);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		dots = getText("print.empty-space", request.getLocale());
		longerDots = getText("print.empty-space-longer", request.getLocale());
		shorterDots = getText("print.empty-space-shorter", request.getLocale());

		FianceePair fp = null;
		if (StringUtils.isNotEmpty(request.getParameter("fianceePairId")) && StringUtils.isNumeric(request.getParameter("fianceePairId")))
			fp = fianceeManager.get(Long.valueOf(request.getParameter("fianceePairId")));
		else
			fp = new FianceePair();
		
		if (!validate(fp)) {
			saveError(request, getText("errors.licence", request.getLocale()));
			
			Map<String, Long> params = new HashMap<String, Long>();
			params.put(Constants.FIANCEE_PAIR_ID, fp.getId());
			//return new ModelAndView("redirect:/print/fianceesform.html").addObject(Constants.FIANCEE_PAIR_ID, fp.getId());
			return new ModelAndView("common/closeRedirectParent").addObject("redirectUrl", "/print/fianceesform.html")
				.addObject("urlParams", params);
		} else {
			prepareResponse(request, response, fp);
			return null;
		}
	}
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, FianceePair fp) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + fp.getFianceeHe().getFullName() + fp.getFianceeShe().getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		String polish = "";
		if (request.getParameter("polish") != null)
			polish = "polish.";
		
		Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		PdfWriter writer = null;
		
		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			PdfContentByte cb = writer.getDirectContent();
					
			document.add(prepareHeaderTable(request, document, polish));
			//prepareHeaderText(request, document, cb);
			
			Paragraph p1 = new Paragraph(new Phrase(getText("print.licence."+polish+"header1", request.getLocale()).toUpperCase(), arialbold11));
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p1.setSpacingAfter(20f);
			document.add(p1);
			
			//prepareIntroText(request, document, cb, fp);
			
			document.add(prepareIntroTable(request, document, fp, polish));
			document.add(prepareSecondTable(request, document, fp, polish));
			
			//prepareSecondText(request, document, cb, fp);
			
			document.add(prepareThirdTable(request, document, fp));
			document.add(prepareFourthTable(request, document, fp));

			//addPolishText(cb, document);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
			if (writer != null)
				writer.close();
		}			
	}
	
	
	private PdfPTable prepareHeaderTable(HttpServletRequest request, Document document, String polish) {
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.licence."+polish+"header", new Object[]{shorterDots, shorterDots, shorterDots, shorterDots}, request.getLocale()), arial11));
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setLeading(0f, 1.7f);
	    table.addCell(cell1);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private void prepareHeaderText(HttpServletRequest request, Document document, PdfContentByte cb) throws DocumentException {
		/*cb.beginText();
		cb.setTextMatrix(130, 500);
		cb.setFontAndSize(arialitalic9.getBaseFont(), 9);
		cb.showText(getText("print.licence.polish.archdiocese", request.getLocale()));
		cb.endText();*/
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 0,
		200, PageSize.A4.height() - 50, 
		18.5f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.header", request.getLocale()), arialitalic7));
		ct.go();
	}
	
	private PdfPTable prepareIntroTable(HttpServletRequest request, Document document, FianceePair fp, String polish) {
		Phrase p1 = new Phrase(getText("print.licence."+polish+"intro", new Object[]{longerDots, longerDots, dots},
			request.getLocale()), arial11);
		PdfPTable table = new PdfPTable(1);
		
	    PdfPCell cell1 = new PdfPCell(p1);
	    cell1.setBorder(PdfPCell.NO_BORDER);
	    cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	    cell1.setLeading(0f, 1.7f);
	    table.addCell(cell1);
	    
	    PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.licence."+polish+"fiancee-he1", new Object[]{fp.getFianceeHe().getFullName()}, 
	    		request.getLocale()), arialbold11));
	    cell2.setBorder(PdfPCell.NO_BORDER);
	    cell2.setLeading(0f, 1.7f);
	    table.addCell(cell2);
	    
	    /*PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.licence.fiancee-he2",
	    		new Object[]{fp.getFianceeHe().getAddress().getFullString(), df.format(fp.getFianceeHe().getBirthDate()),
	    		fp.getFianceeHe().getBirthPlace(), fp.getFianceeHe().getFathersName(), fp.getFianceeHe().getMothersName(),
	    		fp.getFianceeHe().getReligion(), df.format(fp.getFianceeHe().getBaptismDate()),
	    		fp.getFianceeHe().getBaptismChurch(), fp.getFianceeHe().getBaptismChurchIn(), fp.getFianceeHe().getBaptismNumber(), fp.getFianceeHe().getChurchPostAddress(),
	    		df.format(fp.getFianceeHe().getConfirmationDate()), fp.getFianceeHe().getConfirmationChurch()}, 
	    		request.getLocale()), arial11));*/
	    Phrase p2 = new Phrase();
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getAddress().getFullString(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_2", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getBirthDate(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_3", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getBirthPlace(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_4", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getFathersFullName(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_5", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMothersFullName(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_6", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getReligion(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_7", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismDate(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_8", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurch(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_9", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurchIn(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_10", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getBaptismNumber(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_11", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getChurchPostAddress(), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_12", request.getLocale()), arial11));
	    p2.add(new Chunk(df.format(fp.getFianceeHe().getRemaining().getMarriage().getConfirmationDate()), arialbold11));
	    p2.add(new Chunk(getText("print.licence."+polish+"fiancee-he2_13", request.getLocale()), arial11));
	    p2.add(new Chunk(fp.getFianceeHe().getRemaining().getMarriage().getConfirmationChurch(), arialbold11));
	    
	    PdfPCell cell3 = new PdfPCell(p2);
	    cell3.setBorder(PdfPCell.NO_BORDER);
	    cell3.setLeading(0f, 1.7f);
	    table.addCell(cell3);
	    
	    PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.licence."+polish+"fiancee-she1", new Object[]{fp.getFianceeShe().getFullName()}, 
	    		request.getLocale()), arialbold11));
	    cell4.setBorder(PdfPCell.NO_BORDER);
	    cell4.setLeading(0f, 1.7f);
	    table.addCell(cell4);
	    
	    Phrase p3 = new Phrase();
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getAddress().getFullString(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_2", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getBirthDate(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_3", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getBirthPlace(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_4", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getFathersFullName(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_5", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMothersFullName(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_6", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getReligion(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_7", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismDate(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_8", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurch(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_9", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurchIn(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_10", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getBaptismNumber(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_11", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getChurchPostAddress(), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_12", request.getLocale()), arial11));
	    p3.add(new Chunk(df.format(fp.getFianceeShe().getRemaining().getMarriage().getConfirmationDate()), arialbold11));
	    p3.add(new Chunk(getText("print.licence."+polish+"fiancee-she2_13", request.getLocale()), arial11));
	    p3.add(new Chunk(fp.getFianceeShe().getRemaining().getMarriage().getConfirmationChurch(), arialbold11));
	    
	    /*PdfPCell cell5 = new PdfPCell(new Phrase(getText("print.licence.fiancee-she2",
	    		new Object[]{fp.getFianceeShe().getAddress().getFullString(), df.format(fp.getFianceeShe().getBirthDate()),
	    		fp.getFianceeShe().getBirthPlace(), fp.getFianceeShe().getFathersName(), fp.getFianceeShe().getMothersName(),
	    		fp.getFianceeShe().getReligion(), df.format(fp.getFianceeShe().getBaptismDate()),
	    		fp.getFianceeShe().getBaptismChurch(), fp.getFianceeShe().getBaptismChurchIn(), fp.getFianceeShe().getBaptismNumber(), fp.getFianceeShe().getChurchPostAddress(),
	    		df.format(fp.getFianceeShe().getConfirmationDate()), fp.getFianceeShe().getConfirmationChurch()}, 
	    		request.getLocale()), arial11));*/
	    PdfPCell cell5 = new PdfPCell(p3);
	    cell5.setBorder(PdfPCell.NO_BORDER);
	    cell5.setLeading(0f, 1.7f);
	    table.addCell(cell5);
	    
	    PdfPCell cell6 = new PdfPCell(new Phrase(getText("print.licence."+polish+"fiancees", 
	    		request.getLocale()), arial11));
	    cell6.setBorder(PdfPCell.NO_BORDER);
	    cell6.setLeading(0f, 1.7f);
	    table.addCell(cell6);
	    
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	
	private void prepareIntroText(HttpServletRequest request, Document document, PdfContentByte cb, FianceePair fp) throws DocumentException {
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(0, 150,
		PageSize.A4.width(), PageSize.A4.height() - 185, 
		16, Element.ALIGN_CENTER);
		ct.setText(new Phrase(getText("print.licence.polish.intro", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 222,
		18.5f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.intro1", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 320,
		17, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.fiancee-he1", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 341,
		18.6f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.fiancee-he2",
			new Object[]{
				StringUtil.getSpaces(fp.getFianceeHe().getRemaining().getFathersFullName(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeHe().getRemaining().getMarriage().getReligion(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeHe().getRemaining().getMarriage().getBaptismChurch(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeHe().getRemaining().getMarriage().getBaptismNumber(), SPACE_MULTIPLER)
			},
			request.getLocale()), arialitalic7));
		ct.go();
		
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 478,
		17, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.fiancee-she1", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 499,
		18.6f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.fiancee-she2",
			new Object[]{
				StringUtil.getSpaces(fp.getFianceeShe().getRemaining().getFathersFullName(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeShe().getRemaining().getMarriage().getReligion(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeShe().getRemaining().getMarriage().getBaptismChurch(), SPACE_MULTIPLER),
				StringUtil.getSpaces(fp.getFianceeShe().getRemaining().getMarriage().getBaptismNumber(), SPACE_MULTIPLER)
			},
			request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 150,
		PageSize.A4.width(), PageSize.A4.height() - 634,
		18.6f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.fiancees", request.getLocale()), arialitalic7));
		ct.go();
	}
	
	
	private PdfPTable prepareSecondTable(HttpServletRequest request, Document document, FianceePair fp, String polish) {
		Date date = new Date();
		DateFormat fullDf;
		if (polish.length() == 0)
			fullDf = new SimpleDateFormat("dd MMMMM 'anni' yyyy");
		else
			fullDf = new SimpleDateFormat("dd MMMMM yyyy 'roku'");
		
		PdfPTable table = new PdfPTable(2);
		
		String year = (fp.getProtocoleDate() == null? "..................." : String.valueOf(new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR).format(fp.getProtocoleDate())));
		String number = (fp.getProtocoleNumber() == null? "......................" : String.valueOf(fp.getProtocoleNumber()));
		
		Phrase phrase = new Phrase(getText("print.licence."+polish+"certify",
				new Object[]{year, number, "...........................", "...........................", "......................................................"},
				request.getLocale()), arial11);
		
		PdfPCell cell1 = new PdfPCell(phrase);
		cell1.setColspan(2);
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setLeading(0f, 1.7f);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		cell1.setExtraParagraphSpace(20f);
		table.addCell(cell1);
		
		PdfPCell cell2 = null;
		if (polish.length() == 0)
			cell2 = new PdfPCell(new Phrase(getText("print.licence."+polish+"date", new Object[]{fullDf.format(date)}, request.getLocale()), arial11));
		else
			cell2 = new PdfPCell(new Phrase(getText("print.licence."+polish+"date", new Object[]{DateUtil.getPolishFormatDate(date)}, request.getLocale()), arial11));
			//getPolishFormatDate
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setColspan(2);
		cell2.setExtraParagraphSpace(40f);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.licence."+polish+"stamp", request.getLocale()), arial11));
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.licence."+polish+"rector", request.getLocale()), arial11));
		cell4.setBorder(PdfPCell.NO_BORDER);
		cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell4);
		
		PdfPCell cell5 = new PdfPCell();
		cell5.setBorder(PdfPCell.BOTTOM);
		cell5.setPaddingBottom(20f);
		cell5.setColspan(2);
		table.addCell(cell5);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
		table.setSpacingAfter(30f);
		return table;
	}
	
	
	private void prepareSecondText(HttpServletRequest request, Document document, PdfContentByte cb, FianceePair fp) throws DocumentException {
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 0,
		PageSize.A4.width() - 80, PageSize.A4.height() - 48,
		18.6f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.second", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 0,
		PageSize.A4.width() - 80, PageSize.A4.height() - 199,
		18.6f, Element.ALIGN_LEFT);
		ct.setText(new Phrase(getText("print.licence.polish.second2", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(80, 0,
			PageSize.A4.width() - 80, PageSize.A4.height() - 253,
			18.6f, Element.ALIGN_LEFT);
			ct.setText(new Phrase(getText("print.licence.polish.date",
				new Object[]{"                        ", "                             "},
				request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(174, 0,
			300, PageSize.A4.height() - 320,
			18.6f, Element.ALIGN_LEFT);
			ct.setText(new Phrase(getText("print.licence.polish.stamp", request.getLocale()), arialitalic7));
		ct.go();
		
		ct = new ColumnText(cb);
		ct.setSimpleColumn(392, 0,
			500, PageSize.A4.height() - 320,
			18.6f, Element.ALIGN_LEFT);
			ct.setText(new Phrase(getText("print.licence.polish.rector", request.getLocale()), arialitalic7));
		ct.go();
	}
	
	
	private PdfPTable prepareThirdTable(HttpServletRequest request, Document document, FianceePair fp) {
		Date date = new Date();
		DateFormat dayDf = new SimpleDateFormat("dd MMMMM");
		
		PdfPTable table = new PdfPTable(2);
		
		Phrase phrase = new Phrase(getText("print.licence.third", request.getLocale()), arial11);
		
		PdfPCell cell1 = new PdfPCell(phrase);
		cell1.setColspan(2);
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setLeading(0f, 1.7f);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		cell1.setPaddingBottom(20f);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.licence.third-date", new Object[]{dayDf.format(date), "......................................"}, request.getLocale()), arial11));
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setColspan(2);
		cell2.setExtraParagraphSpace(40f);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.licence.stamp", request.getLocale()), arial11));
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Phrase(getText("print.licence.vicar", request.getLocale()), arial11));
		cell4.setBorder(PdfPCell.NO_BORDER);
		cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell4);
		
		PdfPCell cell5 = new PdfPCell();
		cell5.setBorder(PdfPCell.BOTTOM);
		cell5.setPaddingBottom(20f);
		cell5.setColspan(2);
		table.addCell(cell5);
		
		table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
		table.setSpacingAfter(30f);
		return table;
	}
	
	
	private PdfPTable prepareFourthTable(HttpServletRequest request, Document document, FianceePair fp) {
		Date date = new Date();
		DateFormat fullDf = new SimpleDateFormat("dd MMMMM yyyy");
		
		PdfPTable table = new PdfPTable(2);
		
		Phrase phrase = new Phrase(getText("print.licence.fourth",
				new Object[]{"............................................................................................................"}, request.getLocale()), arial11);
		
		PdfPCell cell1 = new PdfPCell(phrase);
		cell1.setColspan(2);
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setLeading(0f, 1.7f);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		cell1.setPaddingBottom(20f);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.licence.fourth-date", new Object[]{fullDf.format(date)}, request.getLocale()), arial11));
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setColspan(2);
		cell2.setExtraParagraphSpace(40f);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.licence.stamp", request.getLocale()), arial11));
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell3);
		
		PdfPCell cell4 = new PdfPCell(new Phrase(".........................................", arial11));
		cell4.setBorder(PdfPCell.NO_BORDER);
		cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell4);
		
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