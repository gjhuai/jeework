/** * ReportQueryBean.java 
* Created on 2011-12-15 上午10:10:15 
*/

package com.wcs.report.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.wcs.base.dict.model.Dict;
import com.wcs.base.dict.service.DictService;
import com.wcs.base.util.JSFUtils;
import com.wcs.common.constant.IReportDictConst;
import com.wcs.common.constant.IReportDictDetailConst;
import com.wcs.common.controller.dict.DictBean;
import com.wcs.common.model.User;
import com.wcs.common.model.UserRole;
import com.wcs.common.service.permissions.RoleService;
import com.wcs.report.model.ReportFile;
import com.wcs.report.model.ReportMstr;
import com.wcs.report.model.ReportParameter;
import com.wcs.report.model.ReportRole;
import com.wcs.report.service.ReportFileService;
import com.wcs.report.service.ReportManageService;
import com.wcs.report.service.ReportParameterService;
import com.wcs.report.service.ReportRoleService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportQueryBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class ReportQueryBean extends ReportBase implements Serializable {
    @Inject
    ReportManageService reportManageService;
    @Inject
    ReportParameterService reportParameterService;
    @Inject
    ReportRoleService reportRoleService;
    @Inject
    ReportFileService reportFileService;
    @Inject
    DictService dictService;
    @Inject
    RoleService roleService;
    @Inject
    private DictBean dictBean;
    
    private TreeNode root; // 资源树
    private TreeNode selectedNode; // 选择的节点

    private Long reportMstrId; // 报表基本信息ID
    private Boolean previewIsDisable;   // 预览按钮是否可用
    List<ReportParameter> reportParameterList = null; // 报表参数列表
    Map<String, Object> reportMap = new HashMap<String, Object>(); // 报表参数map
    /** 报表导出类型*/
    private List<SelectItem> exportTypeList;
    public ReportQueryBean() {

    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void initConstruct() {
        this.initTreeNode();
        exportTypeList = new ArrayList<SelectItem>();
        exportTypeList.addAll(this.dictService.findWithSelectItem("EXRP"));
    }

    /**
     * 初始化树节点
     */
    private void initTreeNode() {
        root = new ReportTreeNode("Root", null);
        User user = (User) JSFUtils.getSession().get("user");
        //当前用户的角色列表
        if(user == null) {
        	return ;
        }
        List<UserRole> userRoleList = roleService.getUserRoleList(user.getId());
        List<Dict> categoryList = reportManageService.getReportCategory(IReportDictConst.RPTC);
        for (int i = 0; i < categoryList.size(); i++) {
            ReportTreeNode fnode = new ReportTreeNode(categoryList.get(i).getName(), root);
            // 加载子节点
            List<ReportMstr> reportList = reportManageService.getReportByCategory(categoryList.get(i).getCode());
            for (int j = 0; j < reportList.size(); j++) {
            	//报表所具有的角色列表
            	List<ReportRole> reportRoleList = reportRoleService.getReportRoleList(reportList.get(j).getId());
            	for (UserRole userRole : userRoleList) {
            		Boolean bl = false;
            		for(ReportRole reportRole : reportRoleList) {
            			if(userRole.getRole().getId().equals(reportRole.getRole().getId())) {
            				 bl = true;
            				 ReportTreeNode childNode = new ReportTreeNode(reportList.get(j).getReportName(), fnode);
            	             childNode.setReportMstrId(reportList.get(j).getId());
            	             break;
            			}
            		}
            		
            		if(bl) {
            			break;
            		}
            	}
            }
        }
    }

    /**
     * 树节点点击事件
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
    	ReportTreeNode treeNode = (ReportTreeNode) event.getTreeNode();
        Long rmId = treeNode.getReportMstrId();
        if (rmId == null) {
        	this.setPreviewIsDisable(false);
        	this.setReportParameterList(null);
        	super.setReportContent("");
        	return; 
        } else {
        	this.setPreviewIsDisable(true);
        }
        List<ReportParameter> parameterList = reportParameterService.findReportParameterList(rmId);       
        this.setReportMstrId(rmId);
        this.setReportParameterList(parameterList);
        //清空报表展示信息
        super.setReportContent("");
    }

    /**
     * 判断控件类型是否为文本框
     * @param uiType
     * @return
     */
    public boolean uiTypeText(String uiType) {
        if (IReportDictDetailConst.CONT_1.equals(uiType)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断控件类型是否为时间控件
     * @param uiType
     * @return
     */
    public boolean uiTypeCalendar(String uiType) {
        if (IReportDictDetailConst.CONT_2.equals(uiType)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断控件类型是否为下拉框
     * @param uiType
     * @return
     */
    public boolean uiTypeSelect(String uiType) {
    	 if (IReportDictDetailConst.CONT_3.equals(uiType)) {
             return true;
         } else {
             return false;
         }
    }
    
    /**
     * 判断控件类型是否为复选框
     * @param uiType
     * @return
     */
    public boolean uiTypeCheckBox(String uiType) {
   	 if (IReportDictDetailConst.CONT_4.equals(uiType)) {
            return true;
        } else {
            return false;
        }
   }
    
    /**
     * 判断java数据类型是否为整型
     * @param dataType
     * @return
     */
    public boolean isDataTypeNumber(String dataType) {
    	if (IReportDictDetailConst.JADT_3.equals(dataType)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 查询数据字典parentCode下的所有子项
     * @param hql
     * @return
     */
   public List<SelectItem> getSelectItem(String hql) {
	   if(hql == null || "".equals(hql)) {
		   return null;
	   }
	   
	   List<SelectItem> list = dictService.findSelectItemByHql(hql);
	   return list;
   }

    /**
     * 报表预览
     */
    public void preview() {
        /*
         * Map map1 = this.getReportMap(); Date date = (Date) map1.get("productdate"); map1.put("productdate", date);
         */
        ReportFile reportFile = reportFileService.getReportFile(this.getReportMstrId());
        if (reportFile == null) { return; }
        // String fileName = reportFile.getFileName();
        String fileStoreLocation = reportFile.getFileStoreLocation();
        if (fileStoreLocation == null || fileStoreLocation.equals("")) { return; }
        String jasper = fileStoreLocation.replace("jrxml", "jasper");
        String printFile = fileStoreLocation.replace("jrxml", "jrprint");
        super.setupPage(fileStoreLocation, jasper, printFile);
        try {
            super.genneralReport(this.getReportMap());
            JSFUtils.getRequest().setAttribute("exportFlag", 1);
        } catch (JRException e) {
            e.printStackTrace();
        }
        
        reportMap.clear();
    }

    /**
     * 
     * <p>Description: 导出报表</p>
     */
    public void exportReport() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                    .getResponse();
            String type =  findExportType();
            if(type != null ){
                String exportMethod = type.toLowerCase();
                if(exportMethod.contains("pdf")){
                    this.exportToPdf(response);
                }else if(exportMethod.contains("excel")){
                    this.exportToExcel(response);
                }else if(exportMethod.contains("html")){
                    this.exportToXHtml(response);
                }else if(exportMethod.contains("word")){
                    this.exportToDocx(response);
                }
                
            }
            
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String findExportType(){
        for(SelectItem it : exportTypeList){
            String param = JSFUtils.getRequestParam(String.valueOf(it.getValue()));
            if(param != null && param != "null"){
                return param;
            }
        }
        return null;
    }
    
    
    public void handExport(){
        exportReport();
    }

    // -------------------- setter & getter --------------------//

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<ReportParameter> getReportParameterList() {
        return reportParameterList;
    }

    public void setReportParameterList(List<ReportParameter> reportParameterList) {
        this.reportParameterList = reportParameterList;
    }

    public Map<String, Object> getReportMap() {
        return reportMap;
    }

    public void setReportMap(Map<String, Object> reportMap) {
        this.reportMap = reportMap;
    }

    public Long getReportMstrId() {
        return reportMstrId;
    }

    public void setReportMstrId(Long reportMstrId) {
        this.reportMstrId = reportMstrId;
    }

    public List<SelectItem> getExportTypeList() {
        return exportTypeList;
    }

    public void setExportTypeList(List<SelectItem> exportTypeList) {
        this.exportTypeList = exportTypeList;
    }

	public Boolean getPreviewIsDisable() {
		return previewIsDisable;
	}

	public void setPreviewIsDisable(Boolean previewIsDisable) {
		this.previewIsDisable = previewIsDisable;
	}

}
