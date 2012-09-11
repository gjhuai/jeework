package com.wcs.commons.security.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.wcs.base.test.BaseTest;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.User;

/**
 * 
 * @author Chris Guan
 */
public class RoleServiceTest extends BaseTest{

	@EJB
	private RoleService roleService;
	
    /**
     * <b>案例:</b> search() 查询人员信息 <br/> 
     * [前提条件]<br/>
     * [输入参数]<br/>
     * [预期输出]人员信息列表<br/>
     * [预期异常]<br/>
     */
	@Test
	public void test_allocRoles(){
		User user = new User();
		user.setId(1L);
		user.setAdAccount("gjh");
		
		List<Role> roleList = Lists.newArrayList();
		roleService.allocRoles(user, roleList);
		
		roleList = roleService.findAllRoles();
		
		roleService.allocRoles(user, roleList.subList(0, 5));
	}
	
}
