package org.parafia.webapp.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.parafia.Constants;
import org.parafia.model.Celebrant;
import org.parafia.model.CommonIntention;
import org.parafia.model.Intention;
import org.parafia.model.IntentionAnnotation;
import org.parafia.model.IntentionType;
import org.parafia.model.IntentionTypeDuration;
import org.parafia.model.JsonFeed;
import org.parafia.model.LabelValue;
import org.parafia.service.IntentionAnnotationManager;
import org.parafia.service.IntentionManager;
import org.parafia.util.DateUtil;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.lowagie.text.pdf.PdfWriter;

@Controller
public class IntentionController extends BasePrintController
{
	@Autowired
	private IntentionManager intentionManager;

	@Autowired
	private IntentionAnnotationManager intentionAnnotationManager;

	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value)
			{
				if ("".equals(value))
					setValue(null);
				else if (DateUtil.checkDateFormat(value, DateUtil.getDatePattern()))
				{
					try
					{
						setValue(DateUtil.convertStringToDate(DateUtil.getDatePattern(), value));
					}
					catch (ParseException e)
					{
						e.printStackTrace();
						setValue(null);
					}
				}
				else
				{
					Calendar calendar = Calendar.getInstance();
					// TODO: value is 1484128800000,1484128800000
					long realValue = Long.valueOf(value.split(",")[0]);
					calendar.setTimeInMillis(realValue);

					setValue(calendar.getTime());
				}
			}

			public String getAsText()
			{
				if (getValue() == null)
					return null;
				else
					return String.valueOf(((Date) getValue()).getTime());
			}
		});

		binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
			public void setAsText(String value)
			{
				if ("".equals(value))
					setValue(null);
				else
				{
					// setValue(Float.valueOf(value));
					try
					{
						setValue(new BigDecimal(value));
					}
					catch (NumberFormatException ne)
					{
						// TODO: what is there are many than 1 BigDecimal fields?
						binder.getBindingResult().rejectValue("offering", getText("errors.value.decimal"));
						setValue(null);
					}
				}
			}

			public String getAsText()
			{
				if (getValue() == null)
					return null;
				else
					return getValue().toString();
			}
		});

		// binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
		// true));
		CustomPropertyEditor celebrantsEditor = new CustomPropertyEditor(intentionManager.loadAllCelebrants());
		binder.registerCustomEditor(Celebrant.class, celebrantsEditor);
		CustomPropertyEditor confessionsEditor = new CustomPropertyEditor(intentionManager.loadAllConfessions());
		binder.registerCustomEditor(Celebrant.class, confessionsEditor);
		
		CustomPropertyEditor typesEditor = new CustomPropertyEditor(intentionManager.loadAllTypes());
		binder.registerCustomEditor(IntentionType.class, typesEditor);
		CustomPropertyEditor typeDurationEditor = new CustomPropertyEditor(intentionManager.loadAllTypeDuration());
		binder.registerCustomEditor(IntentionTypeDuration.class, typeDurationEditor);
	}
	
	/**
	 * Retrieves all celebrants
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("celebrants")
	public List<Celebrant> getActiveCelebrants(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "allDay", required = false) Boolean isAnnotation)
	{
		return listMakeComplete(id, isAnnotation, "celebrants");
	}

	/**
	 * Retrieves all confessions
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("confessions")
	public List<Celebrant> getActiveConfessions(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "allDay", required = false) Boolean isAnnotation)
	{
		return listMakeComplete(id, isAnnotation, "confessions");
	}
		
	/**
	 * Retrieves all celebrants (LabelValues for pickList)
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("celebrantList")
	public List<LabelValue> getCelebrantList()
	{
		List<Celebrant> celebrants = intentionManager.loadActiveCelebrants();
		List<LabelValue> celebrantList = new ArrayList<LabelValue>();

		if (celebrants != null)
		{
			for (Celebrant c : celebrants)
			{
				// convert the user's roles to LabelValue Objects
				// celebrantList.add(new LabelValue(c.getFullName(), c.getFullName()));
				celebrantList.add(new LabelValue(c.getFullName(), String.valueOf(c.getId())));
			}
		}

		return celebrantList;
	}
	
	/**
	 * Retrieves all confessions (LabelValues for pickList)
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("confessionList")
	public List<LabelValue> getConfessionList()
	{
		List<Celebrant> confessions = intentionManager.loadActiveConfessions();
		List<LabelValue> confessionList = new ArrayList<LabelValue>();

		if (confessions != null)
		{
			for (Celebrant c : confessions)
			{
				// convert the user's roles to LabelValue Objects
				// confessionList.add(new LabelValue(c.getFullName(), c.getFullName()));
				confessionList.add(new LabelValue(c.getFullName(), String.valueOf(c.getId())));
			}
		}

		return confessionList;
	}

	/**
	 * Gets the intentionDate
	 * 
	 * @return Date intentionDate
	 */
	// -- NOT TESTED --
	@ModelAttribute("intentionDate")
	public String getIntentionDate(@RequestParam(value = "date", required = false) Long date)
	{
		if (date == null)
			return null;
		else
		{
			DateFormat df = new SimpleDateFormat(" 'o' HH:mm");
			// Date d = new Date(date);
			return DateUtil.getPolishFormatDate(new Date(date)) + df.format(date);
		}
	}

	/**
	 * Retrieves all types
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("types")
	public List<IntentionType> getAllTypes()
	{
		return intentionManager.loadAllTypes();
	}
	
	/**
	 * Retrieves all types duration
	 * 
	 * @return
	 */
	@ModelAttribute("durations")
	public List<IntentionTypeDuration> getAllTypeDuration()
	{
		return intentionManager.loadAllTypeDuration();
	}

	@ModelAttribute("defaultDate")
	public String getDefaultDate(HttpSession session)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (null != session.getAttribute(Constants.INTENTION_DEFAULT_DATE))
			return formatter.format((Date) session.getAttribute(Constants.INTENTION_DEFAULT_DATE));
		else
			return formatter.format(new Date());
	}

	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, params = { "start", "end",
			"_" }, value = "/tomes/intentions.json", produces = "application/json")
	public @ResponseBody List<JsonFeed> getJsonFeed(@RequestParam("start") String start,
			@RequestParam("end") String end, HttpSession session, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		// TODO: not the best solution of session attribute here but the spring's
		// version is still pretty old

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * HttpHeaders responseHeaders = new HttpHeaders();
		 * responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		 */
		List<JsonFeed> feed = new ArrayList<>();
		try
		{
			Date startDate = formatter.parse(start);
			Date endDate = formatter.parse(end);

			session.setAttribute(Constants.INTENTION_DEFAULT_DATE, startDate);

			List<Intention> intentions = intentionManager.loadIntentions(startDate, endDate);
			List<IntentionAnnotation> intentionAnnotations = intentionAnnotationManager
					.loadIntentionAnnotations(startDate, endDate);

			if (intentions.size() > 0)
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				for (Intention i : intentions)
				{
					JsonFeed f = new JsonFeed();
					f.setTitle(i.getText().replaceAll(System.getProperty("line.separator"), "<br>"));
					f.setStart(df.format(i.getDate()));
					
					Calendar cal = Calendar.getInstance(); 
					cal.setTime(i.getDate());
					cal.add(Calendar.MINUTE, i.getDuration().getDuration());
					f.setEnd(df.format(cal.getTime()));
					
					f.setColor(i.getType().getColor());
					f.setInfo(i.getInfo());
					feed.add(f);
				}
			}

			if (intentionAnnotations.size() > 0)
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				for (IntentionAnnotation iA : intentionAnnotations)
				{
					JsonFeed f = new JsonFeed();
					f.setTitle(iA.replaceEols(iA.getAnnotation()));
					f.setStart(df.format(iA.getDate()));
					f.setAllDay(true);
					f.setId(iA.getId());
					feed.add(f);
				}
			}
			return feed;
		}
		catch (ParseException ex)
		{
			System.err.println(ex.getStackTrace());
			return feed;
		}
	}

	// @RequestMapping(value = "/tomes/intentionTome.html", method =
	// RequestMethod.GET)
	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, value = "/tomes/intentionTome")
	public String drawCalendar()
	{
		// ModelAndView model = new ModelAndView("tomes/intentionTome");

		return "/tomes/intentionTome";
		// return model;
	}

	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, params = "list", value = "/tomes/intentionsList")
	public ModelAndView listIntentions(@RequestParam("date") long date) throws ParseException
	{
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		// System.out.println(new Date(date));

		List<Intention> intentions = intentionManager.loadIntentions(new Date(date));

		ModelAndView model = new ModelAndView("/tomes/intentionsList", "intentions", intentions);

		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "list", value = "/tomes/annotationsList")
	public ModelAndView listAnnotations(
			@RequestParam("date") long date) throws ParseException
	{

		List<IntentionAnnotation> annotations = intentionAnnotationManager.loadIntentionAnnotations((new Date(date)));

		ModelAndView model = new ModelAndView("/tomes/annotationsList", "annotations", annotations);

		return model;
	}

	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, params = "edit", value = "/tomes/editIntention")
	public ModelAndView addIntention(@RequestParam("date") Long date,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "allDay", required = false) Boolean isAnnotation)
	{
		Date d = new Date(date);

		IntentionAnnotation intentionAnnotation = null;

		if (isAnnotation != null && isAnnotation == true)
		{
			if (id == null)
			{
				intentionAnnotation = new IntentionAnnotation();
				intentionAnnotation.setDate(d);
			}
			else
			{
				intentionAnnotation = intentionAnnotationManager.get(id);
			}

			return new ModelAndView("/tomes/editIntentionAnnotation", "intentionAnnotation", intentionAnnotation);
		}
		else
		{
			Intention intention = null;

			if (id == null)
			{
				intention = new Intention();
				intention.setDate(d);
			}
			else
			{
				intention = intentionManager.get(id);
			}

			ModelAndView model = new ModelAndView("/tomes/editIntention", "intention", intention);
			return model;
		}
	}

	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, params = "delete", value = "/tomes/intentionTome")
	public ModelAndView deleteIntention(
					@RequestParam("delete") Long intentionId, 
					@RequestParam("date") long date,
					@RequestParam(value = "allDay", required = false) Boolean isAnnotation)throws ParseException
	{
		
		if (isAnnotation == null) {
			intentionManager.delete(intentionId);

			return listIntentions(date);
		}
		else {
			intentionAnnotationManager.delete(intentionId);

			return listAnnotations(date);

		}
	}
	
	// -- TESTED --
	@RequestMapping(method = RequestMethod.POST, value = "/tomes/editIntention")
	public ModelAndView saveIntention(
			@ModelAttribute("intention") @Valid Intention intention, 
			BindingResult result,
			@RequestParam(value = "celebrantList", required = false) List<String> celebrants,
			@RequestParam(value = "confessionList", required = false) List<String> confessions,
			@RequestParam(value = "repeat", required = false) String repeat)
	{
		if (repeat != null && intention.getToDate() == null)
		{
			result.rejectValue("toDate", getText("errors.value.notnull"));
		}

		if (result.hasErrors())
		{
			// return "/tomes/editIntention";
			ModelAndView model = new ModelAndView("/tomes/editIntention", "intention", intention);
			// return "/tomes/intentionTome.html";
			return model;
		}
		else
		{
			intention.getCelebrants().clear();
			intention.getConfessions().clear();

			if (celebrants != null)
				for (String celebrant : celebrants)
				{
					Celebrant c = new Celebrant();
					c.setId(Long.valueOf(celebrant));
					intention.getCelebrants().add(c);
				}
			
			if (confessions != null)
				for (String confession : confessions)
				{
					Celebrant c = new Celebrant();
					c.setId(Long.valueOf(confession));
					intention.getConfessions().add(c);
				}

			intentionManager.save(intention);

			if (repeat != null)
			{
				for (CommonIntention ia : repeat(intention, repeat))
				{
					intentionManager.save((Intention) ia);
				}
			}

			return new ModelAndView("common/closeReloadParent");
		}
	}

	// -- TESTED --
	@RequestMapping(method = RequestMethod.POST, params = "allDay", value = "/tomes/editIntention")
	public ModelAndView saveIntentionAnnotation(
			@ModelAttribute("intentionAnnotation") @Valid IntentionAnnotation intentionAnnotation, BindingResult result,
			@RequestParam(value = "repeat", required = false) String repeat)
	{
		if (repeat != null && intentionAnnotation.getToDate() == null)
		{
			result.rejectValue("toDate", getText("errors.value.notnull"));
		}

		if (result.hasErrors())
		{
			ModelAndView model = new ModelAndView("/tomes/editIntentionAnnotation", "intentionAnnotation",
					intentionAnnotation);
			return model;
		}
		else
		{
			intentionAnnotationManager.save(intentionAnnotation);

			if (repeat != null)
			{
				for (CommonIntention ia : repeat(intentionAnnotation, repeat))
				{
					intentionAnnotationManager.save((IntentionAnnotation) ia);
				}
			}

			return new ModelAndView("common/closeReloadParent");
		}
	}

	private List<CommonIntention> repeat(CommonIntention commonInt, String repeat)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(commonInt.getDate());

		switchRepeat(cal, repeat);

		List<CommonIntention> intentions = new ArrayList<CommonIntention>();

		while (DateUtils.ceiling(cal.getTime(), Calendar.DATE)
				.compareTo(DateUtils.ceiling(commonInt.getToDate(), Calendar.DATE)) <= 0)
		{
			CommonIntention newCommonInt = commonInt.clone();
			newCommonInt.setDate(cal.getTime());
			intentions.add(newCommonInt);

			switchRepeat(cal, repeat);
		}

		return intentions;
	}

	private void switchRepeat(Calendar cal, String repeat)
	{
		switch (repeat)
		{
		case "day":
			cal.add(Calendar.DATE, 1);
			break;
		case "week":
			cal.add(Calendar.DATE, 7);
			break;
		case "month":
			cal.add(Calendar.MONTH, 1);
			break;
		}
	}

	@RequestMapping(method = RequestMethod.GET, params = "print", value = "/tomes/editIntention")
	public ModelAndView printIntention(@RequestParam("print") Long intentionId, HttpServletResponse response)
				throws ParseException {
		Intention intention = intentionManager.get(intentionId);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"intencja" + intention.getId() + ".pdf\"");
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");

		Document document = new Document(PageSize.A6.rotate(), 20, 20, 10, 10);
		//Document document = getDocument();

		try
		{
			//response.setContentType("application/pdf");
			PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
			try
			{
				document.open();
				for (Paragraph p : prepareIntentionTable(intention))
					document.add(p);
			}
			finally
			{
				if (writer != null) // do not know why it's necessary
					writer.close();
			}
		}
		catch (DocumentException de)
		{
			System.err.println(de.getMessage());
		}
		catch (IOException ioe)
		{
			System.err.println(ioe.getMessage());
		}
		finally
		{
			if (document != null && document.isOpen())
				document.close();
		}

		return null;
	}

	/* printing methods */

	private Paragraph[] prepareIntentionTable(Intention intention) throws DocumentException {
		Font arialSmall = new Font(getBaseFont(),11);
		Font arial = new Font(getBaseFont(),12);
		Font arialBold = new Font(getBaseFont(),12, Font.BOLD);
		
		DateFormat tdf = new SimpleDateFormat(Constants.TIME_FORMAT_USER);
		
		Phrase p = new Phrase(getText("tomes.editIntention.print-intention-header"), arialSmall);
		p.setLeading(14);
		Paragraph header = new Paragraph(p);
		header.setSpacingAfter(30);
		header.setAlignment(Paragraph.ALIGN_CENTER);
		header.setIndentationRight(180);
		
		
		Phrase textP = new Phrase();
		textP.setLeading(20);
		textP.add(new Chunk(getText("tomes.editIntention.print-intention-text0"), arial));
		textP.add(new Chunk(DateUtil.getPolishFormatDate(intention.getDate()), arialBold));
		textP.add(new Chunk(getText("tomes.editIntention.print-intention-text1"), arial));
		textP.add(new Chunk(tdf.format(intention.getDate()), arialBold));
		textP.add(new Chunk(getText("tomes.editIntention.print-intention-text2"), arial));
		textP.add(new Chunk(intention.getText(), arialBold));
		
		Paragraph text = new Paragraph(textP);
		
		Paragraph signature = new Paragraph(getText("tomes.editIntention.print-intention-priest-signature"), arialSmall);
		signature.setSpacingBefore(30);
		signature.setAlignment(Paragraph.ALIGN_RIGHT);
		signature.setIndentationRight(160);

		return new Paragraph[] {
				header,
				text,
				signature };
	}
	
	private List<Celebrant> listMakeComplete (Long id, Boolean isAnnotation, String name){
		
		List<Celebrant> celebrants = null;
		
		if(name.equals("confessions")) {
			celebrants = intentionManager.loadActiveConfessions();
		} 
		if(name.equals("celebrants")) {
			celebrants = intentionManager.loadActiveCelebrants();
		}
		
		if (id != null && isAnnotation == null)
		{
			Intention intention = intentionManager.get(id);

			Set<Celebrant> newCelebrants = new HashSet<Celebrant>();
			if (intention.getAcceptor() != null && (!intention.getAcceptor().isActive()))
				newCelebrants.add(intention.getAcceptor());
			
				if(name.equals("confessions")) {
					newCelebrants.addAll(intention.getConfessions());
				}
				if(name.equals("celebrants")) {
					newCelebrants.addAll(intention.getCelebrants());
				}

			for (Celebrant c : newCelebrants)
			{
				if (!celebrants.contains(c))
					celebrants.add(c);
			}
		}
		return celebrants;
		}

}
