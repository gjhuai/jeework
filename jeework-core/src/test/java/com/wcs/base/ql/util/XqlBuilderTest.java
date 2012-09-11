package com.wcs.base.ql.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
public class XqlBuilderTest {
	@Test
	public void testString(){
		Map<String,String> ret = new HashMap<String,String>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{ AND u.age=:age }")
			.append("{ AND u.name LIKE %:name }");
		
		String patternStr = "\\{.+?[%]{0,1}:(\\w+)[%]{0,1}.+?\\}";
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sb.toString());

		while (matcher.find()){
			ret.put(matcher.group(1), matcher.group(0));
		}
		
		assertEquals("{name={ AND u.name LIKE %:name }, age={ AND u.age=:age }}",ret.toString());
		
	}
	
	/**
	 * 正确的测试途径
	 */
	@Test
	public void testCleanXqlAndFilters_1(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder sb = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		sb.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{ AND u.age=:age }")
			.append("{ AND u.name LIKE '%:name' }");
		
		filters.put("age",1);
		filters.put("name", "chris");
		
		String xql = builder.cleanXqlAndFilters(sb.toString(), filters);
		assertEquals("SELECT u.id FROM User u WHERE 1=1{ AND u.age=:age }{ AND u.name LIKE '%:name' }",xql);
		assertEquals("{name=chris, age=1}", filters.toString());
	}
	
	/**
	 * 备选1：传入filters 中参数不足时
	 */
	@Test
	public void testCleanXqlAndFilters_2(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder sb = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		sb.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{ AND u.age=:age }")
			.append("{ AND u.name LIKE %:name }");
		
		String xql = builder.cleanXqlAndFilters(sb.toString(), filters);
		assertEquals("SELECT u.id FROM User u WHERE 1=1",xql);
		assertEquals("{}",filters.toString());
		
		filters.put("age",1);
		filters.put("name", "");
		
		xql = builder.cleanXqlAndFilters(sb.toString(), filters);
		assertEquals("SELECT u.id FROM User u WHERE 1=1{ AND u.age=:age }",xql);
		assertEquals("{age=1}",filters.toString());
	}
	
	/**
	 * 备选2：传入filters 中参数过多时
	 */
	@Test
	public void testCleanXqlAndFilters_3(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder sb = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		sb.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{ AND u.age=:age }")
			.append("{ AND u.name LIKE %:name }");
		
		filters.put("age",1);
		filters.put("name", "chris");
		filters.put("sex", "男");
		
		String xql = builder.cleanXqlAndFilters(sb.toString(), filters);
		assertEquals("SELECT u.id FROM User u WHERE 1=1{ AND u.age=:age }{ AND u.name LIKE %:name }",xql);
		assertEquals("{name=chris, age=1}",filters.toString());
	}
	
	/**
	 * 备选3：传入filters 中参数不足和过多的问题同时存在
	 */
	@Test
	public void testCleanXqlAndFilters_4(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder sb = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		sb.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{ AND u.age=:age }")
			.append("{ AND u.name LIKE %:name }")
			.append("{ AND u.idCard=:idCard }");
		
		filters.put("age",1);
		filters.put("name", "chris");
		filters.put("sex", "男");
		
		String xql = builder.cleanXqlAndFilters(sb.toString(), filters);
		assertEquals("SELECT u.id FROM User u WHERE 1=1{ AND u.age=:age }{ AND u.name LIKE %:name }",xql);
		assertEquals("{name=chris, age=1}",filters.toString());
	}
	
	@Test
	public void testMakeXql(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder xql = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		xql.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{AND u.age=:age}")
			.append("{AND u.name LIKE '%:name'}")
			.append("{AND u.idCard=':idCard'}");
		
		filters.put("age",1);
		filters.put("name", "chris");
		filters.put("sex", "男");
		
		String newXql = builder.makeXql(xql.toString(), filters);
		//System.out.println(newXql);
		assertEquals("SELECT u.id FROM User u WHERE 1=1 AND u.age=1  AND u.name LIKE '%chris' ", newXql);
		
	}
	
	@Test
	public void testMakeJpql_1(){
		XqlBuilder builder = new XqlBuilder();

		StringBuilder xql = new StringBuilder();
		Map<String,Object> filters = new HashMap<String,Object>();

		xql.append("SELECT u.id FROM User u WHERE 1=1")
			.append("{AND u.age=:I_age}")
			.append("{AND u.name LIKE %:S_name}")
			.append("{AND u.idCard=':S_idCard'}");
		
		filters.put("I_age",1);
		filters.put("S_name", "chris");
		filters.put("B_sex", "男");
		
		String newXql = builder.makeJpql(xql.toString(), filters);
		//System.out.println(filters);
		//System.out.println(newXql);
		assertEquals("{age=1, name=%chris}",filters.toString());
		assertEquals("SELECT u.id FROM User u WHERE 1=1 AND u.age=:age  AND u.name LIKE :name ", newXql);
	}

}
