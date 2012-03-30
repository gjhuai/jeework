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
 * A column is composed of one or more fields
 */
public class Column {
	
	private String name;
	private int width;
	private String alignment;
	private String clazzType;
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
	 * @return Returns the width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return Returns the alignment.
	 */
	public String getAlignment() {
		return alignment;
	}
	/**
	 * @param alignment The alignment to set.
	 */
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	/**
	 * @return Returns the clazzType.
	 */
	public String getClazzType() {
		return clazzType;
	}
	/**
	 * @param clazzType The clazzType to set.
	 */
	public void setClazzType(String clazzType) {
		this.clazzType = clazzType;
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
