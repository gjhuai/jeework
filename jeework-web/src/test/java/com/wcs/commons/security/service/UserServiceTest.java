package com.wcs.commons.security.service;

import static junit.framework.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.junit.Test;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.wcs.base.test.BaseTest;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.User;

/**
 * 
 * @author Chris Guan
 */
public class UserServiceTest extends BaseTest{
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@EJB
	private UserService userService;
	
	@Test
	public void test_findUser(){
		User user = userService.findUser(1L);
	}
	
	@Test
	public void test_findUser2(){
		User user = userService.findUser("guanjianghuai");
	}
	

    /**
     * <b>案例:</b> search() 查询人员信息 <br/> 
     * [前提条件]<br/>
     * [输入参数]<br/> 
     * [预期输出]人员信息列表<br/>
     * [预期异常]<br/>
     */
	@Test
	public void test_findRoles(){
		List<Role> list = userService.findRoles("guanjianghuai");
		assertTrue( list.size() >=0 );
	}
	
    /**
     * <b>案例:</b> search() 查询人员信息 <br/> 
     * [前提条件]<br/>
     * [输入参数]<br/>
     * [预期输出]人员信息列表<br/>
     * [预期异常]<br/>
     */
	@Test
	public void test_findUsers(){
		Map<String, Object> filterMap = Maps.newHashMapWithExpectedSize(4);
		filterMap.put("LIKES_adAccount", "fnadmin");
		filterMap.put("LIKES_nachn", "");
		filterMap.put("LIKES_telno", "");
		
		LazyDataModel<User> list = userService.findUsers(filterMap);
		logger.debug("test_findUsers() : "+list.getRowCount());
		
	}
	
}
