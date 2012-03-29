/** * ReportMstr.java 
* Created on 2011-12-6 下午1:55:18 
*/

package com.wcs.report.model;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportMstr.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "REPORTMSTR")
@SuppressWarnings("serial")
public class ReportMstr extends BaseEntity {
	
	private String reportCode;         // 报表编码
	private String reportName;         // 报表名字
	private String reportNameLang;     // 报表英文名字
	private String reportMode;         // 报表模式
	private String reportCategory;     // 报表分类
	private String remarks;            // 备注
	private String reportConfig;       // 报表配置
	
	private Set<ReportParameter> reportParameters = new HashSet<ReportParameter>();
	private Set<ReportFile> reportFiles = new HashSet<ReportFile>();
	private Set<ReportRole> reportRoles = new HashSet<ReportRole>();

	@Override
	@Transient
	public String getDisplayText() {
		return null;
	}
	
	public ReportMstr() {
		
	}

	@Column(name = "REPORT_CODE", length = 50)
	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	@Column(name = "REPORT_NAME", nullable = false, length = 200)
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name = "REPORT_NAME_LANG", length = 200)
	public String getReportNameLang() {
		return reportNameLang;
	}

	public void setReportNameLang(String reportNameLang) {
		this.reportNameLang = reportNameLang;
	}

	@Column(name = "REPORT_MODE", length = 20)
	public String getReportMode() {
		return reportMode;
	}

	public void setReportMode(String reportMode) {
		this.reportMode = reportMode;
	}

	@Column(name = "REPORT_CATEGORY", length = 20)
	public String getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	@Column(name = "REMARKS", length = 300)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportMstr")
	public Set<ReportParameter> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(Set<ReportParameter> reportParameters) {
		this.reportParameters = reportParameters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportMstr")
	public Set<ReportFile> getReportFiles() {
		return reportFiles;
	}

	public void setReportFiles(Set<ReportFile> reportFiles) {
		this.reportFiles = reportFiles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportMstr")
	public Set<ReportRole> getReportRoles() {
		return reportRoles;
	}

	public void setReportRoles(Set<ReportRole> reportRoles) {
		this.reportRoles = reportRoles;
	}

	@Column(name = "REPORT_CONFIG", length = 600)
	public String getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(String reportConfig) {
		this.reportConfig = reportConfig;
	}

}
