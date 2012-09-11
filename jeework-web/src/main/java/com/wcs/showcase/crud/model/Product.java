/** * Product.java 
* Created on 2011-11-10 下午2:31:41 
*/

package com.wcs.showcase.crud.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wcs.base.entity.BaseEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: Product.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Entity
@Table(name = "PRODUCT")
@SuppressWarnings("serial")
public class Product extends BaseEntity {
	
	@Column(name = "NAME")
	private String name;             // 商品名称

	@Column(name = "CODE")
	private String code;             // 商品编码

	@Column(name = "CATEGORY")
	private String category;         // 商品类别

	@Column(name = "PRICE")
	private Double price;            // 商品价格

	@Column(name = "AVAILABLE")
	private Boolean available;       // 是否还有

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRODUCTION_DATE")	
	private Date productingDate;     // 生产日期

	@Column(length = 1000)
	private String description;      // 商品描述

	@Override
	@Transient
	public String getDisplayText() {
		return this.getName();
	}
	
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}


	public Date getProductingDate() {
		return productingDate;
	}

	public void setProductingDate(Date productingDate) {
		this.productingDate = productingDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
