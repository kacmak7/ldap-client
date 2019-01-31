package org.parafia.webapp.controller;

import java.util.List;

import org.parafia.model.Addressee;
import org.parafia.service.AddresseeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddresseesController {
	@Autowired
	private AddresseeManager addresseeManager;
	
	@RequestMapping(method = RequestMethod.GET, value="/mail/addressees")
	public ModelAndView listAddresses() {
		ModelAndView mv = new ModelAndView("/mail/addresseesList");
		
		List<Addressee> addressees = addresseeManager.getAll();
		
		return mv.addObject("addresseesList", addressees);
	}
}
