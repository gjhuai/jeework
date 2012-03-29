/** * ReportParameter.java 
* Created on 2011-12-6 下午3:03:35 
*/

package com.wcs.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportParameter.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "REPORTPARAMETER")
@SuppressWarnings("serial")
public class ReportParameter extends BaseEntity{
	
	private String reportParameterCode;           // 报表参数编码
	private String uiLabel;                       // 输入框显示的提示
	private String uiLabelLang;                   // 输入框显示的提示英文
	private String uiType;                        // 控件类型
	private String javaDataType;                  // 参数的java类型
	private String defaultValueType;              // 参数默认类型
	private String defaultValue;                  // 参数默认值
	private Boolean mandatoryInd;                 // 是否必需参数
	private Boolean displayInd;                   // 是否显示
	private Boolean editInd;                      // 是否编辑
	private String remarks;                       // 备注
	private String sqlStatement;                  // 报表相关SQL语句
	
	private ReportMstr reportMstr;

	@Override
	@Transient
	public String getDisplayText() {
		return null;
	}
	
	public ReportParameter() {
		
	}

	@Column(name = "REPORTPARAMETER_CODE", nullable = false, length = 30)
	public String getReportParameterCode() {
		return reportParameterCode;
	}

	public void setReportParameterCode(String reportParameterCode) {
		this.reportParameterCode = reportParameterCode;
	}

	@Column(name = "UI_LABEL", length = 50)
	public String getUiLabel() {
		return uiLabel;
	}

	public void setUiLabel(String uiLabel) {
		this.uiLabel = uiLabel;
	}

	@Column(name = "UI_LABEL_LANG", length = 50)
	public String getUiLabelLang() {
		return uiLabelLang;
	}

	public void setUiLabelLang(String uiLabelLang) {
		this.uiLabelLang = uiLabelLang;
	}

	@Column(name = "UI_TYPE", nullable = false, length = 20)
	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	@Column(name = "JAVA_DATA_TYPE", nullable = false, length = 20)
	public String getJavaDataType() {
		return javaDataType;
	}

	public void setJavaDataType(String javaDataType) {
		this.javaDataType = javaDataType;
	}

	@Column(name = "DEFAULT_VALUE_TYPE", length = 20)
	public String getDefaultValueType() {
		return defaultValueType;
	}

	public void setDefaultValueType(String defaultValueType) {
		this.defaultValueType = defaultValueType;
	}

	@Column(name = "DEFAULT_VALUE", length = 200)
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Column(name = "MANDATORY_IND", columnDefinition="smallint")
	public Boolean getMandatoryInd() {
		return mandatoryInd;
	}

	public void setMandatoryInd(Boolean mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}

	@Column(name = "DISPLAY_IND", columnDefinition="smallint")
	public Boolean getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(Boolean displayInd) {
		this.displayInd = displayInd;
	}

	@Column(name = "EDIT_IND", columnDefinition="smallint")
	public Boolean getEditInd() {
		return editInd;
	}

	public void setEditInd(Boolean editInd) {
		this.editInd = editInd;
	}

	@Column(name = "REMARKS", length = 600)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "SQL_STATEMENT", length = 2000)
	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTMSTR_ID")
	public ReportMstr getReportMstr() {
		return reportMstr;
	}

	public void setReportMstr(ReportMstr reportMstr) {
		this.reportMstr = reportMstr;
	}

}
