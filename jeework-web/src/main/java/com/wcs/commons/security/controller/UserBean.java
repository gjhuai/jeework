package com.wcs.commons.security.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.wcs.base.exception.TransactionException;
import com.wcs.commons.security.model.User;
import com.wcs.commons.security.model.master.Person;
import com.wcs.commons.security.service.UserService;

/**
 * 
 * @author Chris Guan
 */
@ManagedBean
@ViewScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Object> filterMap = Maps.newHashMapWithExpectedSize(4); // 查询条件Map封装
    private LazyDataModel<User> lazyModel;
    private User instance = new User(); // 当前角色对象
    private List<Person> persons = new ArrayList<Person>();		// adAccount LIKE对应的person 列表，用于Add Page    
    
    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
    	logger.debug("init() - @PostConstruct");
        list();
    }

    public void list() {
        lazyModel = userService.findUsers(filterMap);
    }

    public void delete() {
        userService.deleteUser(instance);
        list();
    }
    
    /**
     * 清空instance，准备存储要添加的 User
     */
    public void toAdd(){
    	logger.debug("toAdd");
    	this.instance = new User();
    }
    
    /**
     * 按照 adAccount 查找Person对象，需要关联 CasUsr-->PU<--Person
     */
    public void findPersons(){
    	persons = userService.findPersons(this.instance.getAdAccount());
    }
    
    /**
     * 添加一个User实体，User实体的adAccount 来自Usr.id，pernr 来自Person.id
     */
    public void add(){
    	String pernr = this.instance.getPernr();  // Person->id
    	try{
    		userService.addUser(pernr);
    	} catch (TransactionException te){
    		logger.debug( te.getMessage() );
    		//MessageUtils.addMessage("", te.getMessage());
    	}
    }
    //--------------------------- setter & getter ----------------------------//
    
    public LazyDataModel<User> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<User> lazyModel) {
        this.lazyModel = lazyModel;
    }

	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	public User getInstance() {
		return instance;
	}

	public void setInstance(User instance) {
		this.instance = instance;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
}
