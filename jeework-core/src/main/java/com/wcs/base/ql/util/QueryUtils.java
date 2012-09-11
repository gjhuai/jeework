package com.wcs.base.ql.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wcs.base.util.StringUtils;

/**
 * 拼接sql语句的工具类
 * 
 * @author Chris Guan
 *
 */
public class QueryUtils {
	public final static String EMPTY_STRING = new String();
	public final static char BLANK_SPACE = ' ';
	public final static String OP_AND = new String(" and ");
	
	public static String and(String subJpql, Number param){
		if ( param==null ){
			return EMPTY_STRING;
		}
		return OP_AND + subJpql.replace("?", String.valueOf(param)) + BLANK_SPACE;
	}
	
	public static String and(String subJpql, String param){
		if ( StringUtils.isEmpty(param) ){
			return EMPTY_STRING;
		}
		return OP_AND + subJpql.replace("?", param.trim()) + BLANK_SPACE;
	}
	
	public static String and(String subJpql, String ... param ){
		String[] params = param;
		if ( params.length == 0 ){
			return EMPTY_STRING;
		}
		
		for(String str:params){
			subJpql = subJpql.replace("?", str);
		}
		
		return OP_AND + subJpql + BLANK_SPACE;
	}
	
	public static String and(String subJpql, Character param){
		if ( param==null) {
			return EMPTY_STRING;
		}
		return OP_AND + subJpql.replace('?', param) + BLANK_SPACE;
	}
	
	
	public static String and(String subJpql, Date param, String pattern){
		if ( param==null ){
			return EMPTY_STRING;
		}
		SimpleDateFormat sdf= new SimpleDateFormat(pattern);
		
		return OP_AND + subJpql.replace("?", sdf.format(param)) + BLANK_SPACE;
	}
	
	
}
