package org.parafia.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Child;
import org.parafia.model.Errors;
import org.parafia.model.Family;
import org.parafia.model.Messages;
import org.parafia.model.Other;
import org.parafia.model.PriestlyVisit;
import org.parafia.model.dictionary.MarriageStatus;
import org.parafia.model.dictionary.TakePart;
import org.parafia.service.FamilyManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(value = { "successMessages", "errors" })
public class FamilyEditController
{
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private FamilyManager familyManager;
	@Autowired
	private BaseControllerTemp base;

	@ModelAttribute("successMessages")
	public Messages<String> getMessages()
	{
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors()
	{
		return new Errors<>();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder)
	{
		base.bind(binder);
		CustomPropertyEditor marriageStatusEditor = new CustomPropertyEditor(familyManager.getMarriageStatuses());
		binder.registerCustomEditor(MarriageStatus.class, marriageStatusEditor);
		CustomPropertyEditor takePartEditor = new CustomPropertyEditor(familyManager.getTakePart());
		binder.registerCustomEditor(TakePart.class, takePartEditor);
	}

	@RequestMapping(value = "/print/familyform", method = RequestMethod.GET)
	public ModelAndView formBackingObject(@RequestParam(value = "id", required = false) String id) throws Exception
	{
		Family family;
		ModelAndView mv = new ModelAndView("print/familyEdit");
		
		mv.addObject(Constants.MARRIAGE_STATUSES_LIST, familyManager.getMarriageStatuses());
		mv.addObject(Constants.TAKE_PART_LIST, familyManager.getTakePart());
		if (id != null && !"".equals(id))
		{
			family = familyManager.get(Long.valueOf(id));

		}
		else
		{
			family = new Family();
		}

		mv.addObject("family", family);
		return mv;
	}

	@RequestMapping(value="/print/familyform", method=RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("family") @Valid Family family,
				BindingResult errors,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err,
				@RequestParam Map<String, String> params, 
				@RequestParam(value = "delete", required = false) String delete
				) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		List<String> parNames = new ArrayList<>();

		Locale locale = LocaleContextHolder.getLocale();

		for (Entry<String, String> e : params.entrySet())
			parNames.add(e.getKey());

		int count = base.countSubmittedParams(parNames, "others\\[(\\d)*\\].firstName");
		log.debug("count=" + count + " family.others=" + family.getOthers().size());
		int differ = count - family.getOthers().size();
		log.debug("differ = " + differ);
		for (int z = 0; z < differ; z++)
		{
			// fakes to initialize the collection
			family.getOthers().add(new Other());
		}
		for (int z = differ; z < 0; z++)
		{
			family.getOthers().remove(family.getOthers().size() - 1);
		}
		log.debug("count=" + count + " family.others=" + family.getOthers().size());

		count = base.countSubmittedParams(parNames, "children\\[(\\d)*\\].firstName");
		log.debug("count=" + count + " family.children=" + family.getChildren().size());
		differ = count - family.getChildren().size();
		log.debug("differ = " + differ);
		for (int z = 0; z < differ; z++)
		{
			// fakes to initialize the collection
			//family.getChildren().add(new Children());
			family.getChildren().add(new Child());
		}
		for (int z = differ; z < 0; z++)
		{
			family.getChildren().remove(family.getChildren().size() - 1);
		}
		log.debug("count=" + count + " family.children=" + family.getChildren().size());

		count = base.countSubmittedParams(parNames, "visits\\[(\\d)*\\].date");
		log.debug("count=" + count + " family.visits=" + family.getVisits().size());
		differ = count - family.getVisits().size();
		log.debug("differ = " + differ);
		for (int z = 0; z < differ; z++)
		{
			// fakes to initialize the collection
			family.getVisits().add(new PriestlyVisit());
		}
		for (int z = differ; z < 0; z++)
		{
			// fakes to initialize the collection
			family.getVisits().remove(family.getVisits().size() - 1);
		}
		log.debug("count=" + count + " family.visits=" + family.getVisits().size());

		if (delete != null)
		{
			familyManager.remove(family.getId());
			msg.add(base.getText("family.deleted", family.getSurname(), locale));
			return new ModelAndView(new RedirectView("cards.html"));
		}
		else
		{
			if (StringUtils.isBlank(family.getWife().getFirstName()))
				family.setWife(null);
			else
			{
				family.getWife().setFamily(family);
				if (StringUtils.isBlank(family.getWife().getSurname()))
					family.getWife().setSurname(family.getSurname());
			}

			if (StringUtils.isBlank(family.getHusband().getFirstName()))
				family.setHusband(null);
			else
			{
				family.getHusband().setFamily(family);
				if (StringUtils.isBlank(family.getHusband().getSurname()))
					family.getHusband().setSurname(family.getSurname());
			}

			for (Other other : family.getOthers())
			{
				other.setFamily(family);
				if (StringUtils.isBlank(other.getSurname()))
					other.setSurname(family.getSurname());
			}

			for (Child child : family.getChildren())
			{
				child.setFamily(family);
				if (StringUtils.isBlank(child.getSurname()))
					child.setSurname(family.getSurname());
			}

			for (PriestlyVisit visit : family.getVisits())
			{
				visit.setFamily(family);
			}

			try
			{
				familyManager.saveFamily(family);
				msg.add(base.getText("family.saved", family.getSurname(), locale));
				return new ModelAndView(new RedirectView("cards.html"));
			}
			catch (AccessDeniedException ade)
			{
				// thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
				log.warn(ade.getMessage());
				throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
			}
			catch (ObjectExistsException e)
			{
				// errors.rejectValue("surname", "errors.existing.family",
				// new Object[] {family.getSurname(), family.getAddress().getFullString()},
				// "Rodzina już istnieje w bazie danych");
				err.add(base.getText("errors.existing.family",
						new Object[] { family.getSurname(), family.getAddress().getFullString() }, locale));
				return new ModelAndView("/print/familyEdit");
			}
		}
	}

}
