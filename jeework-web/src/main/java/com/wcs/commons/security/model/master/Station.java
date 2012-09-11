package com.wcs.commons.security.model.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the "S" database table.
 * 
 */
@Entity
@Table(name="S")
public class Station implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true,length=20)
	private String id;

	@Column(name="DEFUNCT_IND", length=1)
	private String defunctInd;

	@Column(length=200)
	private String kostl;

	@Column(nullable=false, length=20)
	private String oid;  // Organization->id

	@Column(length=200)
	private String stext;

	@Column(length=200)
	private String zhrcjid;

	@Column(length=200)
	private String zhrcjms;

	@Column(length=200)
	private String zhrtxxlid;

	@Column(length=200)
	private String zhrtxxlms;

    public Station() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefunctInd() {
		return this.defunctInd;
	}

	public void setDefunctInd(String defunctInd) {
		this.defunctInd = defunctInd;
	}

	public String getKostl() {
		return this.kostl;
	}

	public void setKostl(String kostl) {
		this.kostl = kostl;
	}

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getStext() {
		return this.stext;
	}

	public void setStext(String stext) {
		this.stext = stext;
	}

	public String getZhrcjid() {
		return this.zhrcjid;
	}

	public void setZhrcjid(String zhrcjid) {
		this.zhrcjid = zhrcjid;
	}

	public String getZhrcjms() {
		return this.zhrcjms;
	}

	public void setZhrcjms(String zhrcjms) {
		this.zhrcjms = zhrcjms;
	}

	public String getZhrtxxlid() {
		return this.zhrtxxlid;
	}

	public void setZhrtxxlid(String zhrtxxlid) {
		this.zhrtxxlid = zhrtxxlid;
	}

	public String getZhrtxxlms() {
		return this.zhrtxxlms;
	}

	public void setZhrtxxlms(String zhrtxxlms) {
		this.zhrtxxlms = zhrtxxlms;
	}

}