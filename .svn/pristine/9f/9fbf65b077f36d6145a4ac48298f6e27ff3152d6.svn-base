package org.parafia.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.FamilyManager;
import org.parafia.webapp.controller.commands.FamilyFindFilter;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.parafia.webapp.util.pagination.PaginateListFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { Constants.FAMILY_FILTER })
public class FamilyListController
{
	private final static int PAGESIZE = 15;
	@Autowired
	private FamilyManager familyManager;
	@Autowired
	private PaginateListFactory paginateListFactory;
	private final Logger log = Logger.getLogger(this.getClass());

	@ModelAttribute(Constants.FAMILY_FILTER)
	public FamilyFindFilter getFilter()
	{
		return new FamilyFindFilter();
	}

	@RequestMapping(value = "/print/cards", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(@ModelAttribute(Constants.FAMILY_FILTER) FamilyFindFilter filter,
			HttpServletRequest request) throws Exception
	{
		ModelAndView mv = new ModelAndView("print/cards");
		log.debug("entering 'formBackingObject' method...");
		
		

		ExtendedPaginatedList familiesList = paginateListFactory.getPaginatedListFromRequest(request);
		familiesList.setPageSize(PAGESIZE);

		if (filter.isNotBlank())
		{
			familyManager.getRecPageFamilies(familiesList, filter.getSurname(), filter.getStreet(),
					filter.getFirstNumber());

			mv.addObject(Constants.FAMILIES_LIST, familiesList);
			return mv;
		}
		else
		{
			familyManager.getRecPageFamilies(familiesList, null, null, null);
			mv.addObject(Constants.FAMILIES_LIST, familiesList);
			return mv;
		}
	}

	@RequestMapping(value = "/print/cards", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute(Constants.FAMILY_FILTER) FamilyFindFilter filter,
			HttpServletRequest request, BindingResult errors) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		ModelAndView mv = new ModelAndView();
		ExtendedPaginatedList familiesList = paginateListFactory.getPaginatedListFromRequest(request);
		familiesList.setPageSize(PAGESIZE);

		if (filter.isNotBlank())
		{
			mv.addObject(Constants.FAMILY_FILTER, filter);
			familyManager.getRecPageFamilies(familiesList, filter.getSurname(), filter.getStreet(),
					filter.getFirstNumber());
		}
		else
		{
			familyManager.getRecPageFamilies(familiesList, null, null, null);
		}

		return new ModelAndView("print/cards").addObject("filter", filter).addObject(Constants.FAMILIES_LIST,
				familiesList);
	}
}