package org.parafia.webapp.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.parafia.model.BaseObject;

public class CustomPropertyEditorCheckbox extends PropertyEditorSupport {
	Logger log = Logger.getLogger(getClass());
	private Collection<? extends BaseObject> objects;
	
	public CustomPropertyEditorCheckbox(Collection<? extends BaseObject> objects) {
		this.objects = objects;
	}
	
	/**
	 * Parse the Product from the given text
	 */
	public void setAsText(String text) {
		log.debug(text);
		boolean set = false;
		for (BaseObject object : objects) {
			if ((object.getId() != null) && object.getId().longValue() == Long.valueOf(text)) {
				setValue(object);
				set = true;
			}
		}
		if (!set)
			setValue(null);
	}

	/**
	 * Format the Product as String (get it's id)
	 */
	public String getAsText() {
		BaseObject object = (BaseObject) getValue();
		if (object != null && object.getId() != null)
			return String.valueOf(object.getId());
		else
			return "";
	}
}
