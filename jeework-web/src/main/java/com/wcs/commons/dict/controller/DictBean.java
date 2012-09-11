package com.wcs.commons.dict.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.wcs.commons.dict.model.Dict;
import com.wcs.commons.dict.service.DictService;

/**
 * 系统代码表
 * 1. 通过 category, key 能获得想要的代码值
 * 2. 通过 category 装载某一类代码值，eg. 下拉列表需要的键值对
 * 
 * 对常用的category下的dict-item实现缓存
 * @author Chris Guan
 */

@ManagedBean
@ApplicationScoped
public class DictBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final long MAX_SIZE = 100;
    private final LoadingCache<String, List<Dict>> cache;   //category缓存

    @EJB
    DictService dictService;
    
    public DictBean(){
        cache = CacheBuilder.newBuilder().maximumSize( MAX_SIZE ).expireAfterAccess(15, TimeUnit.HOURS).build( new CacheLoader<String, List<Dict>>() {
            @Override
            public List<Dict> load( String category ) throws Exception {
              return findDicts(category);
            }
          }
        );
    }
    
    private List<Dict> findDicts(String category){
    	Locale browserLang=FacesContext.getCurrentInstance().getViewRoot().getLocale();
    	return dictService.loadDicts(category, browserLang.toString());
    }
    
    /**
     * 通过 category 来获取对应的  List<dict> 集合,常用于下拉框.
     */
	public List<Dict> getDicts(String category) {
		return cache.getUnchecked( category );
	}
	
	/**
     * 
     * 通过 category 来获取对应的  List<SelectItem> 集合,用于下拉框.
     */
    public List<SelectItem> getItems(String category) {
    	List<Dict> dicts = this.getDicts(category);		//从缓存中获取

    	List<SelectItem> items = Lists.newArrayList();
        for (Dict d : dicts) {
            items.add(new SelectItem(d.getKey(), d.getValue()));
        }
        return items;
    }

    /**
     * 通过 category，key 来获取相应的dict
     */
    public Dict getDict(String category,String key) {
		List<Dict> list = this.getDicts(category);
		for (Dict d : list){
			if ( d.getKey().equals(key)){
				return d;
			}
		}
		return null;
    }
    
    /**
     * 通过 category，key 来获取相应的dict 的 value
     */
    public String getDictValue(String category,String key) {
        return this.getDict(category, key).getValue();
    }
}
