/** * ProductBean.java 
* Created on 2011-11-10 下午3:53:35 
*/

package com.wcs.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.google.common.collect.Maps;
import com.wcs.base.controller.ConversationBaseBean;
import com.wcs.base.dict.service.DictService;
import com.wcs.demo.model.Product;
import com.wcs.demo.service.ProductService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ProductBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Named
@ConversationScoped
@SuppressWarnings("serial")
public class ProductBean extends ConversationBaseBean<Product> {
	@Inject
	ProductService productService;
	@Inject
	private DictService dictService;
	
	private Map<String,Object> filterMap = Maps.newHashMapWithExpectedSize(5);
    private LazyDataModel<Product> lazyModel;
    private List<SelectItem> productCategoryList = new ArrayList<SelectItem>();        // 商品分类下拉框
    
    /**
     * 构造函数
     */
    public ProductBean() {
    	this.listPage = "/faces/product/list.xhtml";
		this.inputPage = "/faces/product/input.xhtml";
    }
    
    /**
	 * 构造方法执行后会自动执行此方法
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void postConstruct() {
		this.setProductCategoryList(dictService.findWithSelectItem("PROT"));
    	this.search();	
	}
	
	/**
	 * 查询商品
     */
    public void search() {
    	lazyModel = productService.findModelByMap(filterMap);
    }
    
    //-------------------- setter & getter --------------------//
    
	public Map<String, Object> getFilterMap() {
		return filterMap;
	}
	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}
	public LazyDataModel<Product> getLazyModel() {
		return lazyModel;
	}
	public void setLazyModel(LazyDataModel<Product> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public List<SelectItem> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<SelectItem> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

}
