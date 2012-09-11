package com.wcs;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringTest {

	@Test
	public void splitTest(){
		String s = "FROM User u, Person p, PU pu ";
		String[] fromQls =s.split("[,\\s]+");//多个空格
		
		assertEquals(fromQls.length, 7);
		for (String ss : fromQls){
			assertFalse(ss.contains(","));
		}
	}
	
	@Test
	public void replaceTest(){
		String  s = "base:setting:preference";
		
		String result = s.replaceAll(":", "_");
		
		assertEquals("base:setting:preference", s);
		assertEquals("base_setting_preference", result);
	}
}
