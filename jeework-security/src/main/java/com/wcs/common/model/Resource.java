package com.wcs.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: Resource.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "resource")
public class Resource extends BaseEntity {
    private Long parentId; // 父菜单Id
    private String name; // 资源名称
    private String url; // 资源URL
    private Boolean ismenu; // 是否是菜单
    private String number; // 资源编号
    private Boolean isLeaf;
    private Integer level;  
    private String keyName; // 权限关键值

    private String parentName; // 父菜单名称
    private String isMenuLang;
    private String isLeafLang;

    public Resource() {
    }

    public Resource(String level, String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Column(name = "NAME", length = 40)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "URL", length = 100)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ISMENU", columnDefinition = "smallint")
    public Boolean getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(Boolean ismenu) {
        this.ismenu = ismenu;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "Number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "IS_LEAF", columnDefinition = "smallint")
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
    @Column(name = "LEVEL", nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "KEY_NAME", nullable = false)
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Transient
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    @Transient
    public String getIsMenuLang() {
        return isMenuLang;
    }

    public void setIsMenuLang(String isMenuLang) {
        this.isMenuLang = isMenuLang;
    }

    @Transient
    public String getIsLeafLang() {
        return isLeafLang;
    }

    public void setIsLeafLang(String isLeafLang) {
        this.isLeafLang = isLeafLang;
    }

    @Override
    @Transient
    public String getDisplayText() {
        return null;
    }
}
