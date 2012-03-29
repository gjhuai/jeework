/** * ReportTreeNode.java 
* Created on 2011-12-15 下午2:38:07 
*/

package com.wcs.report.controller;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportTreeNode.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@SuppressWarnings("serial")
public class ReportTreeNode extends DefaultTreeNode{
	private Long reportMstrId;       // 报表id
	private String reportCategory;   // 报表类型
	
	public ReportTreeNode() {
		super();
	}
	
	public ReportTreeNode(String name, TreeNode parent) {
		super(name, parent);
	}
	
	public String getReportCategory() {
		return reportCategory;
	}
	
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	public Long getReportMstrId() {
		return reportMstrId;
	}

	public void setReportMstrId(Long reportMstrId) {
		this.reportMstrId = reportMstrId;
	}
	
}
