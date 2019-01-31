package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class PersonFindFilter implements Serializable {
	private static final long serialVersionUID = 8998855203922495457L;

	private String firstName;
	private String surname;

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
	
	public boolean isNotBlank() {
		if (StringUtils.isNotBlank(firstName) || StringUtils.isNotBlank(surname))
			return true;
		else
			return false;
	}
}
