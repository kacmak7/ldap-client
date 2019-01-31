package org.parafia.webapp.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Collection;

import org.parafia.model.BaseObject;

public class CustomPropertyEditor extends PropertyEditorSupport {
	private Collection<? extends BaseObject> objects;
	
	public CustomPropertyEditor(Collection<? extends BaseObject> objects) {
		this.objects = objects;
	}
	
	/**
	 * Parse the Product from the given text
	 */
	public void setAsText(String text) {
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
