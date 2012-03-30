package com.wcs.common.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: User.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
	private String userName;                                           // 姓名
	private String loginName;                                          // 用户账户
	private String userPass;                                           // 用户密码
	private String cellPhone;                                          // 手机
	private String telephone;                                          // 联系电话
	private String email;                                              // 电子邮箱
	private String address;                                            // 联系地址
	private Set<UserRole> userRoles = new HashSet<UserRole>();         // 用户角色

	public User() {}

	public User(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_NAME", nullable = false, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "LOGIN_NAME", nullable = false, length = 30)
	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "USER_PASS", nullable = false, length = 20)
	public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Column(name = "CELL_PHONE", length = 20)
    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Column(name = "TELEPHONE", length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "EMAIL", length = 60)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "ADDRESS", length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

    @Transient
    @Override
    public String getDisplayText() {
        return null;
    }
}
