package com.wcs.showcase.crud.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: </p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "TEACHER")
@SuppressWarnings("serial")
public class Teacher extends BaseEntity {
	
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;           // 姓名

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY")	
    private Date birthday;         // 出生日期

    @Column(name = "SEX", length = 10)
    private String sex;            // 性别

    @Column(name = "PHONE", length = 20)
    private String phone;          // 电话

    @Column(name = "ADDRESS", length = 100)
    private String address;        // 地址

    @Column(name = "EMAIL", length = 50)
    private String email;          // 邮箱

    @Column(name = "NATIONALITY", length = 50)
    private String nationality;    // 国籍

    @Column(name = "VIP")
    private Boolean vip;           // VIP

    @Column(name = "REMARKS", length = 500)
    private String remarks;        // 备注
	
	@Override
	@Transient
	public String getDisplayText() {
		return this.getName();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	
	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

}
