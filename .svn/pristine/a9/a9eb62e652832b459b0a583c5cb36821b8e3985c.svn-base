package org.parafia.webapp.controller;

import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.ObjectTraverser;
import org.parafia.model.Errors;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.dictionary.BaptismPlace;
import org.parafia.model.dictionary.Practising;
import org.parafia.model.printcards.Baptism;
import org.parafia.model.printcards.BaptismKnowAct;
import org.parafia.model.printcards.BaptismWitness;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.FamilyManager;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.parafia.webapp.propertyeditors.CustomPropertyEditorCheckbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
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
public class BaptismKnowActController
{
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private FamilyManager familyManager;
	@Autowired
	private PersonManager personManager;
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

	/**
	 * Set up a custom property editor for converting form inputs to real objects
	 */
	@InitBinder
	protected void initBinder(final WebDataBinder binder)
	{
		CustomPropertyEditorCheckbox practisingEditor = new CustomPropertyEditorCheckbox(familyManager.getPractising());
		binder.registerCustomEditor(Practising.class, practisingEditor);

		CustomPropertyEditor baptismPlacesEditor = new CustomPropertyEditor(familyManager.getBaptismPlaces());
		binder.registerCustomEditor(BaptismPlace.class, baptismPlacesEditor);
		
		base.bind(binder);
		
	}

	@RequestMapping(value = "/baptismKnowActEdit", method = RequestMethod.GET)
	protected ModelAndView showForm(
				@ModelAttribute("person") Person person, 
				@RequestParam("personId") Long personId,
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{

		ModelAndView mv = new ModelAndView("print/baptismKnowActEdit");
		
		person = personManager.get(personId);
	
		if (person.getRemaining() == null)
		{
			person.setRemaining(new Remaining());
		}
		if (person.getRemaining().getBaptism() == null)
		{
			person.getRemaining().setBaptism(new Baptism());
		}
		if (person.getRemaining().getBaptismKnowAct() == null)
		{
			//person.getRemaining().setBaptismKnowAct(new BaptismKnowAct());
			
			person.getRemaining().setBaptismKnowAct((BaptismKnowAct) ObjectTraverser.fillMockData(BaptismKnowAct.class, person.getRemaining(), "", 0));
			/*person.getRemaining().getBaptismKnowAct().setAcquaintanceFrom("urodzenia");
			person.getRemaining().getBaptismKnowAct().setBaptismGiver("troll");
			person.getRemaining().getBaptismKnowAct().setBaptismKnow("byl");
			person.getRemaining().getBaptismKnowAct().setBaptismPlace(new BaptismPlace());
			person.getRemaining().getBaptismKnowAct().setBaptismPlaceAdditional("");
			person.getRemaining().getBaptismKnowAct().setDate(new Date());
			person.getRemaining().getBaptismKnowAct().setGodfather("");
			person.getRemaining().getBaptismKnowAct().setGodmother("");*/
			person.getRemaining().getBaptismKnowAct().getWitness().getPractising().setId(1L);
		}
		if (person.getRemaining().getBaptismKnowAct().getWitness() == null)
		{
			person.getRemaining().getBaptismKnowAct().setWitness(new BaptismWitness());
		}
		Object o = familyManager.getBaptismPlaces();
		mv.addObject("baptismPlaces", o);
		mv.addObject("person", person);

		return mv;
	}

	@RequestMapping(value = "/baptismKnowActEdit", method = RequestMethod.POST)
	public ModelAndView onSubmit(
				@ModelAttribute("person") Person person,
				@RequestParam(value="personId") Long personId,
				@ModelAttribute("successMessages") Messages<String> msg, 
				@ModelAttribute("errors") Errors<String> err) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			BaptismKnowAct baptismKnowAct = person.getRemaining().getBaptismKnowAct();
			person = personManager.get(personId);
			person.getRemaining().setBaptismKnowAct(baptismKnowAct);
			personManager.savePerson(person);
			msg.add(base.getText("person.saved", locale));
			return new ModelAndView(new RedirectView("baptismKnowActEdit.html")).addObject(Constants.PERSON_ID, personId);
		}
		catch (AccessDeniedException ade)
		{
			// thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
			log.warn(ade.getMessage());
			throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
		}
		catch (ObjectExistsException e)
		{
			err.add(base.getText("errors.existing.person", locale));
			return new ModelAndView("print/baptismKnowActEdit");
		}
		catch (Exception e) {
			err.add(base.getText("errors.parameternotfound", locale));
			return new ModelAndView(new RedirectView("baptismKnowActEdit.html")).addObject(Constants.PERSON_ID, personId);
		}
	}
}
