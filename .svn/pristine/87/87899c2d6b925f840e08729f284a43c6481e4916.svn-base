package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.FianceeManager;
import org.parafia.service.PersonManager;
import org.parafia.webapp.controller.commands.BapConDeaFindFilter;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.parafia.webapp.util.pagination.PaginateListFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TomeFianceesListController {

	private Logger log = Logger.getLogger(getClass());
	private final static int PAGESIZE = 15;

	@Autowired
	private PersonManager personManager;

	@Autowired
	private FianceeManager fianceeManager;

	@Autowired
	private PaginateListFactory paginateListFactory;

	@ModelAttribute(Constants.BAP_CON_DEA_FILTER)
	public BapConDeaFindFilter getFilter() {
		return new BapConDeaFindFilter();
	}

	@RequestMapping(value = "/tomes/fianceesTome", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(HttpServletRequest request,
			@ModelAttribute(Constants.BAP_CON_DEA_FILTER) BapConDeaFindFilter filter) throws Exception {

		log.debug("entering 'formBackingObject' method...");
		ModelAndView mv = new ModelAndView("tomes/fianceesTome");
		Integer[] fianceesYears = personManager.getFianceesYears();

		ExtendedPaginatedList fianceePairsList = paginateListFactory.getPaginatedListFromRequest(request);
		fianceePairsList.setPageSize(PAGESIZE);

		if (filter.isNotBlank()) {
			fianceeManager.getRecPageFianceePairs(fianceePairsList, filter.getFirstName(), filter.getSurname(), filter.getYear());
			mv.addObject(Constants.FIANCEES_LIST, fianceePairsList);
		} else {
			fianceeManager.getRecPageFianceePairs(fianceePairsList, null, null, 0);
			mv.addObject(Constants.FIANCEES_LIST, fianceePairsList);
		}

		mv.addObject(Constants.BAP_CON_DEA_FILTER, filter);
		mv.addObject(Constants.YEARS, fianceesYears);
		return mv;
	}

	@RequestMapping(value = "/tomes/fianceesTome", method = RequestMethod.POST)
	public ModelAndView onSubmit(
			HttpServletRequest request,
			@ModelAttribute(Constants.BAP_CON_DEA_FILTER) BapConDeaFindFilter filter) throws Exception {
		
		log.debug("entering 'onSubmit' method...");

		ModelAndView mv = new ModelAndView("tomes/fianceesTome");
		Integer[] fianceesYears = personManager.getFianceesYears();

		ExtendedPaginatedList fianceePairsList = paginateListFactory.getPaginatedListFromRequest(request);
		fianceePairsList.setPageSize(PAGESIZE);

		if (filter.isNotBlank()) {
			fianceeManager.getRecPageFianceePairs(fianceePairsList, filter.getFirstName(), filter.getSurname(), filter.getYear());
			mv.addObject(Constants.FIANCEES_LIST, fianceePairsList);
		} else {
			fianceeManager.getRecPageFianceePairs(fianceePairsList, null, null, 0);
			mv.addObject(Constants.FIANCEES_LIST, fianceePairsList);
		}
		
		mv.addObject(Constants.BAP_CON_DEA_FILTER, filter);
		mv.addObject(Constants.YEARS, fianceesYears);
		return mv;
	}
}
