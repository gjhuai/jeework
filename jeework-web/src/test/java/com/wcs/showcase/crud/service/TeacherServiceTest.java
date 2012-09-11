package com.wcs.showcase.crud.service;

import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;

import org.junit.Test;

import com.wcs.base.test.BaseTest;
import com.wcs.showcase.crud.model.Teacher;

/**
/**
 * <p>Project: btcbase-web</p> 
 * <p>Title: </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright 2011-2020.All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author guanjianghuai
 */
public class TeacherServiceTest extends BaseTest{

	@EJB
	private TeacherService teacherService;
	
    /**
     * <b>案例:</b> search() 查询人员信息 <br/> 
     * [前提条件]<br/>
     * [输入参数]<br/> 
     * [预期输出]人员信息列表<br/>
     * [预期异常]<br/>
     */
	@Test
	public void test_search(){
		List<Teacher> list = teacherService.search();
		assertTrue( list.size() >=0 );
	}
	
}
