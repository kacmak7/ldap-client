package org.parafia.model;

import java.util.Date;

import javax.persistence.Transient;

public abstract class CommonIntention extends BaseObject implements Cloneable {
	private static final long serialVersionUID = 1404815343317455420L;
	
	private Date toDate;

    public String replaceEols(String text) {
    	String what = System.getProperty("line.separator");
    	return text.replaceAll(what, "<br>");
    }
    
	@Transient
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public abstract void setDate(Date d);
	public abstract Date getDate();
	public abstract CommonIntention clone();
}
