package com.wcs.commons.security.model.master;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Lists;


/**
 * The persistent class for the P database table.
 * 
 */
@Entity
@Table(name="P")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, length=20)
	private String id;

	@Column(length=200)
	private String bukrs;

	@Column(length=200)
	private String celno;

	@Column(name="DEFUNCT_IND", length=1)
	private String defunctInd;

	@Column(length=200)
	private String email;

	@Column(length=200)
	private String gesch;

	@Column(length=200)
	private String icnum;

	@Column(length=200)
	private String kostl;

	@Column(length=200)
	private String nachn;	//姓名

	@Column(length=200)
	private String name2;

	@Column(length=200)
	private String orgeh;

	@Column(length=200)
	private String telno;
	
	@Transient
	private String adAcccount;

	@ManyToMany
	@JoinTable(name = "PS", joinColumns = { @JoinColumn(name = "pid") }, inverseJoinColumns = { @JoinColumn(name = "sid") })
	private List<Station> stationList = Lists.newArrayList();

	public Person(){}
	
	public Person(String id, String bukrs, String celno, String defunctInd,
			String email, String gesch, String icnum, String kostl,
			String nachn, String name2, String orgeh, String telno) {
		this.id = id;
		this.bukrs = bukrs;
		this.celno = celno;
		this.defunctInd = defunctInd;
		this.email = email;
		this.gesch = gesch;
		this.icnum = icnum;
		this.kostl = kostl;
		this.nachn = nachn;
		this.name2 = name2;
		this.orgeh = orgeh;
		this.telno = telno;
	}

	//--------------------- setter & getter -------------------//
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBukrs() {
		return this.bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getCelno() {
		return this.celno;
	}

	public void setCelno(String celno) {
		this.celno = celno;
	}

	public String getDefunctInd() {
		return this.defunctInd;
	}

	public void setDefunctInd(String defunctInd) {
		this.defunctInd = defunctInd;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGesch() {
		return this.gesch;
	}

	public void setGesch(String gesch) {
		this.gesch = gesch;
	}

	public String getIcnum() {
		return this.icnum;
	}

	public void setIcnum(String icnum) {
		this.icnum = icnum;
	}

	public String getKostl() {
		return this.kostl;
	}

	public void setKostl(String kostl) {
		this.kostl = kostl;
	}

	public String getNachn() {
		return this.nachn;
	}

	public void setNachn(String nachn) {
		this.nachn = nachn;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getOrgeh() {
		return this.orgeh;
	}

	public void setOrgeh(String orgeh) {
		this.orgeh = orgeh;
	}

	public String getTelno() {
		return this.telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public List<Station> getStationList() {
		return stationList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}
	
}