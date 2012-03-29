/** * ReportManageBean.java 
* Created on 2011-12-6 下午1:46:09 
*/

package com.wcs.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

import com.wcs.base.controller.ViewBaseBean;
import com.wcs.base.dict.service.DictService;
import com.wcs.base.util.JSFUtils;
import com.wcs.common.constant.IReportDictConst;
import com.wcs.common.model.Role;
import com.wcs.report.model.ReportMstr;
import com.wcs.report.model.ReportParameter;
import com.wcs.report.model.ReportRole;
import com.wcs.report.service.ReportManageService;
import com.wcs.report.service.ReportParameterService;
import com.wcs.report.service.ReportRoleService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportManageBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class ReportManageBean extends ViewBaseBean<ReportMstr> {
	@Inject
	ReportManageService reportManageService;
	@Inject
	private DictService dictService;
	@Inject
	private ReportRoleService reportRoleService;
	@Inject
	private ReportParameterService reportParameterService;
	private Map<String, Object> filterMap = new HashMap<String, Object>();
	private LazyDataModel<ReportMstr> lazyModel;               // 动态分页使用
	
	private DualListModel<Role> dualModel;                     // 配置角色	
	private List<Role> rolesSource = new ArrayList<Role>();
	private List<Role> rolesTarget = new ArrayList<Role>();
	private String reportRoles;
	
	private List<SelectItem> reportCategoryList = new ArrayList<SelectItem>();        // 报表分类下拉框
	private List<SelectItem> reportModeList = new ArrayList<SelectItem>();            // 报表模式下拉框
	
	private List<ReportParameter> parameterTabList = new ArrayList<ReportParameter>();         // tab的参数列表
	
	 /**
     * 构造函数
     */
    public ReportManageBean() {
    	this.listPage = "/faces/report/reportmanage/list.xhtml";
		this.inputPage = "/faces/report/reportmanage/input.xhtml";
    }
    
    /**
	 * 构造方法执行后会自动执行此方法
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void postConstruct() {
    	this.search();	
    	this.initSelectItem();
    	
    	rolesSource = reportManageService.findAllRoles();
    	dualModel = new DualListModel<Role>(rolesSource, rolesTarget);
	}
	
	/**
	 * 查询报表列表
     */
    public void search() {
    	lazyModel = reportManageService.findModelByMap(filterMap);
    }
    
    /**
     * 初始化报表分类、报表模式下拉框
     */
    public void initSelectItem() {
    	this.setReportCategoryList(dictService.findWithSelectItem(IReportDictConst.RPTC));
    	this.setReportModeList(dictService.findWithSelectItem(IReportDictConst.RPTM));
    }
    
    /**
     * 保存报表信息
     */
    public String saveReportInfo() {
    	 super.saveEntity();
    	 //向报表角色表插入数据
         this.saveReportRole();
    	
    	 return listPage ==null?"/faces/debug/failed.xhtml": listPage;
    }
    
    /**
     * 保存报表角色信息
     */
    public void saveReportRole() {
		ReportMstr reportMstr = super.getInstance();
		List<ReportRole> reportRoleList = reportRoleService.findRoleList(reportMstr.getId());
		int reportRoleLeng = reportRoleList.size();
		List targetList = dualModel.getTarget();
		int targetLeng = targetList.size();
		// 当前报表在报表角色中没有角色时
		if (reportRoleList == null || reportRoleLeng == 0) {
			for (int i = 0; i < targetLeng; i++) {
				Role role = reportManageService.findRole(Long.valueOf(targetList.get(i).toString()));
				ReportRole reportRole = new ReportRole();

				reportRole.setReportMstr(reportMstr);
				reportRole.setRole(role);
				reportRole.setCreatedBy((String)JSFUtils.getSession().get("loginName"));
				entityService.create(reportRole);
			}
		}
		// 当没有配置角色时
		else if (targetList == null || targetLeng == 0) {
			for (int i = 0; i < reportRoleLeng; i++) {
				ReportRole reportRole = reportRoleList.get(i);
				reportRole.setDefunctInd(true);
				reportRole.setUpdatedBy((String)JSFUtils.getSession().get("loginName"));
				entityService.update(reportRole);
			}
		} else {
			for (int i = 0; i < reportRoleLeng; i++) {
				ReportRole reportRole = reportRoleList.get(i);
				reportRole.setDefunctInd(true);
				reportRole.setUpdatedBy((String)JSFUtils.getSession().get("loginName"));
				entityService.update(reportRole);
			}

			for (int i = 0; i < targetLeng; i++) {
				boolean create = true;
				Role role = reportManageService.findRole(Long.valueOf(targetList.get(i).toString()));
				for (int j = 0; j < reportRoleLeng; j++) {
					if (role.getId().equals(reportRoleList.get(j).getRole().getId())) {
						ReportRole reportRole = reportRoleList.get(j);
						reportRole.setDefunctInd(false);
						reportRole.setUpdatedBy((String)JSFUtils.getSession().get("loginName"));
						entityService.update(reportRole);
						create = false;
						break;
					}
				}
				if (create) {
					ReportRole reportRole = new ReportRole();

					reportRole.setReportMstr(reportMstr);
					reportRole.setRole(role);
					reportRole.setCreatedBy((String)JSFUtils.getSession().get("loginName"));
					entityService.create(reportRole);
				}
			}
		}
    }
    
    /**
     * 角色配置
     */
    public void roleConfig() {
    	List listId = dualModel.getTarget();
    	if(listId == null || listId.size() == 0) {
    		reportRoles = "";
    	} else {
    		StringBuffer sb = new StringBuffer();
    		for(int i = 0; i < listId.size(); i++) {
    			Role role = reportManageService.findRole(Long.valueOf(listId.get(i).toString()));
    			String name = role.getRoleName();
    			sb.append(name + ";");
    		}
    		String str = sb.toString();
    		reportRoles = str.substring(0, str.length()-1);
    	}
    	//this.setReportRoles(reportRoles);
    	this.getInstance().setReportConfig(reportRoles);
    }
    
    /**
	 * 当报表列表行选中，然后调用此单击方法
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) { 
		ReportMstr reportMstr = (ReportMstr)event.getObject();
		Long reportMstrId = reportMstr.getId();
		if(reportMstrId == null) {
			return ;
		}
		this.getParameterTabList(reportMstrId);	
		JSFUtils.getRequest().setAttribute("rowSelect", reportMstrId);
		JSFUtils.getRequest().setAttribute("enable", 1);
	}
    
    /**
     * 查询报表参数列表
     * @param reportMstrId
     */
    public void getParameterTabList(Long reportMstrId) {
    	this.parameterTabList = reportParameterService.findReportParameterList(reportMstrId);
    }
    
	
	//-------------------- setter & getter --------------------//

	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	public LazyDataModel<ReportMstr> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<ReportMstr> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public String getReportRoles() {
		return reportRoles;
	}

	public void setReportRoles(String reportRoles) {
		this.reportRoles = reportRoles;
	}

	public DualListModel<Role> getDualModel() {
		return dualModel;
	}

	public void setDualModel(DualListModel<Role> dualModel) {
		this.dualModel = dualModel;
	}

	public List<SelectItem> getReportCategoryList() {
		return reportCategoryList;
	}

	public void setReportCategoryList(List<SelectItem> reportCategoryList) {
		this.reportCategoryList = reportCategoryList;
	}

	public List<SelectItem> getReportModeList() {
		return reportModeList;
	}

	public void setReportModeList(List<SelectItem> reportModeList) {
		this.reportModeList = reportModeList;
	}

	public List<ReportParameter> getParameterTabList() {
		return parameterTabList;
	}

	public void setParameterTabList(List<ReportParameter> parameterTabList) {
		this.parameterTabList = parameterTabList;
	}

   
	
}
