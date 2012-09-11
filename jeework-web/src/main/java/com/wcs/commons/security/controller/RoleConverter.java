package com.wcs.commons.security.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.wcs.commons.security.model.Role;

@FacesConverter(value="roleConverter") 
public class RoleConverter extends Object implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) { 
	    if (!(component instanceof PickList)) {
	    	return null;
	    }
	    
		Object dualList = ((PickList) component).getValue();
		@SuppressWarnings("unchecked")
		DualListModel<Role> dl = (DualListModel<Role>) dualList;

		for (Role o : dl.getSource()) {
			if (o.getId().equals(Long.valueOf(value))) {
				return o;
			}
		}

		for (Role o : dl.getTarget()) {
			if (o.getId().equals(Long.valueOf(value))) {
				return o;
			}
		}
			
	    return null;
	} 

	public String getAsString(FacesContext context, UIComponent component, Object value) { 
		return ((Role)value).getId().toString();
	}
	
}
