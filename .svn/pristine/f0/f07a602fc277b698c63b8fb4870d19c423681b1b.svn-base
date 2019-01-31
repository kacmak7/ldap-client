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
import org.parafia.model.Parent;
import org.parafia.model.Person;
import org.parafia.service.FianceeManager;
import org.parafia.service.PersonManager;
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

public class PrintBapConDeaCertificateController extends BaseController {
	private PersonManager personManager;
	private FianceeManager fianceeManager;

	private final String BAPTISM = "baptism";
	private final String CONFIRMATION = "confirmation";
	private final String MARRIAGE = "marriage";
	private final String DEATH = "death";
	
	private final int MAX_REMARKS_LENGTH = 70;
	
	private final Logger log = Logger.getLogger(getClass());
	
	private final DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	private Font arial11;
	private Font arial13;
	private Font arialbold11;
	private Font arialbold13;
	private Font arial8;
	private Font arialbold15;
	//private Font arialitalic7;
	
	//private final float SPACE_MULTIPLER = 2.67f;
	
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
	
	public void setFianceeManager(FianceeManager fianceeManager) {
		this.fianceeManager = fianceeManager;
	}

	public PrintBapConDeaCertificateController(String fontFileLocation) {
		try {
			log.debug("Font is taken from " + fontFileLocation);
			FontFactory.register(fontFileLocation);
			//BaseFont times = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
			BaseFont font = BaseFont.createFont(fontFileLocation, BaseFont.IDENTITY_H, BaseFont.CACHED);
			arial8 = new Font(font,8);
			arial11 = new Font(font,11);
			arial13 = new Font(font,13);
			arialbold11 = new Font(font,11, Font.BOLD);
			arialbold13 = new Font(font,13, Font.BOLD);
			arialbold15 = new Font(font,15,Font.BOLD);
			//arialitalic7 = new Font(font,7,Font.ITALIC);
		} catch (DocumentException de) {
			log.error(de);
		} catch (IOException ie) {
			log.error(ie);
		}
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Person person = null;
		FianceePair fianceePair = null;
		
		if (StringUtils.isNumeric(request.getParameter("personId")) && !"".equals(request.getParameter("personId")))
			person = personManager.get(Long.valueOf(request.getParameter("personId")));
		else if (MARRIAGE.equals(request.getParameter("sort"))) {
			fianceePair = fianceeManager.get(Long.valueOf(request.getParameter(Constants.FIANCEE_PAIR_ID)));
		}
		
		if (request.getParameter("sort") != null) {
			if (!validate(request.getParameter("sort"), person, fianceePair)) {
				
				if (BAPTISM.equals(request.getParameter("sort")))
					saveError(request, getText("errors.baptismcertificate", request.getLocale()));
				else if (CONFIRMATION.equals(request.getParameter("sort")))
					saveError(request, getText("errors.confirmationcertificate", request.getLocale()));
				else if (MARRIAGE.equals(request.getParameter("sort")))
					saveError(request, getText("errors.marriagecertificate", request.getLocale()));
				else if (DEATH.equals(request.getParameter("sort")))
					saveError(request, getText("errors.deathcertificate", request.getLocale()));
				
				Map<String, Long> params = new HashMap<String, Long>();
				params.put("personId", person.getId());
				return new ModelAndView("common/closeRedirectParent").addObject("redirectUrl", "/print/personform.html")
					.addObject("urlParams", params);
			}
			
			prepareResponse(request, response, person, request.getParameter("sort"), fianceePair);
		}
		
		return null;
	}
	
	
	public void prepareResponse(HttpServletRequest request, HttpServletResponse response, Person person,
			String sort, FianceePair fp) {
		if (sort == null)
			return;
		
		if (person != null)
			response.setHeader("Content-Disposition","attachment; filename=\"" + person.getFullName() + ".pdf\"");
		else
			response.setHeader("Content-Disposition","attachment; filename=\"" + fp.getFianceeHe().getFullName() + ".pdf\"");
		response.setContentType("application/pdf");
		
		Document document;
		
		if (BAPTISM.equals(sort) && (person.getRemaining().getBaptism().getConfirmationRemarks().length() > MAX_REMARKS_LENGTH ||
				person.getRemaining().getBaptism().getMarriageRemarks().length() > MAX_REMARKS_LENGTH ||
				person.getRemaining().getBaptism().getOtherRemarks().length() > MAX_REMARKS_LENGTH)) {
			document = new Document(PageSize.A4, 20, 20, 40, 40);
		} else
			document = new Document(PageSize.A5.rotate(), 20, 20, 40, 10);		//zgodnie z mailem od P. Malinowskiej swiadectwo chrztu jest formatu A5
		//else
		//	document = new Document(PageSize.A4, 20, 20, 40, 40);
		
		PdfWriter writer = null;
		
		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			//PdfContentByte cb = writer.getDirectContent();
			
			String latin = "";
			if (request.getParameter("latin") != null) {
				latin = "-latin";
			}
			
			document.add(prepareHeaderTable(request, document, sort, latin));
			document.add(prepareTextTable(request, document, person, sort, latin, fp));
			document.add(prepareEndTable(request, document, sort, latin));
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
	
	private PdfPTable prepareHeaderTable(HttpServletRequest request, Document document, String sort, String latin) {
		PdfPTable table = new PdfPTable(1);
		
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-certificate.archdiocese" + latin, request.getLocale()), arial8));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setExtraParagraphSpace(20f);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Phrase(getText("print.baptism-certificate.stamp" + latin, request.getLocale()), arial8));
		cell2.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell2);
		
		PdfPCell cell3;
		if (BAPTISM.equals(sort))
			cell3 = new PdfPCell(new Phrase(getText("print.baptism-certificate.baptism" + latin, request.getLocale()), arialbold15));
		else if (CONFIRMATION.equals(sort))
			cell3 = new PdfPCell(new Phrase(getText("print.baptism-certificate.confirmation" + latin, request.getLocale()), arialbold15));
		else if (MARRIAGE.equals(sort))
			cell3 = new PdfPCell(new Phrase(getText("print.baptism-certificate.marriage" + latin, request.getLocale()), arialbold15));
		else if (DEATH.equals(sort))
			cell3 = new PdfPCell(new Phrase(getText("print.baptism-certificate.death" + latin, request.getLocale()), arialbold15));
		else
			cell3 = new PdfPCell();
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell3.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell3);
		
