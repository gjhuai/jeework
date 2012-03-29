/** * ReportRole.java 
* Created on 2011-12-6 下午4:09:27 
*/

package com.wcs.report.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;
import com.wcs.common.model.Role;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportRole.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "REPORTROLE")
@SuppressWarnings("serial")
public class ReportRole extends BaseEntity {
	
	private ReportMstr reportMstr;
	private Role role;

	@Override
	@Transient
	public String getDisplayText() {
		return null;
	}
	
	public ReportRole() {
		
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTMSTR_ID")
	public ReportMstr getReportMstr() {
		return reportMstr;
	}

	public void setReportMstr(ReportMstr reportMstr) {
		this.reportMstr = reportMstr;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
