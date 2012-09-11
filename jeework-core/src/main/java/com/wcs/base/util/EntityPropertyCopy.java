/**
 * EntityPropertyCopy.java
 * Created: 2011-8-13 下午04:03:47
 */
package com.wcs.base.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;
/**
 * 
 * <p>Project: ncp</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">chen long</a>
 */
@Named
@Singleton
public class EntityPropertyCopy {
	
	
	public EntityPropertyCopy(){
		
	}
	
	/**
	 * 
	 * <p>Description: 
	 * 实现两个实例对象之间的对拷，将obj1属性值复制到obj2
	 *           
	 * @param obj1 有属性字段值的对象
	 * @param obj2 创建的新对象
	 */
	public static void copeField(Object obj1, Object obj2) {
		// 对obj1反射
		Class<? extends Object> obj1Class = obj1.getClass();
		Method[] obj1methods = obj1Class.getDeclaredMethods();
		// 对obj2反射
		Class<? extends Object> obj2Class = obj2.getClass();
		Method[] obj2methods = obj2Class.getDeclaredMethods();

		// 把obj2的方法影射到MAP中，方便调用
		Map<String, Method> obj2MeMap = new HashMap<String, Method>();
		for (int i = 0; i < obj2methods.length; i++) {
			Method method = obj2methods[i];
			obj2MeMap.put(method.getName(), method);
		}
		for (int i = 0; i < obj1methods.length; i++) {
			String methodName = obj1methods[i].getName();
			if (methodName != null && methodName.startsWith("get")) {
				// 取得对象里的FIELD
				try {
					// 调用obj1实例中的getXXXX方法
					Object returnObj = obj1methods[i].invoke(obj1,
							new Object[0]);
					// 获取obj2的set方法
					Method obj2method = (Method) obj2MeMap.get("set"
							+ methodName.split("get")[1]);
					// 调用obj2实例中的setXXX方法
					if (returnObj != null) {
						obj2method.invoke(obj2, returnObj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
