package com.wcs.commons.security.model.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the PS database table.
 * 
 */
@Entity
@Table(name="PS")
public class PS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, length=20)
	private String id;

	@Column(name="DEFUNCT_IND", length=1)
	private String defunctInd;

	@Column(name="MAIN_IND", length=200)
	private String mainInd;

	@Column(nullable=false, length=20)
	private String pid;   //person_id

	@Column(nullable=false, length=20)
	private String sid;		//station_id

    public PS() {
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

	public String getMainInd() {
		return this.mainInd;
	}

	public void setMainInd(String mainInd) {
		this.mainInd = mainInd;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}