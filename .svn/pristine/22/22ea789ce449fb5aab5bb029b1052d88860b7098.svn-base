package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class OfferingFilter implements Serializable {
	private static final long serialVersionUID = -2546185474180591012L;

	private String firstDate;
	private String lastDate;

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public boolean isNotBlank() {
		if (StringUtils.isNotBlank(firstDate) || StringUtils.isNotBlank(lastDate))
			return true;
		else
			return false;
	}
}
