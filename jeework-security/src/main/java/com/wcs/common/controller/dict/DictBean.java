package com.wcs.common.controller.dict;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.wcs.base.dict.model.Dict;
import com.wcs.base.service.StatelessEntityService;

/**
 * 
 * <p>Project: btcbase</p>
 * <p>Description: 数据字典封装Bean</p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">Chen Long</a>
 */
@Named
@ApplicationScoped
public class DictBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private StatelessEntityService entityService;
    /** 代码缓存Map*/
    private  ConcurrentMap<String, String> codeCaseMap;

    public DictBean() {

    }
    
    @PostConstruct
    public void initDictCode() {
        String sql = "select dict from Dict dict ";
        codeCaseMap = new MapMaker().concurrencyLevel(4).softKeys().weakValues().maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS).makeComputingMap(new Function<String, String>() {
                    @Override
                    public String apply(String codeNumber) {
                        String name = findByCode(codeNumber).getName();
                        return name != null?name:"";
                    }
                });
        List<Dict> dictList = this.entityService.findList(sql);
        this.addElement(dictList);
    }

    /**
     * 
     * <p>Description: 将数据放入到Map</p>
     * @param dictList
     */
    public void addElement(List<Dict> dictList) {
        if (dictList != null && !dictList.isEmpty()) {
            for (Dict dict : dictList) {
                codeCaseMap.put(dict.getCode(), dict.getName());
            }
        }
    }
    
    
    public String queryCode(String code){
        String str = codeCaseMap.get(code);
        System.out.println(str);
        return codeCaseMap.get(code);
    }
    
    /**
     * 
     * <p>Description: 根据Code查询DICT</p>
     * @param number
     * @return
     */
    public Dict findByCode(String number) {
        String sql = "select dict from Dict dict where dict.code=? ";
        List list = this.entityService.findList(sql, number);
        if (!list.isEmpty() && list.size() == 1) { 
            return (Dict) list.get(0); 
        }else{
            String sql1 = "select dict from Dict dict where  dict.parentCode = ?";
            List list1 = this.entityService.findList(sql, number);
            if(!list1.isEmpty() && list1.size() == 1){
                return (Dict) list1.get(0); 
            }else{
                return new Dict();
            }
           
        }
    }

    // -------------------------------------------------- Get & Set--------------------//
    public ConcurrentMap<String, String> getCodeCaseMap() {
        return codeCaseMap;
    }

    public void setCodeCaseMap(ConcurrentMap<String, String> codeCaseMap) {
        this.codeCaseMap = codeCaseMap;
    }

}
