package com.wcs.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wcs.base.controller.PropertyFilter.PropertyType;
import com.wcs.base.ql.util.XqlBuilder;
import com.wcs.base.util.ReflectionUtils;
import com.wcs.base.util.StringUtils;
import com.wcs.base.util.Validate;


/**
 * JPA Service 基类.
 * <p/>
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可直接使用,也可以扩展EntityService
 *
 * @author Chris Guan
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class XqlEntityReader extends EntityReader {
	
    // -----------------------------------  List 查询  --------------------------------------//
	/**
     * <p>按 XSQL 查询，参数以 Filter(Map) 形式提供.
     * xsql的写法参考：http://code.google.com/p/rapid-xsqlbuilder/，例如：
     * String xsql = "select b from Book b where 1=1"  + " /~ and name = {name}~/ " ;
     * 位于/~ ~/之间的语句为可选部分，{name}表示变量值，当 map 中没有 name值或name为空（null或“”）时，/~ ~/之间的语句被忽略。
     * filterMap 为jsf页面与Bean的传值容器，命名方法示例：EQL_id, LIKES_name等
     * <p/>
     * 用法例子：
     * <p/>
     * 页面代码：
     * 书名：<h:inputText value="#{bookBean.map['EQS_name']}"/>
     * 作者：<h:inputText value="#{bookBean.map['LIKES_author']}"/>
     * 价格：<h:inputText value="#{bookBean.map['EQN_price']}"/>
     * 页数：<h:inputText value="#{bookBean.map['EQI_pageNum']}"/>
     * <p/>
     * Bean类代码：
     * StringBuilder sql =  new StringBuilder("select name from Book where 1=1");
     * sql.append(" /~ and name = {name}~/ ")
     * .append(" /~ and author like {author}~/ ")
     * .append(" /~ and price = {price}~/")
     * .append(" /~ and pageNum = {pageNum}~/");
     * <p/>
     * private Map<String,Object> map = Maps.newHashMapWithExpectedSize(5);
     * //set, get...
     * <p/>
     * List list = entityService.findXsqlList(sql.toString(), map);
     * <p/>
     * </p>
     *
     * @param xql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 从页面上以Map形式传过来的属性集合.
     * @return 分页的查询结果.
     */
    @SuppressWarnings("unchecked")
	public <X> List<X> findXqlList(final String xql, final Map<String, Object> filterMap) {
        Validate.hasText(xql, "xsql不能为空");
        Query q = createXqlQuery(xql, filterMap);

        return q.getResultList();
    }


    /**
     * 根据查询XSQL与参数结合创建Query对象.
     * 与 findModelByMap() 函数可进行更加灵活的操作.
     *
     * @param xql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 参数集合，从页面上以Map形式传过来的属性集合.
     * @return 返回 javax.persistence.Query 对象
     */
    public Query createXqlQuery(String xql, Map<String, Object> filterMap) {
    	XqlBuilder builder = new XqlBuilder();
    	String jpql = builder.makeJpql(xql, filterMap);
    	
        //Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        //paramMap = this.buildParamMap(xql, filterMap);
        
        // 构建 JPQL 语句
        //XsqlBuilder builder = new XsqlBuilder();
        //String jpql = builder.generateHql(xql, paramMap).getXsql().toString();

        return createQuery(jpql, filterMap);
    }

    /**
     * 用法参考
     *
     * @param xql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 参数集合，从页面上以Map形式传过来的属性集合.
     * @return paramMap  回调的参数列表，Map的key剔除了前缀
     * @see XqlEntityReader#findXqlPage
     */
    public Map<String, Object> buildParamMap(String xql, Map<String, Object> filterMap) {
        // 得到需要动态构建的字段
        List<String> avialableKeys = Lists.newArrayList();

        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(xql);

        while (m.find()) {
            avialableKeys.add(m.group(1));
        }
        //剔除不需要的过滤属性 和 空值的属性
        Map<String, Object> tmpMap = Maps.newHashMap();
        for (Map.Entry<String, Object> kv : filterMap.entrySet()){
        	if (kv.getValue()==null || "".equals(kv.getValue())){
        		continue;
        	}
        	boolean hasIt = false;
        	for (String s : avialableKeys){
        		if (kv.getKey().contains(s)){
        			hasIt = true;
        			break;
        		}
        	}
        	if (hasIt) tmpMap.put(kv.getKey(),kv.getValue());
        }
        //Assert.isTrue(avialableKeys.size()== filterMap.size());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = this.convertMap(tmpMap);
        
        return paramMap;
    }
    
    private Map<String,Object> convertMap(Map<String, Object> xsqlFilterMap){
    	Map<String, Object> paramMap = Maps.newHashMap();
        
    	for (Map.Entry<String, Object> kv : xsqlFilterMap.entrySet()){
			// 获取属性的 Name (key)
			String filterName = kv.getKey();
			String propertyName = StringUtils.substringAfter(filterName, "_");
			
	        //分离属性的 Type
	        String matchTypeStr = StringUtils.substringBefore(filterName, "_");
	        String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
	        
	        // 如果带有 LIKE，则一定为字符串，此时，需要加上 % （目前只处理了全 Like）
	        if(filterName.contains("LIKE")){
	        	if (!("S".equals(propertyTypeCode.toUpperCase()))) {
	        		throw new IllegalArgumentException("filter name: " + filterName
	        				+ "'LIKE' needs 'S'.");
	        	}
	        	paramMap.put(propertyName, "%" + kv.getValue() + "%");   // 构建 paramMap
	        	continue;
	        } 
	        
	        // 获得属性的 Type 的 Class 类型
	        Class<?> propertyType = null;
	        try {
	        	propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
	        } catch (RuntimeException e) {
	            throw new IllegalArgumentException("filter name: " + filterName
	               + "Not prepared in accordance with the rules, attribute value types can not be.", e);
	        }
	        
	        // 比较属性value的类型是否给定类型相同，如果相同则不转换，不相同，则需要将value转换为propertyType指定的类型
	        if (kv.getValue().getClass().equals(propertyType)){
	        	paramMap.put(propertyName, kv.getValue());
	        } else {
	        	Object propertyValue = ReflectionUtils.convertStringToObject(kv.getValue().toString(), propertyType);
	        	paramMap.put(propertyName, propertyValue);
	        }
    	}
    	
    	return paramMap;
    }

}
