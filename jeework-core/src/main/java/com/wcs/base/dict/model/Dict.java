package com.wcs.base.dict.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "dict")
public class Dict extends IdEntity {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    /** 代码编号*/
    private String code;
    /** 代码名称*/
    private String name;
    /** 代码值*/
    private String value;
    /** 父级代码*/
    private String parentCode;
    private Boolean defunctInd;

    public Dict() {
    }

    public Dict(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Dict(String code, String name, String value, String parentCode) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.parentCode = parentCode;
    }

    @Transient
    public String getDisplayText() {
        return null;
    }

    @Column(name = "CODE", nullable = false, length = 30)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", nullable = false, length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "VALUE", length = 30)
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "PARENT_CODE", length = 30)
    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Column(name = "DEFUNCT_IND", columnDefinition = "smallint")
    public Boolean isDefunctInd() {
        return defunctInd;
    }

    public void setDefunctInd(Boolean defunctInd) {
        this.defunctInd = defunctInd;
    }

    @PrePersist
    public void init() {
        defunctInd = false;
    }
}
