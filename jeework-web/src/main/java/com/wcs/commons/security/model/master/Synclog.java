package com.wcs.commons.security.model.master;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wcs.base.entity.IdEntity;


/**
 * The persistent class for the SYNCLOG database table.
 * 
 */
@Entity
@Table(name="SYNCLOG")
public class Synclog extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "REMARKS",length=200)
	private String remarks;

	@Column(name="SYNC_DATETIME",insertable = false)
	private Timestamp syncDatetime;

	@Column(name="SYNC_IND", length=1)
	private String syncInd;

	@Column(name="SYNC_TYPE", length=20)
	private String syncType;

	@Column(name="VERSION", nullable=false)
	private long version;

    public Synclog() {
    }
    
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getSyncDatetime() {
		return this.syncDatetime;
	}

	public void setSyncDatetime(Timestamp syncDatetime) {
		this.syncDatetime = syncDatetime;
	}

	public String getSyncInd() {
		return this.syncInd;
	}

	public void setSyncInd(String syncInd) {
		this.syncInd = syncInd;
	}

	public String getSyncType() {
		return this.syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}