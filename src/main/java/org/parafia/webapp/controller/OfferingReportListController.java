package org.parafia.webapp.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Celebrant;
import org.parafia.model.Errors;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.model.Messages;
import org.parafia.service.IntentionManager;
import org.parafia.webapp.controller.commands.CelebrantIntentions;
import org.parafia.webapp.controller.commands.OfferingFilter;
import org.parafia.webapp.controller.commands.OfferingSumIntentions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class OfferingReportListController {

	private Logger log = Logger.getLogger(getClass());
	private DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private IntentionManager intentionManager;

	@Autowired
	private BaseControllerTemp base;

	@ModelAttribute("successMessages")
	public Messages<String> getMessages() {
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors() {
		return new Errors<>();
	}

	@ModelAttribute(Constants.OFFERING_FILTER)
	public OfferingFilter getFilter() {
		return new OfferingFilter();
	}

	@RequestMapping(value = "/tomes/offeringReport", method = RequestMethod.GET)
	public ModelAndView showForm(
			@ModelAttribute(Constants.OFFERING_FILTER) OfferingFilter filter,
			@ModelAttribute("successMessages") Messages<String> msg, 
			@ModelAttribute("errors") Errors<String> err) throws ParseException {

		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView("tomes/offeringReport");
		msg.add(base.getText("offering-report.filter.message", locale));

		List<List<Object>> rowList = new ArrayList<>();
		List<String> celebrantsList = new ArrayList<>();

		if (filter.isNotBlank()) {
			try {
				Date firstDate = format.parse(filter.getFirstDate());
				Date lastDate = format.parse(filter.getLastDate());

				List<Intention> intentionsList = intentionManager.loadIntentions(firstDate, lastDate);

				rowList = getRowList(intentionsList);
				celebrantsList = getCelebrantList(intentionsList);

				// remove object where offering sum is zero
				List<Integer> indexList = getIndexList(getSumList(intentionsList));
				for (int i = celebrantsList.size() - 1; i >= 0; i--) {
					if (indexList.contains(i)) {
						celebrantsList.remove(i);
					}
				}
				// remove object where offering sum is zero
				for (List<Object> o1 : rowList) {
					for (int i = o1.size() - 1; i >= 0; i--) {
						if (indexList.contains(i)) {
							o1.remove(i);
						}
					}
				}
			} catch (ParseException ex) {
				err.add(base.getText("errors.valid", locale));
			}
		}

		if (celebrantsList.size() > 1) {
			mv.addObject("rowList", rowList);
			mv.addObject("celebrantsList", celebrantsList);
		}
		
		return mv;
	}

	@RequestMapping(value = "/tomes/offeringReport", method = RequestMethod.POST)
	public ModelAndView onSubmit(
			@ModelAttribute(Constants.OFFERING_FILTER) OfferingFilter filter,
			@ModelAttribute("successMessages") Messages<String> msg, 
			@ModelAttribute("errors") Errors<String> err) throws ParseException {

		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView("tomes/offeringReport");
		mv.addObject(Constants.OFFERING_FILTER, filter);

		List<List<Object>> rowList = new ArrayList<>();
		List<String> celebrantsList = new ArrayList<>();

		if (filter.isNotBlank()) {
			try {
				Date firstDate = format.parse(filter.getFirstDate());
				Date lastDate = format.parse(filter.getLastDate());

				List<Intention> intentionsList = intentionManager.loadIntentions(firstDate, lastDate);

				rowList = getRowList(intentionsList);
				celebrantsList = getCelebrantList(intentionsList);

				// remove object where offering sum is zero
				List<Integer> indexList = getIndexList(getSumList(intentionsList));
				for (int i = celebrantsList.size() - 1; i >= 0; i--) {
					if (indexList.contains(i)) {
						celebrantsList.remove(i);
					}
				}
				// remove object where offering sum is zero
				for (List<Object> o1 : rowList) {
					for (int i = o1.size() - 1; i >= 0; i--) {
						if (indexList.contains(i)) {
							o1.remove(i);
						}
					}
				}
			} catch (ParseException ex) {
				err.add(base.getText("errors.valid", locale));
			}
		} else {
			msg.add(base.getText("offering-report.filter.message", locale));
		}

		if (celebrantsList.size() > 1) {
			mv.addObject("rowList", rowList);
			mv.addObject("celebrantsList", celebrantsList);
		}
		
		return mv;
	}

	private List<CelebrantIntentions> getCelebrantIntentionsList(List<Intention> intentions) {
		List<CelebrantIntentions> resultList = new ArrayList<>();
		List<Intention> temp = new ArrayList<>();

		for (Intention intention : intentions) {
			if (intention.getAcceptor() != null) {
				temp.add(intention);
			}
		}
		for (Celebrant celebrant : intentionManager.loadAllCelebrants()) {
			List<Intention> intentionsList = new ArrayList<>();
			CelebrantIntentions celebrantIntentions = new CelebrantIntentions();

			celebrantIntentions.setCelebrant(celebrant);
			for (Intention intention : temp) {
				if (intention.getAcceptor().getId().equals(celebrant.getId())) {
					intentionsList.add(intention);
				}
			}
			celebrantIntentions.setIntentions(intentionsList);

			List<OfferingSumIntentions> sumIntentionsList = new ArrayList<>();
			for (IntentionType intentionType : intentionManager.loadAllTypes()) {
				OfferingSumIntentions sumIntention = new OfferingSumIntentions();
				BigDecimal sum = new BigDecimal(0.0);

				sumIntention.setIntentionType(intentionType);
				for (Intention intention : intentionsList) {
					if (intentionType.getId().equals(intention.getType().getId())
							&& (intention.getOffering() != null)) {
						sum = sum.add(intention.getOffering());
					}
				}
				sumIntention.setSum(sum);
				sumIntentionsList.add(sumIntention);
				celebrantIntentions.setSumIntentions(sumIntentionsList);
			}
			resultList.add(celebrantIntentions);
		}
		return resultList;
	}

	private List<String> getCelebrantList(List<Intention> intentions) {
		List<String> celebrantsList = new ArrayList<>();
		celebrantsList.add("Typ");
		for (CelebrantIntentions cI : getCelebrantIntentionsList(intentions)) {
			celebrantsList.add(cI.getCelebrant().getFullName());
		}
		return celebrantsList;
	}

	private List<List<Object>> getRowList(List<Intention> intentions) {
		List<List<Object>> rowList = new ArrayList<>();
		rowList.add(getSumList(intentions));

		for (int i = 0; i < intentionManager.loadAllTypes().size(); i++) {
			List<Object> list = new ArrayList<>();
			list.add(intentionManager.loadAllTypes().get(i).getName());

			for (CelebrantIntentions c : getCelebrantIntentionsList(intentions)) {
				list.add(c.getSumIntentions().get(i).getSum());
			}
			rowList.add(list);
		}
		return rowList;
	}

	private List<Object> getSumList(List<Intention> intentions) {
		List<Object> sumList = new ArrayList<>();
		sumList.add("Suma");

		for (CelebrantIntentions celebrantIntentions : getCelebrantIntentionsList(intentions)) {
			BigDecimal sum = new BigDecimal(0.0);
			for (Intention intention : celebrantIntentions.getIntentions()) {
				if (intention.getOffering() != null) {
					sum = sum.add(intention.getOffering());
				}
			}
			sumList.add(sum);
		}
		return sumList;
	}

	/**
	 * 
	 * @param getSumList
	 * @return index list where offering sum is zero
	 */
	private List<Integer> getIndexList(List<Object> getSumList) {
		List<Integer> indexList = new ArrayList<>();
		for (int i = 1; i < getSumList.size(); i++) {
			Long value = BigDecimal.class.cast(getSumList.get(i)).longValueExact();
			if (value == 0) {
				indexList.add(i);
			}
		}
		return indexList;
	}
}
