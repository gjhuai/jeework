/** * BaseUtils.java 
* Created on 2011-11-8 上午9:03:23 
*/

package com.wcs.base.util;

import java.io.Serializable;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/** 
* <p>Project: btcbase</p> 
* <p>Title: BaseUtils.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2005-2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

@SuppressWarnings("serial")
public class BaseUtils implements Serializable {
    
	/**
	 * 得到Session
	 * @return
	 */
	public static HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false); 
		
		return session;
	}
}
