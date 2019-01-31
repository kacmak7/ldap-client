package org.parafia.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.parafia.model.Celebrant;
import org.parafia.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CelebrantController
{
	// TODO: functionality likely to be moved to IntentionController

	@Autowired
	@Qualifier("celebrantManager")
	private GenericManager<Celebrant, Long> celebrantManager;

	// -- TESTED --
	@RequestMapping(method = RequestMethod.GET, value = "/tomes/celebrantsList")
	public String celebrantsList()
	{
		return "/tomes/celebrantsList";
	}

	/**
	 * Retrieves all celebrants
	 * 
	 * @return
	 */
	// -- TESTED --
	@ModelAttribute("celebrants")
	public List<Celebrant> getAllCelebrants()
	{
		List<Celebrant> celebrants = celebrantManager.getAll();

		return celebrants;
	}

	
	@RequestMapping(method = RequestMethod.GET, params = "edit", value = "/tomes/editCelebrant")
	public ModelAndView editCelebrant(@RequestParam(value = "id", required = false) Long id)
	{
		Celebrant celebrant = null;

		if (id == null)
			celebrant = new Celebrant();
		else
			celebrant = celebrantManager.get(id);

		ModelAndView model = new ModelAndView("/tomes/editCelebrant", "celebrant", celebrant);
		return model;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/tomes/editCelebrant")
	public ModelAndView saveCelebrant(@ModelAttribute("celebrant") @Valid Celebrant celebrant, BindingResult result)
	{
		if (result.hasErrors())
		{
			// return "/tomes/editIntention";
			ModelAndView model = new ModelAndView("/tomes/editCelebrant", "celebrant", celebrant);
			// return "/tomes/intentionTome.html";
			return model;
		}
		else
		{
			celebrantManager.save(celebrant);

			return new ModelAndView("common/closeReloadParent");
		}
	}
}
