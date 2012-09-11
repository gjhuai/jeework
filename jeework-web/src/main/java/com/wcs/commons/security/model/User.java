package com.wcs.commons.security.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Lists;
import com.wcs.base.entity.IdEntity;
import com.wcs.commons.security.model.master.Person;


/**
 * 
 * @author Chris Guan
 */
@Entity
@Table(name = "users")
public class User extends IdEntity {

	@Column(name="AD_ACCOUNT", length=50)
	private String adAccount;  // TDS帐号(LDAP帐号)

	@Column(length=20)
	private String pernr;  //员工工号 Person->ID
	
	@Column(name="DEFUNCT_IND", length=1)
	private String defunctInd;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@OrderBy("id")
	private List<Role> roleList = Lists.newArrayList();
	
	@Transient
	private Person person = new Person();
	
	public User(){}
	
	public User(String adAccount, String pernr){
		this.adAccount=adAccount;
		this.pernr = pernr;
	}
	
	public User(Long id, String adAccount, Person person){
		this.setId(id);
		this.adAccount=adAccount;
		this.person = person;
	}
	
	public User(Long id, String adAccount, String userDefunctInd,
			String personId, String bukrs, String celno, String personDefunctInd,
			String email, String gesch, String icnum, String kostl,
			String nachn, String name2, String orgeh, String telno) {
		
		this.setId(id);
		this.setAdAccount(adAccount);
		this.setDefunctInd(userDefunctInd);
		
		person.setId ( personId );
		person.setBukrs(bukrs);
		person.setCelno(celno);
		person.setDefunctInd(personDefunctInd);
		person.setEmail(email);
		person.setGesch(gesch);
		person.setIcnum(icnum);
		person.setKostl(kostl);
		person.setNachn(nachn);
		person.setName2(name2);
		person.setOrgeh(orgeh);
		person.setTelno(telno);
	}

	//--------------------- setter & getter -------------------//
	
	@PrePersist
	private void initData(){
		this.defunctInd = "N";
	}
	
	public String getAdAccount() {
		return this.adAccount;
	}

	public void setAdAccount(String adAccount) {
		this.adAccount = adAccount;
	}

	public String getPernr() {
		return this.pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}

	public String getDefunctInd() {
		return defunctInd;
	}

	public void setDefunctInd(String defunctInd) {
		this.defunctInd = defunctInd;
	}

	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}