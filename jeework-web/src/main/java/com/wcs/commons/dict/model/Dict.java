package com.wcs.commons.dict.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.wcs.base.entity.IdEntity;

/**
 * 系统代码表
 * 1. 通过 category, key 能获得想要的代码值
 * 2. 通过 category 装载某一类代码值，eg. 下拉列表需要的键值对
 * 
 * @author Chris Guan
 *
 */
@Entity
@Table(name = "dict")
public class Dict extends IdEntity {

    @Column(nullable = false, length = 30)
    private String category;   //代码分类。    [“系统分类”]. [“系统名称”]. [“字典数据类型”]. [”CAT编码”] ， 原则：无分类不加


    @Column(name="code_key",nullable = false, length = 30)
    private String key;    //代码键值，eg. 1

    @Column(length = 30)
    private String value;   //代码值, eg. 男性
    
    @Column(nullable=false, length=5)
	private String lang;   //语言标示, eg. zh_CN

	@Column(length=200)
	private String remarks;

	@Column(name="SEQ_NO")
	private long seqNo;    // 排序No.

	@Column(name="SYS_IND", nullable=false, length=1)
	private Character sysInd;    // N:用户不允许修正-默认; Y:用户允许修正

    @Column(name = "defunct_ind")
	private Character defunctInd = 'N';   // Y表示失效/逻辑删除，N表示有效

    public Dict() {}

    public Dict(String category, String key) {
        this.category = category;
        this.key = key;
    }

    @PrePersist
    public void init() {
        defunctInd = 'N';
        sysInd = 'N';
    }

    public Dict(String category, String key, String value) {
        this.category = category;
        this.key = key;
        this.value = value;
    }
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public Character getSysInd() {
		return sysInd;
	}

	public void setSysInd(Character sysInd) {
		this.sysInd = sysInd;
	}

	public Character isDefunctInd() {
        return defunctInd;
    }

    public void setDefunctInd(Character defunctInd) {
        this.defunctInd = defunctInd;
    }

}
