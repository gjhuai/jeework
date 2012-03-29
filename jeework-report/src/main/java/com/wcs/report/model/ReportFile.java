/** * ReportFile.java 
* Created on 2011-12-6 下午3:29:37 
*/

package com.wcs.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;
import com.wcs.base.entity.IdEntity;

/**
 * 
 * <p>Project: btcbase</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">Chen Long</a>
 */
@Entity
@Table(name = "REPORTFILE")
@SuppressWarnings("serial")
public class ReportFile extends BaseEntity {
    /** 文件名称*/
    private String fileName;
    /** 文件存储地址*/
    private String fileStoreLocation;
    /** 上传文件人*/
    private String upLoadedBy;
    /** 上传时间*/
    private Date upLoadedDatetime;
    /** 备注*/
    private String remarks;
    /** 版本号*/
    private int versionNo;
    /** 是否使用*/
    private Boolean useInd;

    private ReportMstr reportMstr;

    public ReportFile() {

    }

    @Column(name = "FILE_NAME", nullable = false, length = 200)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "FILE_STORE_LOCATION", nullable = false, length = 400)
    public String getFileStoreLocation() {
        return fileStoreLocation;
    }

    public void setFileStoreLocation(String fileStoreLocation) {
        this.fileStoreLocation = fileStoreLocation;
    }

    @Column(name = "UPLOADED_BY", nullable = false, length = 30)
    public String getUpLoadedBy() {
        return upLoadedBy;
    }

    public void setUpLoadedBy(String upLoadedBy) {
        this.upLoadedBy = upLoadedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPLOADED_DATETIME", nullable = false)
    public Date getUpLoadedDatetime() {
        return upLoadedDatetime;
    }

    public void setUpLoadedDatetime(Date upLoadedDatetime) {
        this.upLoadedDatetime = upLoadedDatetime;
    }

 

    @Column(name = "REMARKS", length = 600)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "VERSION_NO", length = 3)
    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    @Column(name = "USE_IND", columnDefinition = "smallint")
    public Boolean getUseInd() {
        return useInd;
    }

    public void setUseInd(Boolean useInd) {
        this.useInd = useInd;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTMSTR_ID")
    public ReportMstr getReportMstr() {
        return reportMstr;
    }

    public void setReportMstr(ReportMstr reportMstr) {
        this.reportMstr = reportMstr;
    }
    
    @Transient
    @Override
    public String getDisplayText() {
        
        return null;
    }

}
