package com.wcs.base.exception.jsf;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.wcs.base.util.JSFUtils;
/**
 * 处理Conversion过期的问题。如果Conversation过期，那么页面转向去掉URL的cid参数后的URL。
 * @author Chris
 */
class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler parent;

	public CustomExceptionHandler(ExceptionHandler parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.parent;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			
			if (t instanceof javax.enterprise.context.NonexistentConversationException) {
				//System.out.println("====> "+JSFUtils.getRequestURI());
				try {
					JSFUtils.redirect(JSFUtils.getRequestURI());
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					i.remove();
				}
			}
		}
		getWrapped().handle();
	}
}
