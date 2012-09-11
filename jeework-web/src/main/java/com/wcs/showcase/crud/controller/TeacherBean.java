package com.wcs.showcase.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.wcs.base.controller.ViewBaseBean;
import com.wcs.commons.dict.controller.DictBean;
import com.wcs.showcase.crud.model.Teacher;
import com.wcs.showcase.crud.service.TeacherService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: </p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class TeacherBean extends ViewBaseBean<Teacher> {

	@Inject
	private TeacherService teacherService;
	@Inject
	private DictBean dictBean;
	
	private Map<String, Object> filterMap = Maps.newHashMapWithExpectedSize(4);
	private LazyDataModel<Teacher> lazyModel;           // 动态分页使用
	private List<Teacher> teacherList = new ArrayList<Teacher>();
	private boolean editMode;        					// 是否修改
	private List<SelectItem> sexList = new ArrayList<SelectItem>();        // 性别下拉框
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 构造函数
	 */
	public TeacherBean() {
		this.listPage = "/faces/teacher/list.xhtml";
		this.inputPage = "/faces/teacher/input.xhtml";
	}
	
	/**
	 * 构造方法执行后会自动执行此方法
	 */
	@PostConstruct
	private void postConstruct() {
		this.setSexList(dictBean.getItems("SEX"));
    	this.search();	
	}
	
	/**
	 * 查找
     */
    public void search() {
    	lazyModel = teacherService.findModelByMap(filterMap);
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

	public LazyDataModel<Teacher> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Teacher> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<SelectItem> getSexList() {
		return sexList;
	}

	public void setSexList(List<SelectItem> sexList) {
		this.sexList = sexList;
	}

}
