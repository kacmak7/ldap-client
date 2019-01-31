package org.parafia.webapp.util;

import org.displaytag.decorator.TableDecorator;
import org.parafia.model.Addressee;

public class AddresseeLinkDecorator extends TableDecorator {
	public String getLink() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getId()+"</a>";
	}
	
	public String getFullName() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getFullName()+"</a>";
	}
	
	public String getInstitution() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getInstitution()+"</a>";
	}
	
	public String getStreet() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getStreet()+"</a>";
	}
	
	public String getPost() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getPost()+"</a>";
	}
	
	public String getCountry() {
	    Addressee addressee = (Addressee)getCurrentRowObject();
	    return "<a href=\"javascript:showEdit(" + addressee.getId() + ");\">"+addressee.getCountry()+"</a>";
	}
}
