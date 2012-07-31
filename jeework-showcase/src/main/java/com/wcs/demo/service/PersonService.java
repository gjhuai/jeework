package com.wcs.demo.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.wcs.base.service.StatelessEntityService;
import org.primefaces.model.LazyDataModel;

import com.wcs.demo.model.Person;

/** 
* <p>Project: btcbase</p> 
* <p>Title: PersonService.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class PersonService {
	@Inject
	public StatelessEntityService entityService;

    /**
     * 查询所有的人员信息
     * @return List<Person>
     */
    public List<Person> search() {
    	List<Person> list = entityService.findAll(Person.class);
    	
		return  list;	
	}	

    /**
     * 动态分页， XSQL 查询 （推荐使用）
     * @param filterMap
     * @return LazyDataModel<Person>
     */
	public LazyDataModel<Person> findModelByMap(Map<String, Object> filterMap) {
		String hql = "select p from Person p where p.defunctInd = false";
		StringBuilder xsql =  new StringBuilder(hql);
	    xsql.append(" /~ and p.name like {name} ~/ ")
	        .append(" /~ and p.sex like {sex} ~/ ")
	        .append(" /~ and p.birthday >= {startBirthday} ~/")
	        .append(" /~ and p.birthday <= {endBirthday} ~/");
	    return entityService.findXsqlPage(xsql.toString(), filterMap);
	}

	//-------------------- setter & getter --------------------//
	
	public void setEntityService(StatelessEntityService entityService) {
		this.entityService = entityService;
	}
	
}
