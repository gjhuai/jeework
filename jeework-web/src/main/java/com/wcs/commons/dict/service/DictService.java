package com.wcs.commons.dict.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.wcs.base.service.EntityReader;
import com.wcs.commons.dict.model.Dict;

/**
 * 系统代码表
 * 1. 通过 category, key 能获得想要的代码值
 * 2. 通过 category 装载某一类代码值，eg. 下拉列表需要的键值对
 * 
 * @author Chris Guan
 *
 */
@Startup
@Singleton
public class DictService {

	private List<Dict> dicts = null;	// 所有的Dict item
	
	@EJB(beanName="EntityReader")
	EntityReader entityReader;
	
	@PostConstruct
	public void init() {
		this.dicts = this.findAllDicts();	// 从DB装载所有的 Dict item
	}
	
	private List<Dict> findAllDicts(){
		return entityReader.findList("SELECT d FROM Dict d");
	}
	

	public List<Dict> loadAllDicts() {
		return dicts;
	}

	public List<Dict> loadDicts(String category, String lang){
		List<Dict> list = new ArrayList<Dict>();
		for (Dict d : list){
			if ( d.getCategory().equals(category) && d.getLang().equals(lang)){
				list.add(d);
			}
		}
		return list;
	}
	
	public Dict loadDict(String category, String lang, String key){
		List<Dict> list = this.loadDicts(category,lang);
		for (Dict d : list){
			if ( d.getKey().equals(key)){
				return d;
			}
		}
		return null;
	}

}
