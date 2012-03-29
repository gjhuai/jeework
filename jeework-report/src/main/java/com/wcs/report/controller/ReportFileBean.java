/** * ReportFileBean.java 
* Created on 2011-12-6 下午1:47:38 
*/

package com.wcs.report.controller;

import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.wcs.base.controller.ViewBaseBean;
import com.wcs.base.exception.ServiceException;
import com.wcs.base.util.JSFUtils;
import com.wcs.base.util.MessageUtils;
import com.wcs.report.model.ReportFile;
import com.wcs.report.model.ReportMstr;
import com.wcs.report.service.ReportFileService;
import com.wcs.report.util.FileUtil;

/**
 * 
 * <p>Project: btcbase</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">Chen Long</a>
 */
@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class ReportFileBean extends ViewBaseBean<ReportFile> {
    private LazyDataModel<ReportFile> reportFileModel;
    /** 报表主数据Id*/
    private Long mastrId;
    /** 上传模板文件对象*/
    private UploadedFile rptFile;
    private Integer version;
    @Inject
    private ReportFileService reportFileService;

    /**
     * 构造函数
     */
    public ReportFileBean() {

    }

    /**
     * 
     * <p>Description: 对象构造之后调用此方法</p>
     */
    @SuppressWarnings("unused")
    @PostConstruct
    private void postConstruct() {
        File file = new File(FileUtil.getProjectAbsolute());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 
     * <p>Description: 上传模板之前,生产版本号取用户信息</p>
     */
    public void befforeAdd() {
        this.setInstance(new ReportFile());
        getInstance().setUpLoadedBy((String) JSFUtils.getSession().get("userName"));
        if (mastrId != null) {
            version = reportFileService.findReptFileNumber(mastrId) + 1;
        }

    }

    public void befforeTabAdd() {
        this.setInstance(new ReportFile());
        getInstance().setUpLoadedBy((String) JSFUtils.getSession().get("userName"));
        String str = JSFUtils.getRequestParam("master");
        if (str != null && !"null".equals(str)) {
            mastrId = Long.parseLong(str);
            version = reportFileService.findReptFileNumber(mastrId) + 1;
        }
    }

    /**
     * 
     * <p>Description: </p>
     * @return
     */
    public String saveRptFile() {
        String viewId = JSFUtils.getViewId();
        try {
            int validtorReult = validatorFile();
            if (validtorReult == 1) { 
                JSFUtils.getRequest().setAttribute("rptFileMessage", 1);
                return viewId;
            }
            File file = fillRptFile();
            // saveRptFile 做了 上传的模板设置了为使用 其他模板属性更新为不使用
            reportFileService.saveRptFile(getInstance(), rptFile.getInputstream(), file);
            // 上传的模板设置了为使用 其他模板属性更新为不使用
            /*
             * if(this.getInstance().getUseInd()){ this.reportFileService.updateUseInd(getInstance().getId()); }
             */
            JSFUtils.getRequest().setAttribute("rptManagerFlag", 1);
            findRptTableData(this.mastrId);
        } catch (ServiceException e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("rptFileId", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/faces/report/reportmanage/list.xhtml";
    }

    /**
     * 
     * <p>Description: 是否使用模板</p>
     */
    public void update() {
        try {
            this.reportFileService.updateRptFile(getInstance(), this.mastrId);
            // 上传的模板设置了为使用 其他模板属性更新为不使用
            /*
             * if (this.getInstance().getUseInd()) { this.reportFileService.updateUseInd(getInstance().getId(),this.mastrId); }
             */
            JSFUtils.getRequest().setAttribute("rptManagerFlag", 1);
            findRptTableData(this.mastrId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return "/faces/report/reportmanage/list.xhtml";
    }

    public void findRptTableData(Long masId) {
        String sql = "select rpf from ReportFile rpf where rpf.reportMstr.id=? and rpf.defunctInd=false";
        reportFileModel = this.entityService.findPage(sql, masId);
        // reportFileModel = this.reportFileService.findRptFileDataModel(masId);
        // this.setReportFileModel(reportFileModel);
    }

    public void onTabChange(TabChangeEvent event) {
        Object id = JSFUtils.getRequestParam("masId");
        System.out.println(JSFUtils.getRequestParam("masId"));
        String title = event.getTab().getTitle();
        if ("报表文件".equals(title) && id != null && !"null".equals(id)) {
            reportFileModel = this.reportFileService.findRptFileDataModel(Long.parseLong((String) id));
        }
    }

    private int validatorFile() throws Exception {
        if (rptFile == null || rptFile.getSize() == 0) {
            JSFUtils.getRequest().setAttribute("rptflag", 1);
            MessageUtils.addErrorMessage("rptFileId", "请选择上传文件");
            return 1;
        }
        String str = rptFile.getFileName();
        str = str.substring(str.indexOf(".") + 1);
        boolean flag = reportFileService.isUpload(str, rptFile.getSize());
        if (!flag) {
            JSFUtils.getRequest().setAttribute("rptflag", 1);
            MessageUtils.addErrorMessage("rptFileId", "文件大小或者文件类型不符合标准");
            return 1;
        }
        return 2;
    }

    /**
     * 
     * <p>Description:填充报表文件对象 </p>
     * @return
     */
    private File fillRptFile() {
        ReportMstr rptMstr = reportFileService.findRepMstrById(mastrId);
        String rptCode = rptMstr.getReportCode().toLowerCase();
        String fileName = rptCode.concat("_").concat(String.valueOf(version)).concat("_").concat(rptFile.getFileName());
        File file = createRptFile(rptCode, fileName);
        getInstance().setFileName(fileName);
        getInstance().setFileStoreLocation(FileUtil.FLODER_NAME + File.separator + rptCode + File.separator + fileName);
        getInstance().setReportMstr(rptMstr);
        getInstance().setVersionNo(version);
        getInstance().setUpLoadedDatetime(new Date());
        return file;
    }

    /**
     * 
     * <p>Description: 创建报表文件路径</p>
     * @param rptCode
     * @param fileName
     * @return
     */
    private File createRptFile(String rptCode, String fileName) {
        try {
            String path = FileUtil.getProjectAbsolute();
            path = path.concat(File.separator).concat(rptCode.toLowerCase());
            File file = new File(path);
            boolean flag = false;
            if (!file.exists()) {
                flag = file.mkdir();
            } else {
                flag = true;
            }
            if (flag) {
                path = path.concat(File.separator).concat(fileName);
                file = new File(path);
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                } else {
                    file.createNewFile();
                }
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // -------------------- setter & getter --------------------//

    public Long getMastrId() {
        return mastrId;
    }

    public void setMastrId(Long mastrId) {
        this.mastrId = mastrId;

    }

    public UploadedFile getRptFile() {
        return rptFile;
    }

    public void setRptFile(UploadedFile rptFile) {
        this.rptFile = rptFile;
    }

    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LazyDataModel<ReportFile> getReportFileModel() {
        return reportFileModel;
    }

    public void setReportFileModel(LazyDataModel<ReportFile> reportFileModel) {
        this.reportFileModel = reportFileModel;
    }

}
