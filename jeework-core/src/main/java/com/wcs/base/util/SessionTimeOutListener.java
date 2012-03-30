/**
 * TimeOutProcess.java
 * Created: 2011-10-25 上午10:18:32
 */
package com.wcs.base.util;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 * <p>Project: cmdpms</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yourname@wcs-global.com">Your Name</a>
 */
public class SessionTimeOutListener implements PhaseListener{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SessionTimeOutListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void afterPhase(PhaseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // TODO Auto-generated method stub
        
        FacesContext context = event.getFacesContext();  
        ExternalContext externalContext = context.getExternalContext();  
        HttpSession session = (HttpSession) externalContext.getSession(false);  
  
        boolean newSession = session == null || session.isNew();  
//        boolean postBack = !externalContext.getRequestParameterMap().isEmpty();//form submit  
  
        if (newSession) {//timeout  
            ViewHandler viewHandler = context.getApplication().getViewHandler();  
            UIViewRoot viewRoot = viewHandler.createView(context, "/login.xhtml");  
            context.setViewRoot(viewRoot);  
            context.renderResponse();  
            try {  
                viewHandler.renderView(context, viewRoot);  
                context.responseComplete();  
            } catch (Exception e) {  
                throw new FacesException("Session is timeout", e);  
            }  
        }  
        
    }

    @Override
    public PhaseId getPhaseId() {
        // TODO Auto-generated method stub
        return PhaseId.RESTORE_VIEW;
    }

}
