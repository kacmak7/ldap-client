package org.parafia.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.ObjectTraverser;
import org.parafia.model.Child;
import org.parafia.model.Family;
import org.parafia.model.Other;
import org.parafia.model.Person;
import org.parafia.model.PriestlyVisit;
import org.parafia.service.FamilyManager;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PrintFamilyController extends BaseController {
	private FamilyManager familyManager;
	
	public void setFamilyManager(FamilyManager familyManager) {
		this.familyManager = familyManager;
	}

	private final Logger log = Logger.getLogger(getClass());
	
	//private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	private Font arial11;
	private Font arial9;
	private Font arialbold16;
	
	public PrintFamilyController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			//BaseFont times = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial9 = new Font(font,9);
			arial11 = new Font(font,11);
			arialbold16 = new Font(font,16,Font.BOLD);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Family family = null;
		if (request.getParameter("familyId") != null)
			family = familyManager.get(Long.valueOf(request.getParameter("familyId")));
		else
			family = new Family();
		
		prepareResponse(request, response, family);
		
		return null;
	}
	
	

	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, Family family) {
		response.setHeader("Content-Disposition","attachment; filename=\"" + family.getSurname() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document = new Document(PageSize.A4, 20, 20, 40, 40);
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					response.getOutputStream());
			writer.setPageEvent(new HeaderFooter(request, document, family));
			document.open();
			
			document.add(prepareHeader(request, document));
			document.add(prepareMainFamilyTable(request, document, family));
			document.add(prepareWifeHusbandTable(request, document, family));
			document.add(prepareMarriageStatusTable(request, document, family));
			if (family.getChildren().size() > 0) {
				document.add(prepareChildrenHeaderTable(request, document, family));
				document.add(prepareChildrenTable(request, document, family));
			}
			if (family.getOthers().size() > 0) {
				document.add(prepareOthersHeaderTable(request, document, family));
				document.add(prepareOthersTable(request, document, family));
			}
			document.add(prepareSummaryTable(request, document, family));
			if (family.getVisits().size() > 0)
				document.add(prepareVisitsTable(request, document, family));
			
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
		Phrase p1 = new Phrase(getText("print.files.header", new Object[]{}, request.getLocale()), arialbold16);
	    PdfPTable table = new PdfPTable(1);
	    PdfPCell cell = new PdfPCell(p1);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(20f);
	    return table;
	}
	
	private PdfPTable prepareMainFamilyTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable t = new PdfPTable(new float[]{40f, 40f, 20f});
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.files.family-surname", new Object[]{family.getSurname()}, request.getLocale()), arial11));
		cell1.setBorder(PdfPCell.NO_BORDER);
		t.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.files.family-address", new Object[]{family.getAddress().getFullString()}, request.getLocale()), arial11));
		cell2.setBorder(PdfPCell.NO_BORDER);
		t.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.files.family-phone", new Object[]{family.getPhone()!=null?family.getPhone():"--"}, request.getLocale()), arial11));
		cell3.setBorder(PdfPCell.NO_BORDER);
		t.addCell(cell3);
		
		PdfPTable table = new PdfPTable(new float[]{80f, 10f, 10f});
		table.addCell(t);
		table.addCell(new Phrase(getText("print.family.take-part-mass", request.getLocale()), arial9));
		table.addCell(new Phrase(getText("print.family.take-part-sacrament", request.getLocale()), arial9));
		
		return table;
	}
	
	
	private PdfPTable prepareWifeHusbandTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[] {10f, 35f, 35f, 10f, 10f});
		
		//husband
		//family = (Family) ObjectTraverser.fillStrings(family, null);
		if (family.getHusband() != null) {
			table.addCell(new PdfPCell(new Phrase(getText("print.family.husband", request.getLocale()), arial11)));
			
			PdfPTable t = new PdfPTable(1);
			PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.files.name", new Object[] {family.getHusband().getFullName()}, request.getLocale()), arial11));
			cell1.setBorder(PdfPCell.NO_BORDER);
			t.addCell(cell1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.files.birth-date", new Object[] {family.getHusband().getBirthDate()}, request.getLocale()), arial11));
			cell2.setBorder(PdfPCell.NO_BORDER);
			t.addCell(cell2);
			table.addCell(t);
			
			table.addCell(new PdfPCell(new Phrase(getText("print.files.job", new Object[]{family.getHusband().getJob()}, request.getLocale()), arial11)));
			table.addCell(new PdfPCell(new Phrase(family.getHusband().getTakePartMass().getName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(family.getHusband().getTakePartSacrament().getName(), arial11)));
		}
		
		//wife
		
		if (family.getWife() != null) {
			table.addCell(new PdfPCell(new Phrase(getText("print.family.wife", request.getLocale()), arial11)));
			
			PdfPTable t = new PdfPTable(1);
			PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.files.name", new Object[] {family.getWife().getFullName()}, request.getLocale()), arial11));
			cell1.setBorder(PdfPCell.NO_BORDER);
			t.addCell(cell1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.files.birth-date", new Object[] {family.getWife().getBirthDate()}, request.getLocale()), arial11));
			cell2.setBorder(PdfPCell.NO_BORDER);
			t.addCell(cell2);
			table.addCell(t);
			
			table.addCell(new PdfPCell(new Phrase(getText("print.files.job", new Object[]{family.getWife().getJob()}, request.getLocale()), arial11)));
			table.addCell(new PdfPCell(new Phrase(family.getWife().getTakePartMass().getName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(family.getWife().getTakePartSacrament().getName(), arial11)));
		}
		
		return table;
	}
	
	
	private PdfPTable prepareMarriageStatusTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(1);
		
		table.addCell(new PdfPCell(new Phrase(getText("print.files.marriage-status", new Object[]{family.getMarriageStatus().getName()}, request.getLocale()), arial11)));
		
		return table;
	}
	
	private PdfPTable prepareChildrenHeaderTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[]{30f, 15f, 35f, 20f});
		
		table.addCell(new PdfPCell(new Phrase(getText("print.files.children", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.family.birthdate", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.family.religious-formation", request.getLocale()), arial11)));
		table.addCell(new PdfPCell());
		
		return table;
	}
	
	private PdfPTable prepareChildrenTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[] {30f, 15f, 35f, 10f, 10f});
		
		for (Child child : family.getChildren()) {
			table.addCell(new PdfPCell(new Phrase(child.getFullName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(child.getBirthDate(), arial11)));
			table.addCell(new PdfPCell(new Phrase(child.getReligiousFormation(), arial11)));
			table.addCell(new PdfPCell(new Phrase(child.getTakePartMass().getName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(child.getTakePartSacrament().getName(), arial11)));
		}
		
		return table;
	}
	
	
	private PdfPTable prepareOthersHeaderTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[]{30f, 15f, 35f, 20f});
		
		table.addCell(new PdfPCell(new Phrase(getText("print.files.others", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.family.birthdate", request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.family.religious-formation", request.getLocale()), arial11)));
		table.addCell(new PdfPCell());
		
		return table;
	}
	
	private PdfPTable prepareOthersTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[] {30f, 15f, 35f, 10f, 10f});
		
		for (Other other : family.getOthers()) {
			table.addCell(new PdfPCell(new Phrase(other.getFullName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(other.getBirthDate(), arial11)));
			table.addCell(new PdfPCell(new Phrase(other.getReligiousFormation(), arial11)));
			table.addCell(new PdfPCell(new Phrase(other.getTakePartMass().getName(), arial11)));
			table.addCell(new PdfPCell(new Phrase(other.getTakePartSacrament().getName(), arial11)));
		}
		
		return table;
	}
	
	
	private PdfPTable prepareSummaryTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(1);
		
		table.addCell(new PdfPCell(new Phrase(getText("print.files.remarks", new Object[]{family.getPriestRemarks()}, request.getLocale()), arial11)));
		table.addCell(new PdfPCell(new Phrase(getText("print.files.material-situation", new Object[]{family.getMaterialSituation()}, request.getLocale()), arial11)));
		
		table.setSpacingBefore(20f);
		
		return table;
	}
	
	
	private PdfPTable prepareVisitsTable(HttpServletRequest request, Document document, Family family) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[] {20, 80});
		
		table.addCell(new PdfPCell(new Phrase(getText("print.files.visit-dates", request.getLocale()), arial9)));
		table.addCell(new PdfPCell(new Phrase(getText("print.files.visit-remarks", request.getLocale()), arial9)));
		
		for (PriestlyVisit visit : family.getVisitsSorted()) {
			table.addCell(new PdfPCell(new Phrase(visit.getDate(), arial11)));
			table.addCell(new PdfPCell(new Phrase(visit.getRemarks(), arial11)));
		}
		
		table.setSpacingBefore(20f);
		
		return table;
	}

	
	class HeaderFooter extends PdfPageEventHelper {
		/** The Phrase that will be added as the header of the document. */
		protected PdfPTable header;
	 
		/** The PdfPTable that will be added as the footer of the document. */
		protected PdfPTable footer;
		
		private PdfPTable prepareFooter(HttpServletRequest request, Document document) {
			PdfPTable table = new PdfPTable(1);
			
	        PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.parish", request.getLocale()), arial11));
	        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setBorder(PdfPCell.TOP);
	        PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.parish-address", request.getLocale()), arial11));
	        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell2.setBorder(PdfPCell.NO_BORDER);
			
	        table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	        table.addCell(cell1);
	        table.addCell(cell2);
	        
	        return table;
		}
	 
		/**
		 * Constructs an Event that adds a Header and a Footer.
		 * @throws DocumentException 
		 */
		public HeaderFooter(HttpServletRequest request, Document document, Family family) throws DocumentException {
			//header = prepareHeader(request, document);
			footer = prepareFooter(request, document);
		}
	 
		/**
		 * @see com.lowagie.text.pdf.PdfPageEvent#onEndPage(com.lowagie.text.pdf.PdfWriter,
		 *      com.lowagie.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			//if (document.getPageNumber() > 1) {
			/*ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
					(document.right() - document.left()) / 2
							+ document.leftMargin(), document.top() + 10, 0);*/
			/*header.writeSelectedRows(0, -1,
					(document.right() - document.left() - header.getTotalWidth()) / 2
							+ document.leftMargin(), document.top() + 10, cb);*/
			
			footer.writeSelectedRows(0, -1,
					(document.right() - document.left() - footer.getTotalWidth()) / 2
							+ document.leftMargin(), document.bottom() + 10, cb);
		}
	}
}
