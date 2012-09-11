package com.wcs.base.ql.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.collections.tuple.Tuple;
import com.wcs.base.collections.tuple.TwoTuple;
import com.wcs.base.util.ReflectionUtils;


/**
 * 
 * @author chris guan
 *
 */
public class XqlBuilder {
	final Logger logger = LoggerFactory.getLogger(XqlBuilder.class);
	private final String VAR_MARK = ":";
	private final char BLANK_SPACE = ' ';
	private final char OPENING_BRACE = '{';
	private final char CLOSING_BRACE = '}';
	private final char PERCENT_SIGN = '%';

	
	/**
	 * 
	 * @param xql
	 * @param filters
	 * @return map的key为处理后的jpql， value为剔除前缀、转换类型的filters
	 */
	public String makeJpql(String xql, Map<String,Object> filters){
		xql = this.cleanXqlAndFilters(xql, filters);
		
		Map<String,Object> copiedMap = new HashMap<String,Object>();
		copiedMap.putAll(filters);
		filters.clear();

		for (Map.Entry<String, Object> kv : copiedMap.entrySet()){
			// 分离出属性的类型, Tuple<newName_NoType,Type)
			TwoTuple<String,TypeEnum> newKey_type = splitType(kv.getKey());
			xql  = xql.replace(VAR_MARK+kv.getKey(), VAR_MARK+newKey_type.first);
			// 将原值转换成指定类型的Object,Tuple<newXql,newValue> （由于value是从界面传入，所以绝大多数是String）
			TwoTuple<String,Object> tuple2 = convertValueByType(newKey_type.second.getClass(),newKey_type.first,kv.getValue(),xql);
			filters.put(newKey_type.first, tuple2.second);
			xql = tuple2.first;
		}

		return xql.replace(OPENING_BRACE, BLANK_SPACE).replace(CLOSING_BRACE, BLANK_SPACE);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private TwoTuple<String,TypeEnum> splitType(String key){
		String newKey = StringUtils.substringAfter(key, "_");
		String typeStr = StringUtils.substringBefore(key, "_");
		
		// 获得属性的 Type 类型
		TypeEnum type = null;
        try {
        	type = Enum.valueOf(TypeEnum.class, typeStr);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter name: " + key
               + "Not prepared in accordance with the rules, attribute value types can not be.", e);
        }
		
		return Tuple.tuple(newKey, type);
	}
	
	/**
	 * 如果value是字符串，那么就检查Xql是否有Like匹配；
	 * 如果value的类型与type相同，那么不进行转换
	 * 
	 * @param type	value要转换的类型
	 * @param key filters中的key，也即Xql中要替换的标示符
	 * @param value filters中value，用来替换Xql中标示符的值	
	 * @return
	 */
	private TwoTuple<String,Object> convertValueByType(Class<?> type, String key, Object value, String xql){
        // 比较属性value的类型是否给定类型相同，如果相同则不转换，不相同，则需要将value转换为propertyType指定的类型
		if (value instanceof String){
			int pos = xql.indexOf(VAR_MARK+key);
			if (pos!=-1 && xql.charAt(pos+1)==PERCENT_SIGN){		//右 Like 匹配
				value = value + String.valueOf(PERCENT_SIGN);
				xql = xql.replace(VAR_MARK+key+String.valueOf(PERCENT_SIGN), VAR_MARK+key);
			}
			if (pos!=-1 && xql.charAt(pos-1)==PERCENT_SIGN){		// 左 Like 匹配
				value = String.valueOf(PERCENT_SIGN) + value;
				xql = xql.replace(String.valueOf(PERCENT_SIGN)+VAR_MARK+key, VAR_MARK+key);
			}
		} else if (!value.getClass().equals(type)){
        	Object newValue = ReflectionUtils.convertStringToObject(value.toString(), type);
        	return Tuple.tuple(xql,newValue);
        }
        return Tuple.tuple(xql,value);
	}
	/**
	 * 通过给定的filter，生成 SQL 语句。
	 * 
	 * @param xql
	 * @param filters
	 * @return
	 */
	public String makeXql(String xql, Map<String,Object> filters){
		xql = this.cleanXqlAndFilters(xql, filters);

		for (Map.Entry<String, Object> entry : filters.entrySet()){
			xql = xql.replace(VAR_MARK+entry.getKey(), entry.getValue().toString());
		}
		xql = xql.replace(OPENING_BRACE, BLANK_SPACE).replace(CLOSING_BRACE, BLANK_SPACE);
		return xql;
	};
	
	/**
	 * 通过给定的filter，生成 XQL 语句。
	 * 
	 * @param xql
	 * @param filters
	 * @return 第一结果为标准的命名参数的JPQL，第二结果为JPQL需要的命名参数的Map
	 */
	public String cleanXqlAndFilters(String xql, Map<String,Object> filters){
		// 得到 Xql 的 参数与Pattern的键值对
		Map<String,String> argPatterns = this.extractArguments(xql);
		// 清理掉 Xql 中值为空的参数
		String newXql = cleanupXql(xql, argPatterns, filters);
		// 得到新的 Xql 的 参数与Pattern的键值对
		if (!xql.equals(newXql)){
			argPatterns = this.extractArguments(newXql);
		}
		// 清除掉 filterMap 中多余的filter
		this.cleanupFilter(argPatterns.keySet(), filters);
		
		return newXql;
	}

	/**
	 * 从给定的 xql 提取需要传入的参数列表
	 * @param xql
	 * @return	key->value : e.g. name ->{ AND name=:name} 
	 */
	private Map<String,String> extractArguments(String xql){
		Map<String,String> ret = new HashMap<String,String>();
		
		String patternStr = "\\{.*?[%]{0,1}:(\\w+)[%]{0,1}.*?\\}";
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(xql);

		while (matcher.find()){
			logger.debug(matcher.group(0) + " => " + matcher.group(1));
			ret.put(matcher.group(1), matcher.group(0));
		}
		
		return ret;
	}
	
	/**
	 * 清理掉 Xql 中值为空的参数。即 filter 没有提供的参数或提供的参数的值为空。
	 * @param xql
	 * @param filters
	 * @return
	 */
	private String cleanupXql(String xql, Map<String,String> argPatterns, Map<String,Object> filters){
		for (Map.Entry<String, String> entry : argPatterns.entrySet()){
			Object obj = filters.get(entry.getKey());
			if ( (obj==null) || (obj instanceof String && StringUtils.isEmpty((String)obj)) ){
				xql = xql.replace(entry.getValue(), "");
				continue;
			}
		}
		return xql;
	}

	
	/**
	 * 清除掉 filterMap 中多余的filter。即清理掉 xql 中不需要的 filter。
	 * @param filters
	 */
	private void cleanupFilter(Set<String> args, Map<String,Object> filters){
		String[] keys = filters.keySet().toArray(new String[0]);
		for (String key : keys){
			if (!args.contains(key)){
				filters.remove(key);
			}
		}
	}
	
}
