/**
 * RoleVO.java
 * Created: 2011-8-14 下午04:12:22
 */
package com.wcs.common.controller.permissions.vo;

import java.io.Serializable;

/**
 * 
 * <p>Project: ncp</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">chen long</a>
 */
public class RoleVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleName;
	private String roleScope;
	private int state;
	private String description;


	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleScope
	 */
	public String getRoleScope() {
		return roleScope;
	}

	/**
	 * @param roleScope
	 *            the roleScope to set
	 */
	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
