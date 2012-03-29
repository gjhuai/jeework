package com.wcs.demo.model;

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
* <p>Title: Person.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "PERSON")
@SuppressWarnings("serial")
public class Person extends BaseEntity {
	
	private String name;           // 姓名
    private Date birthday;         // 出生日期
    private String sex;            // 性别
    private String phone;          // 电话
    private String address;        // 地址
    private String email;          // 邮箱
    private String nationality;    // 国籍
    private Boolean vip;           // VIP
    private String remarks;        // 备注
	
	@Override
	@Transient
	public String getDisplayText() {
		return this.getName();
	}
	
	public Person() {
		
	}
	
	public Person(String name) {
		this.name = name;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "SEX", length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "REMARKS", length = 500)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "NATIONALITY", length = 50)
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "VIP", columnDefinition="smallint")
	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

}
