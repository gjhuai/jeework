/*
 * Created on Mar 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.wcs.base.report;

/**
 * @author olivieri
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Field {
	private String name;
	private String clazzName;
	private String expression;
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return Returns the clazzName.
	 */
	public String getClazzName() {
		return clazzName;
	}
	/**
	 * @param clazzName The clazzName to set.
	 */
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	/**
	 * @return Returns the expression.
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression The expression to set.
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
}
