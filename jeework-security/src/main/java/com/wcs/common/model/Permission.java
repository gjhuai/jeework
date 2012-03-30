/** * Permission.java 
* Created on 2011-11-28 下午1:41:47 
*/

package com.wcs.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wcs.base.entity.IdEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: Permission.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

@SuppressWarnings("serial")
@Entity
@Table(name = "permission")
public class Permission extends IdEntity implements Serializable {
    private String permission;
    private String permissionName;
    private Role role;
    
    public Permission() {
    }

    @Column(name = "PERMISSION" , nullable = false, length = 100)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Column(name = "PERMISSION_NAME", length = 40)
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
