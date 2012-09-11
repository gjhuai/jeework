/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Fixtures.java 1593 2011-05-11 10:37:12Z calvinxiu $
 */
package com.wcs.base.util;

import java.io.IOException;

import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.exception.message.ExceptionKeyMessage;
import com.wcs.base.exception.message.ExceptionMessage;

public class ExceptionUtils extends org.apache.commons.lang.exception.ExceptionUtils{

	/**
	 * 将CheckedException转换为UnCheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e.getMessage(), e);
	} 
	
	public static <E extends Enum<?>> void handleException(E key, Object... args) {
		try {
			ExceptionMessage message = new ExceptionKeyMessage(key,args);

			JSFUtils.flashScope().put("exceptionMessage", message.getAsString());
			JSFUtils.redirect(JSFUtils.contextPath() + SystemConfiguration.EXCEPTION_HANDLE_PAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
