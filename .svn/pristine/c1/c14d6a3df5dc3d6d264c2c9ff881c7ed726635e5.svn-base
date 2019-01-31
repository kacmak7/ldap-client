package org.parafia.webapp.controller.commands;

import java.io.Serializable;
import java.util.Date;

public class IntentionFindFilter implements Serializable {
	private static final long serialVersionUID = -3672931864514556621L;

	private String dateFrom;
	private String dateTo;

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public boolean isNotBlank() {
		if (dateFrom != null || dateTo != null)
			return true;
		else
			return false;
	}
}