	    table.setTotalWidth(PageSize.A4.width() - document.leftMargin() - document.rightMargin());
	    table.setSpacingAfter(10f);
	    return table;
	}
	
	private PdfPTable prepareTextTable(HttpServletRequest request, Document document, Person person, String sort, String latin, FianceePair fp) throws DocumentException {
		DateFormat yearDf = new SimpleDateFormat("yyyy");
		PdfPTable table = new PdfPTable(1);
		//PdfPCell cell1 = null;
		Phrase p1 = null;
		if (BAPTISM.equals(sort)) {
			if (person.getRemaining().getBaptism().getReason() != null) {
				PdfPCell cell0 = new PdfPCell(new Phrase(new Chunk(person.getRemaining().getBaptism().getReason(), arial13)));
				cell0.setBorder(PdfPCell.NO_BORDER);
				cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(cell0);
			}
			
			p1 = new Phrase();
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin, request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getDate().substring(0, 4), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_2", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getFileNumber(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_3", request.getLocale()), arial11));
			p1.add(new Chunk(person.getFullName(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_4", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getFathersFullName(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_5", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getMothersFullName(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_6", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getMothersMaidensName(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_7", request.getLocale()), arial11));
			p1.add(new Chunk(person.getBirthDate() + " " + person.getRemaining().getBirthPlace(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_8", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getDateDaysFirst() + " " + person.getRemaining().getBaptism().getPlace(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_9", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getConfirmationRemarks(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_10", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getMarriageRemarks(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-baptism" + latin + "_11", request.getLocale()), arial11));
			p1.add(new Chunk(person.getRemaining().getBaptism().getOtherRemarks(), arialbold11));
		} else if (CONFIRMATION.equals(sort)) {
			p1 = new Phrase();
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation", request.getLocale()), arial13));
			p1.add(new Chunk(yearDf.format(person.getRemaining().getConfirmation().getDate()), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_2", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getConfirmation().getFileNumber(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_3", request.getLocale()), arial13));
			p1.add(new Chunk(person.getFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_4", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getFathersFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_5", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getMothersFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_6", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getMothersMaidensName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_7", request.getLocale()), arial13));
			p1.add(new Chunk(person.getBirthDate() + " " + person.getRemaining().getBirthPlace(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_8", request.getLocale()), arial13));
			p1.add(new Chunk(df.format(person.getRemaining().getConfirmation().getDate()) + " " + person.getRemaining().getConfirmation().getPlace(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_9", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getConfirmation().getConfirmationName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-confirmation_10", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getConfirmation().getBishop(), arialbold13));
			
		} else if (MARRIAGE.equals(sort)) {
			p1 = new Phrase();
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin, request.getLocale()), arial13));
			p1.add(new Chunk(yearDf.format(fp.getMarriageDate()), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_2", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFileNumber(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_3", request.getLocale()), arial13));
			//if (person.getRemaining().isWoman()) {
			p1.add(new Chunk(fp.getFianceeHe().getFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_4", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFianceeHe().getBirthDate() + " " + fp.getFianceeHe().getRemaining().getBirthPlace(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_5", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFianceeHe().getRemaining().getFathersFullName() + " " + fp.getFianceeHe().getRemaining().getMothersMaidenFullName(), arialbold11));
			
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_6", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFianceeShe().getFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_7", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFianceeShe().getBirthDate() + " " + fp.getFianceeShe().getRemaining().getBirthPlace(), arialbold11));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_8", request.getLocale()), arial13));
			p1.add(new Chunk(fp.getFianceeShe().getRemaining().getFathersFullName() + " " + fp.getFianceeShe().getRemaining().getMothersMaidenFullName(), arialbold11));
			
			p1.add(new Chunk(getText("print.baptism-certificate.intro-marriage" + latin + "_9", request.getLocale()), arial13));
			p1.add(new Chunk(df.format(fp.getMarriageDate()) + " " + fp.getPlace(), arialbold11));
			
		} else if (DEATH.equals(sort)) {
			String spouse = null;
			if (person.isWithoutFamily()) {
				spouse = "--";
			} else if (person instanceof Parent) {
				Parent p = (Parent)person;
				if (p.equals(p.getFamily().getWife()) && p.getFamily().getHusband() != null)
					spouse = p.getFamily().getHusband().getFullName();
				else if (p.equals(p.getFamily().getHusband()) && p.getFamily().getWife() != null)
					spouse = p.getFamily().getWife().getFullName();
				else
					spouse = "--";
			}
			p1 = new Phrase();
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin, request.getLocale()), arial13));
			p1.add(new Chunk(yearDf.format(person.getRemaining().getDeath().getDate()), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_2", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getDeath().getFileNumber(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_3", request.getLocale()), arial13));
			p1.add(new Chunk(person.getFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_4", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getFathersFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_5", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getMothersFullName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_6", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getMothersMaidensName(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_7", request.getLocale()), arial13));
			p1.add(new Chunk(person.getBirthDate() + " " + person.getRemaining().getBirthPlace(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_8", request.getLocale()), arial13));
			p1.add(new Chunk(df.format(person.getRemaining().getDeath().getDate()) + " " + person.getRemaining().getDeath().getPlace(), arialbold13));
			p1.add(new Chunk(getText("print.baptism-certificate.intro-death" + latin + "_9", request.getLocale()), arial13));
			p1.add(new Chunk(person.getRemaining().getDeath().getBurialPlace(), arialbold13));
		}
		PdfPCell cell1 = new PdfPCell(p1);
		
		cell1.setPaddingBottom(20f);
		cell1.setBorder(PdfPCell.NO_BORDER);
		
		cell1.setLeading(0f, 1.7f);
		
		table.addCell(cell1);
		
		return table;
	}
	
	
	private PdfPTable prepareEndTable(HttpServletRequest request, Document document, String sort, String latin) throws DocumentException {
		PdfPTable table = new PdfPTable(new float[]{40f, 60f});
		PdfPCell cell1 = new PdfPCell(new Phrase(getText("print.baptism-certificate.stamp" + latin, request.getLocale()), arial8));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell1);
		
		
		PdfPCell cell2 = null;
		if (BAPTISM.equals(sort)) {
			cell2 = new PdfPCell(new Phrase(getText("print.baptism-certificate.ending-baptism" + latin, new Object[]{df.format(new Date())}, request.getLocale()), arial8));
		} else if (CONFIRMATION.equals(sort)) {
			cell2 = new PdfPCell(new Phrase(getText("print.baptism-certificate.ending-confirmation" + latin, new Object[]{df.format(new Date())}, request.getLocale()), arial8));
		} else if (MARRIAGE.equals(sort)) {
			cell2 = new PdfPCell(new Phrase(getText("print.baptism-certificate.ending-marriage" + latin, new Object[]{df.format(new Date())}, request.getLocale()), arial8));
		} else if (DEATH.equals(sort)) {
			cell2 = new PdfPCell(new Phrase(getText("print.baptism-certificate.ending-death" + latin, new Object[]{df.format(new Date())}, request.getLocale()), arial8));
		}
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell2.setLeading(0f, 1.7f);
		table.addCell(cell2);
		
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(emptyCell);
		
		PdfPCell cell3 = new PdfPCell(new Phrase(getText("print.baptism-certificate.rector" + latin, request.getLocale()), arial8));
		cell3.setBorder(PdfPCell.NO_BORDER);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell3.setPaddingTop(20f);
		table.addCell(cell3);
		
		return table;
	}
	
	private boolean validate(String sort, Person person, FianceePair fp) {
		if (MARRIAGE.equals(sort)) {
			return validateMarriage(fp);
		}
		
		if (person.getRemaining() == null)
			return false;
		
		if (StringUtils.isBlank(person.getRemaining().getFathersFirstName()) || StringUtils.isBlank(person.getRemaining().getFathersLastName()) ||
				StringUtils.isBlank(person.getRemaining().getMothersFirstName()) ||
						StringUtils.isBlank(person.getRemaining().getMothersMaidensName()))
			return false;

		if (BAPTISM.equals(sort)) {
			if (person.getRemaining().getBaptism() == null || person.getRemaining().getBaptism().getDate() == null || StringUtils.isBlank(person.getRemaining().getBaptism().getFileNumber()) ||
					StringUtils.isBlank(person.getRemaining().getBaptism().getPlace()))
				return false;
		} else if (CONFIRMATION.equals(sort)) {
			if (person.getRemaining().getConfirmation() == null || person.getRemaining().getConfirmation().getDate() == null || StringUtils.isBlank(person.getRemaining().getConfirmation().getFileNumber()) ||
					StringUtils.isBlank(person.getRemaining().getConfirmation().getConfirmationName()) || StringUtils.isBlank(person.getRemaining().getConfirmation().getPlace()))
				return false;
		} else if (DEATH.equals(sort)) {
			if (person.getRemaining().getDeath() == null || person.getRemaining().getDeath().getDate() == null || StringUtils.isBlank(person.getRemaining().getDeath().getFileNumber()) ||
					StringUtils.isBlank(person.getRemaining().getDeath().getPlace()) || StringUtils.isBlank(person.getRemaining().getDeath().getBurialPlace()))
				return false;
		}
		
		return true;
	}
	
	private boolean validateMarriage(FianceePair fp) {
		if (fp.getMarriageDate() == null || StringUtils.isBlank(fp.getFileNumber()) ||
				StringUtils.isBlank(fp.getPlace()))
			return false;
		return true;
	}
}
