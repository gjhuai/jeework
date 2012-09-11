package com.wcs.showcase.crud.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.LazyDataModel;

import com.wcs.base.service.PagingEntityReader;
import com.wcs.showcase.crud.model.Teacher;

/** 
* <p>Project: btcbase</p> 
* <p>Title: </p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class TeacherService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PagingEntityReader entityReader;

    /**
     * 查询所有的人员信息
     */
    public List<Teacher> search() {
    	List<Teacher> list = entityReader.findList("SELECT t FROM Teacher t");
    	
		return  list;	
	}	

    /**
     * 动态分页， XSQL 查询 （推荐使用）
     * @param filterMap
     */
	public LazyDataModel<Teacher> findModelByMap(Map<String, Object> filterMap) {
		String hql = "select p from Teacher p where p.defunctInd ='N'";
		StringBuilder xql =  new StringBuilder(hql);
	    xql.append(" /~ and p.name like {name} ~/ ")
	        .append(" /~ and p.sex like {sex} ~/ ")
	        .append(" /~ and p.birthday >= {startBirthday} ~/")
	        .append(" /~ and p.birthday <= {endBirthday} ~/");
	    return entityReader.findXqlPage(xql.toString(), filterMap);
	}
}
