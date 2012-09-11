package com.wcs.base.util;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContainerUtils {
	private static BeanManager beanManager;
	
	static {
		try {
			beanManager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	public static <T> T getBean(Class<? extends T> clazz) {
		Bean<T> bean = (Bean<T>) beanManager.getBeans(clazz).iterator().next();
		CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
		T obj = (T) beanManager.getReference(bean, clazz, ctx);
		return obj;
	}
}
