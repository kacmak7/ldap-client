package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class GraveFindFilter implements Serializable {
	private static final long serialVersionUID = -3672931864514556621L;

	private String graveOwnerFirstName;
	private String graveOwnerSurname;
	private String deadPersonFirstName;
	private String deadPersonSurname;
	private String graveNumber;

	public String getGraveNumber() {
		return graveNumber;
	}
	public void setGraveNumber(String graveNumber) {
		this.graveNumber = graveNumber;
	}
	public String getGraveOwnerFirstName() {
		return graveOwnerFirstName;
	}
	public void setGraveOwnerFirstName(String graveOwnerFirstName) {
		this.graveOwnerFirstName = graveOwnerFirstName;
	}
	public String getGraveOwnerSurname() {
		return graveOwnerSurname;
	}
	public void setGraveOwnerSurname(String graveOwnerSurname) {
		this.graveOwnerSurname = graveOwnerSurname;
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
		if (StringUtils.isNotBlank(graveOwnerFirstName) || StringUtils.isNotBlank(graveOwnerSurname) ||
				StringUtils.isNotBlank(deadPersonFirstName) || StringUtils.isNotBlank(deadPersonSurname) ||
				StringUtils.isNotBlank(graveNumber))
			return true;
		else
			return false;
	}
}