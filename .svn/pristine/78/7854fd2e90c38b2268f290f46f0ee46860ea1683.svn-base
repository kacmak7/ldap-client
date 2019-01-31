package org.parafia.webapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractGraveyardHelper {
	@Value("${geoserver.url}")
	private String geoUrl;
	
	/**
	  * Gets the geoUrl from configuration
	  * @return String geoUrl
	  */
	@ModelAttribute("geoUrl")
	public String getGeoUrl() {
		return geoUrl;
	}
}
