package org.parafia.webapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.GraveyardManager;
import org.parafia.webapp.controller.commands.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Taki sam jak GraveyardController tylko operuje na jednym sektorze, a nie calosci
 * @author pawel
 *
 */

@Controller
public class GraveyardSectorController extends AbstractGraveyardHelper {
	private Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private GraveyardManager graveyardManager;
	
	@ModelAttribute(Constants.SECTORS_LIST)
	public List<String> getSectorsList() {
		List<String> sectors = graveyardManager.getSectors();
		//if (graves.size() == 0)
		//	saveError(request, getText("errors.no-graves-sectors", request.getLocale()));
		/*for (String grave : graves) {
			if (grave == null) {
				grave = "0";
			}
		}*/
		for (int i = 0; i < sectors.size(); i++) {
			if (sectors.get(i) == null) {
				if (sectors.contains("0"))
					sectors.remove(i);
				else
					sectors.set(i, "0");
				break;
			}
		}
		
		return sectors;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/yard/yardSector")
	public ModelAndView initGraveSector() {
	   	return new ModelAndView("/yard/yardSector").addObject("sector", new Sector());
	}
	
    @RequestMapping(method = RequestMethod.POST, value="/yard/yardSector")
	public ModelAndView onSubmit(@ModelAttribute("sector") Sector sector) throws Exception {
	    log.debug("entering 'onSubmit' method...");
		return new ModelAndView("/yard/yard").addObject("sectorx", sector.getSector());
	}
}
