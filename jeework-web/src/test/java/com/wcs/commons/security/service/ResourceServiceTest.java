package com.wcs.commons.security.service;

import javax.ejb.EJB;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.test.BaseTest;

/**
 * <p>Project: btcbase-web</p> 
 * <p>Title: </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright 2011-2020.All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author guanjianghuai
 */
public class ResourceServiceTest extends BaseTest{
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private ResourceCache resourceCache;
	
    /**
     * <b>案例:</b> search() 查询人员信息 <br/> 
     * [前提条件]<br/>
     * [输入参数]<br/>
     * [预期输出]人员信息列表<br/>
     * [预期异常]<br/>
     */
	@Test
	public void testSearch(){
//		List<Resource> list = resourceService.findAllSysResource();
//		for(int i=0;i<list.size();i++){
//			logger.debug(list.get(i).getName());
//		}
	}
	
}
