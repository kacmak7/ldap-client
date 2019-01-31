package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class BapConDeaFindFilter implements Serializable {
	private static final long serialVersionUID = 8998855203922495457L;

	private String firstName;
	private String surname;
	private int year;
	private int burialYear; // only to death model

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getBurialYear() {
		return burialYear;
	}
	public void setBurialYear(int burialYear) {
		this.burialYear = burialYear;
	}
	public boolean isNotBlank() {
		if (StringUtils.isNotBlank(firstName) || StringUtils.isNotBlank(surname) || year > 0 || burialYear > 0)
			return true;
		else
			return false;
	}
}
