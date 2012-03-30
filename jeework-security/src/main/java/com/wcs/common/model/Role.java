package com.wcs.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.wcs.base.entity.IdEntity;

/**
 * 
 * <p>Project: cmdpms</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">chenlong</a>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "role")
public class Role extends IdEntity {
    /** 角色名称*/
    private String roleName = "";
    /** 角色范围*/
    private String roleScope = "";
    /** 角色状态*/
    private int state;
    /** 是否管理员角色*/
    private boolean isAdmin = false;
    /** 角色描述*/
    private String description = "";
    /** 角色资源*/
    private Set<RoleResource> roleResources = new HashSet<RoleResource>();
    /** 用户角色*/
    private Set<UserRole> userRoles = new HashSet<UserRole>();
    private Set<Permission> permissions = new HashSet<Permission>();

    public Role() {
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "role_Name")
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "role_Scope")
    public String getRoleScope() {
        return this.roleScope;
    }

    public void setRoleScope(String roleScope) {
        this.roleScope = roleScope;
    }

    @Column(name = "state", nullable = false)
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    public Set<RoleResource> getRoleResources() {
        return this.roleResources;
    }

    public void setRoleResources(Set<RoleResource> roleResources) {
        this.roleResources = roleResources;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Column(name = "is_admin", columnDefinition = "smallint")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
