package com.wcs.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.wcs.base.controller.ViewBaseBean;
import com.wcs.demo.model.Person;
import com.wcs.demo.service.PersonService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: PersonBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class PersonBean extends ViewBaseBean<Person> {

	@EJB
	PersonService personService;
	
	private Map<String, Object> filterMap = Maps.newHashMapWithExpectedSize(4);
	private LazyDataModel<Person> lazyModel;           // 动态分页使用
	private List<Person> personList = new ArrayList<Person>();
	private boolean editMode;        					// 是否修改
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 构造函数
	 */
	public PersonBean() {
		this.listPage = "/faces/person/list.xhtml";
		this.inputPage = "/faces/person/input.xhtml";
	}
	
	/**
	 * 构造方法执行后会自动执行此方法
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void postConstruct() {
    	this.search();	
	}
	
	/**
	 * 查找person
     */
    public void search() {
    	lazyModel = personService.findModelByMap(filterMap);
    }
    
    /**
     * 跳转到输入页面
     */
    public String input() {
    	return this.inputPage;
    }

    //-------------------- setter & getter --------------------//
    
	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	public LazyDataModel<Person> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Person> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
