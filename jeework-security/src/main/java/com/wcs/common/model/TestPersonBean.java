/** * PersonBean.java 
* Created on 2011-11-1 下午2:47:50 
*/

package com.wcs.common.model;

/** 
* <p>Project: btcbase</p> 
* <p>Title: PersonBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2005-2011.All rights reserved.</p> 
* <p>Company: wcs</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class TestPersonBean {
	private String name;
	private int age;

	public TestPersonBean(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}
}
