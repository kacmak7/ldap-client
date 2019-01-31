package org.parafia.webapp.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.FianceePair;
import org.parafia.model.Messages;
import org.parafia.model.Person;
import org.parafia.model.User;
import org.parafia.model.printcards.Marriage;
import org.parafia.model.printcards.Remaining;
import org.parafia.service.FianceeManager;
import org.parafia.service.PersonManager;
import org.parafia.service.UserManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class FianceeEditController {
	
	private Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private FianceeManager fianceeManager;
	@Autowired
	private UserManager usrMgr;
	@Autowired
	private BaseControllerTemp base;
	@Autowired
	private PersonManager personManager;
	
	@InitBinder
	private void initBinder(final WebDataBinder binder) {
		base.bind(binder);
	}

	@ModelAttribute("successMessages")
	public Messages<String> getMessages() {
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors() {
		return new Errors<>();
	}

	@RequestMapping(value = "/print/fianceesform", method = RequestMethod.GET)
	public ModelAndView showForm(
			@RequestParam(value = "fianceePairId", required  = false) Long fianceePairId,
			@RequestParam(value = "person1Id", required = false) Long person1Id,
			@RequestParam(value = "person2Id", required = false) Long person2Id) throws Exception {
		
		FianceePair fianceePair = null;
		
		if (fianceePairId != null) {
			fianceePair = fianceeManager.getFianceePair(fianceePairId);
		} else {
			fianceePair = new FianceePair();
			
			Person person1 = personManager.getPerson(person1Id);
			Person person2 = personManager.getPerson(person2Id);			
			setHeAndShe(fianceePair, person1, person2);
		}
		if (fianceePair.getFianceeHe().getRemaining() == null)
			fianceePair.getFianceeHe().setRemaining(new Remaining());
		if (fianceePair.getFianceeHe().getRemaining().getMarriage() == null)
			fianceePair.getFianceeHe().getRemaining().setMarriage(new Marriage());

		if (fianceePair.getFianceeShe().getRemaining() == null)
			fianceePair.getFianceeShe().setRemaining(new Remaining());
		if (fianceePair.getFianceeShe().getRemaining().getMarriage() == null)
			fianceePair.getFianceeShe().getRemaining().setMarriage(new Marriage());

		ModelAndView mv = new ModelAndView("print/fianceeEdit");
		mv.addObject("fianceePair", fianceePair);

		return mv;
	}

	@RequestMapping(value = "/print/fianceesform/delete", method = RequestMethod.POST)
	public ModelAndView processFormSubmission(
					@RequestParam(value = "fianceePairId", required = false) Long fianceePairId,
					@RequestParam(value = "code", required = false) String code,
					@ModelAttribute("successMessages") Messages<String> msg,
					@ModelAttribute("errors") Errors<String> err ) throws Exception {

			log.debug("Kod zabezpieczajacy: " + code);

			Locale locale = LocaleContextHolder.getLocale();
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication authentication = ctx.getAuthentication();
			
			User user = usrMgr.loadUserByUsername(authentication.getName());		
			if (user.getDeleteCode() != null && user.getDeleteCode().equals(code)) {
				fianceeManager.remove(fianceePairId);
				msg.add(base.getText("fiancee.fianceepair.deleted", locale));
				return new ModelAndView(new RedirectView("/tomes/fianceesTome.html", true));
			} else {
				msg.add(base.getText("errors.invaliddeletecode", locale));
				return new ModelAndView(new RedirectView("/print/fianceesform.html", true)).addObject(Constants.FIANCEE_PAIR_ID, fianceePairId);
			}
	}

	@RequestMapping(value = "/print/fianceesform", method = RequestMethod.POST)
	public ModelAndView onSubmit(
			@RequestParam(value = "fianceePairId", required = false) Long fianceePairId,
			@ModelAttribute(value = "fianceePair") FianceePair fianceePair,
			@ModelAttribute("successMessages") Messages<String> msg, 
			@ModelAttribute("errors") Errors<String> err) throws Exception {
		
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		try {
			if (fianceePair.getId() != null) {
				FianceePair loadedFP = fianceeManager.get(fianceePair.getId());
				setObjects(fianceePair, loadedFP);
			}
			fianceeManager.premergeSave(fianceePair);
			fianceePair = fianceeManager.saveFianceePair(fianceePair);
			msg.add(base.getText("fiancee.fianceepair.saved", locale));
		} catch (AccessDeniedException ade) {
			// thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
			log.warn(ade.getMessage());
			throw new org.parafia.exceptions.AccessDeniedException(ade.getMessage());
		} catch (ObjectExistsException e) {
			err.add(base.getText("errors.existing.fianceepair", locale));
		}
		return showForm(fianceePairId, fianceePair.getFianceeHe().getId(), fianceePair.getFianceeShe().getId());
	}
	
	private void setHeAndShe(FianceePair fp, Person person1, Person person2) {
		fp.setFianceeHe(person1);
		fp.setFianceeShe(person2);
	}
	
	private void setObjects(FianceePair fp, FianceePair loadedFianceePair) {
		fp.setDispensation(loadedFianceePair.getDispensation());
		fp.setAssistanceDelegation(loadedFianceePair.getAssistanceDelegation());
		fp.setLicenseAssistance(loadedFianceePair.getLicenseAssistance());
		fp.setCertificate(loadedFianceePair.getCertificate());	
	}
}
