package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class NicheFindFilter implements Serializable {
	private static final long serialVersionUID = 9203707647934233215L;

	private String ownerFirstName;
	private String ownerSurname;
	private String deadPersonFirstName;
	private String deadPersonSurname;
	private String number;

	public String getNumber() {
		return number;
	}
	public void setNumber(String nicheNumber) {
		this.number = nicheNumber;
	}
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	public void setOwnerFirstName(String graveOwnerFirstName) {
		this.ownerFirstName = graveOwnerFirstName;
	}
	public String getOwnerSurname() {
		return ownerSurname;
	}
	public void setOwnerSurname(String graveOwnerSurname) {
		this.ownerSurname = graveOwnerSurname;
	}
	public String getDeadPersonFirstName() {
		return deadPersonFirstName;
	}
	public void setDeadPersonFirstName(String deadPersonFirstName) {
		this.deadPersonFirstName = deadPersonFirstName;
	}
	public String getDeadPersonSurname() {
		return deadPersonSurname;
	}
	public void setDeadPersonSurname(String deadPersonSurname) {
		this.deadPersonSurname = deadPersonSurname;
	}
	
	public boolean isNotBlank() {
		if (StringUtils.isNotBlank(ownerFirstName) || StringUtils.isNotBlank(ownerSurname) ||
				StringUtils.isNotBlank(deadPersonFirstName) || StringUtils.isNotBlank(deadPersonSurname) ||
				StringUtils.isNotBlank(number))
			return true;
		else
			return false;
	}
}