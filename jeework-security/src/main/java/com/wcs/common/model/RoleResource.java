package com.wcs.common.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wcs.base.entity.IdEntity;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: RoleResource.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "role_resource")
public class RoleResource extends IdEntity  {
	private Role role;
	private Resource resource;
	

	public RoleResource() {
	}

	public RoleResource(Role role, Resource resource) {
		this.role = role;
		this.resource = resource;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Resource_id")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
