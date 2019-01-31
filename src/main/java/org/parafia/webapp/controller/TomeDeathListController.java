package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.PersonManager;
import org.parafia.webapp.controller.commands.BapConDeaFindFilter;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.parafia.webapp.util.pagination.PaginateListFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class TomeDeathListController extends BaseFormController {

	private Logger log = Logger.getLogger(getClass());
	private final static int PAGESIZE = 15;

	@Autowired
	private PersonManager personManager;

	@Autowired
	private PaginateListFactory paginateListFactory;

	@ModelAttribute(Constants.BAP_CON_DEA_FILTER)
	public BapConDeaFindFilter getFilter() {
		return new BapConDeaFindFilter();
	}

	@RequestMapping(value = "/tomes/deathTome", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(HttpServletRequest request,
			@ModelAttribute(Constants.BAP_CON_DEA_FILTER) BapConDeaFindFilter filter) throws Exception {

		log.debug("entering 'formBackingObject' method...");
		ModelAndView mv = new ModelAndView("tomes/deathTome");

		Integer[] deathYears = personManager.getDeathYears();
		Integer[] burialYears = personManager.getBurialYears();

		ExtendedPaginatedList deathsList = paginateListFactory.getPaginatedListFromRequest(request);
		deathsList.setPageSize(PAGESIZE);

		if (filter.isNotBlank()) {
			personManager.getRecPageDeaths(deathsList, filter.getSurname(), filter.getFirstName(),
					String.valueOf(filter.getYear()), String.valueOf(filter.getBurialYear()));
			mv.addObject(Constants.PERSONS_LIST, deathsList);
		} else {
			personManager.getRecPageDeaths(deathsList, null, null, null, null);
			mv.addObject(Constants.PERSONS_LIST, deathsList);
		}

		mv.addObject(Constants.BAP_CON_DEA_FILTER, filter);
		mv.addObject(Constants.YEARS, deathYears);
		mv.addObject(Constants.BURIAL_YEARS, burialYears);
		return mv;
	}

	@RequestMapping(value = "/tomes/deathTome", method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request,
			@ModelAttribute(Constants.BAP_CON_DEA_FILTER) BapConDeaFindFilter filter) throws Exception {

		log.debug("entering 'onSubmit' method...");
		ModelAndView mv = new ModelAndView("tomes/deathTome");

		Integer[] deathYears = personManager.getDeathYears();
		Integer[] burialYears = personManager.getBurialYears();

		ExtendedPaginatedList deathsList = paginateListFactory.getPaginatedListFromRequest(request);
		deathsList.setPageSize(PAGESIZE);

		if (filter.isNotBlank()) {
			personManager.getRecPageDeaths(deathsList, filter.getSurname(), filter.getFirstName(),
					String.valueOf(filter.getYear()), String.valueOf(filter.getBurialYear()));
			mv.addObject(Constants.PERSONS_LIST, deathsList);
		} else {
			personManager.getRecPageDeaths(deathsList, null, null, null, null);
			mv.addObject(Constants.PERSONS_LIST, deathsList);
		}

		mv.addObject(Constants.BAP_CON_DEA_FILTER, filter);
		mv.addObject(Constants.YEARS, deathYears);
		mv.addObject(Constants.BURIAL_YEARS, burialYears);
		return mv;
	}
}