package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class FamilyFindFilter implements Serializable {
	private static final long serialVersionUID = -2086594079208818331L;

	private String surname;
	private String street;
	private String firstNumber;

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getFirstNumber() {
		return firstNumber;
	}
	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}
	
	public boolean isNotBlank() {
		if (StringUtils.isNotBlank(street) || StringUtils.isNotBlank(surname) || StringUtils.isNotBlank(firstNumber))
			return true;
		else
			return false;
	}
}
