package com.wcs.commons.security.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.persistence.Query;

import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.service.XqlEntityReader;
import com.wcs.base.test.BaseTest;

/**
 * 
 * @author Chris Guan
 */
public class XqlEntityReaderTest extends BaseTest{
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@EJB
	private XqlEntityReader entityReader;
	
	@Test
	public void test_createXqlQuery_1(){
		StringBuilder xql = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		xql.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{AND u.pernr=:S_pernr}")
			.append("{AND u.adAccount LIKE %:S_adAccount}")
			.append("{AND u.idCard=':S_idCard'}");
		
		filters.put("S_pernr","1");
		filters.put("S_adAccount", "chris");
		filters.put("B_sex", "男");
		
		// SELECT u.id FROM User u WHERE 1=1 AND u.age=:age  AND u.name LIKE :name
		Query query = entityReader.createXqlQuery(xql.toString(),filters);
		assertTrue(query!=null);
	}
	
}
