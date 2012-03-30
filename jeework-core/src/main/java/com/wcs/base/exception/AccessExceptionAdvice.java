package com.wcs.base.exception;
import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.util.JSFUtils;
/**
 * @author Chris Guan
 */
@ExceptionHandler @Interceptor
public class AccessExceptionAdvice implements Serializable{
	  
      @AroundInvoke
      public Object handleAccessException(InvocationContext joinPoint) throws Exception {
    	  try{
    		  return joinPoint.proceed();
    	  } catch(ServiceException ae){
    		  JSFUtils.flashScope().put("exceptionMessage", ae.getMessage());
    		  JSFUtils.redirect(JSFUtils.contextPath()+SystemConfiguration.EXCEPTION_HANDLE_PAGE);
    		  return null;
    	  }
      }
}
