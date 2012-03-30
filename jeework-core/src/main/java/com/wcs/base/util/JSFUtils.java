package com.wcs.base.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wcs.base.conf.SystemConfiguration;

public class JSFUtils {
	public static Flash flashScope (){
		return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
	}
	
	public static String contextPath(){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	
	public static ExternalContext externalContext(){
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public static void redirect(String url) throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}
	
	public static void forward(String url) throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().dispatch(url);
	}
	
	public static void handleException(String message) {
		try {
			JSFUtils.flashScope().put("exceptionMessage", message);
			JSFUtils.redirect(JSFUtils.contextPath() + SystemConfiguration.EXCEPTION_HANDLE_PAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static HttpServletRequest getRequest(){
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getResponse(){
		return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
	public static String getRequestParam(String name) {
		Map<String,String> params = externalContext().getRequestParameterMap();
		return params.get(name);
	}
	
	public static String[] getRequestParamArray(String name){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String[]> params = fc.getExternalContext().getRequestParameterValuesMap();
		return params.get(name);
	}
	
	public static Map<String,Object> getSession(){
	    return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	
	public static String getRequestURI(){
		HttpServletRequest request = getRequest();
		return request.getRequestURI();
	}
	
	public static String getViewId(){
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        return viewRoot.getViewId();
	}
	public static String getNoContextRequestUrl(){
		return getRequestURI();
	}
	
	public static String getRealPath(){
		return getRequest().getSession().getServletContext().getRealPath("/");
	}
}
