package com.wcs.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javacommon.xsqlbuilder.XsqlBuilder;

import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.controller.PropertyFilter.PropertyType;
import com.wcs.base.entity.IdEntity;
import com.wcs.base.util.ReflectionUtils;
import com.wcs.base.util.StringUtils;
import com.wcs.base.util.Validate;


/**
 * JPA Service 基类.
 * <p/>
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可直接使用,也可以扩展EntityService
 *
 * @author chris
 */
public abstract class EntityService extends AbstractEntityService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

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
     * List list = entityService.findByMap(sql.toString(), map);
     * <p/>
     * </p>
     *
     * @param xsql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 从页面上以Map形式传过来的属性集合.
     * @return 分页的查询结果.
     */
    
    public <X> List<X> findListByFilter(final String xsql, final Map<String, Object> filterMap) {
        Validate.hasText(xsql, "xsql不能为空");
        Query q = createQueryByMap(xsql, filterMap);

        return q.getResultList();
    }

    // ------------------------------ 计算记录条数  count  ----------------------------//

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的jpql语句,复杂的jpql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String jpql, final Object... values) {
        String countHql = prepareCountHql(jpql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("jpql can't be auto count, jpql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的jpql语句,复杂的jpql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String jpql, final Map<String, ?> values) {
        String countHql = prepareCountHql(jpql);

        try {
            Long count = findUnique(countHql.trim(), values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("jpql can't be auto count, jpql is:" + countHql, e);
        }
    }

    /**
     * select子句与order by子句会影响count查询,进行简单的排除.
     *
     * @param jpql JPQL 查询语句
     * @return 与 jpql对应的 count 语句
     */
    private String prepareCountHql(String jpql) {
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(");

        String fromClause = jpql.substring(jpql.toUpperCase().indexOf("FROM"));

        int beginPos = fromClause.toUpperCase().indexOf("FROM");
        int endPos = fromClause.toUpperCase().indexOf("WHERE");
        String countObj = StringUtils.substring(fromClause,beginPos,endPos).trim();

        countObj =  countObj.substring(countObj.lastIndexOf(" ") + 1) ;

        countJpql.append(countObj).append(") ");

        int pos = fromClause.toUpperCase().indexOf("ORDER BY");
        if (pos != -1) {
            fromClause = fromClause.substring(0, pos);
        }

        countJpql.append(fromClause);
        return countJpql.toString();
    }


    // -------------------- LazyDataModel 动态分页  findModel()  -------------------//

    /**
     * 按JPQL分页查询. 仅供 PrimeFaces 的 <p:dataTable> 实现分页使用。
     *
     * @param jpql   jpql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 以 PrimeFaces 的LazyDataModel 形式返回.
     */
    @SuppressWarnings("unchecked")
    public <T extends IdEntity> LazyDataModel<T> findPage(final String jpql, final Object... values) {
    	PageDataModel<T> lazyModel = new PageDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, String> filters) {
                // 得到总记录数
                Integer count = Long.valueOf(countHqlResult(jpql, values)).intValue();
                this.setRowCount(count);
                //  得到查询结果
                Query q = createQuery(jpql, values);
                setPageParameterToQuery(q, first, pageSize);
                List result = q.getResultList();
                return result;
			}
			
        };


        return lazyModel;
    }
    
    abstract class  PageDataModel<T extends IdEntity> extends LazyDataModel<T> {
        @Override  
        public Object getRowKey(T entity) {
            return entity.getId();  
        }  
        
        @Override
        public int getPageSize(){
        	return SystemConfiguration.PAGE_SIZE;
        }	
    }

    /**
     * 按JPQL分页查询. 仅供 PrimeFaces 的 <p:dataTable> 实现分页使用。
     *
     * @param jpql   jpql语句.
     * @param values 命名参数,按名称绑定.
     * @return 分页查询结果, 以 PrimeFaces 的LazyDataModel 形式返回.
     */
    @SuppressWarnings("unchecked")
    public <T extends IdEntity> LazyDataModel<T> findPage(final String jpql, final Map<String, Object> values) {
    	PageDataModel<T> lazyModel = new PageDataModel<T>() {
            @Override
            public List<T> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, String> filters) {
                // 得到总记录数
                Integer count = Long.valueOf(countHqlResult(jpql, values)).intValue();
                this.setRowCount(count);
                // 得到查询结果
                Query q = createQuery(jpql, values);
                setPageParameterToQuery(q, first, pageSize);
                List result = q.getResultList();
                return result;
            }
        };

        return lazyModel;
    }

    /**
     * <p>按 XSQL 分页查询，参数以 Map 形式提供，仅供 PrimeFaces 的 <p:dataTable> 实现分页使用。
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
     * private Map<String,Object> map = Maps.newHashMapWithExpectedSize(5);
     * // set, get ...
     * <p/>
     * StringBuilder sql =  new StringBuilder("select name from Book where 1=1");
     * sql.append(" /~ and name = {name}~/ ")
     * .append(" /~ and author like {author}~/ ")
     * .append(" /~ and price = {price}~/")
     * .append(" /~ and pageNum = {pageNum}~/");
     * <p/>
     * LazyDataModel lazyModel = entityService.findModelByMap(sql.toString(), map);
     * <p/>
     * </p>
     *
     * @param xsql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 从页面上以Map形式传过来的属性集合.
     * @return 分页的查询结果.  以 PrimeFaces 的LazyDataModel 形式返回。
     * @see com.wcs.common.controller.permissions.UserBean#search()
     */
    @SuppressWarnings("unchecked")
    public <T extends IdEntity> LazyDataModel<T> findPageByFilter(final String xsql, final Map<String, Object> filterMap) {
        //Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        Map<String, Object> paramMap = new HashMap<String,Object>();
        
        String jpql = this.buildJpqlAndParams(xsql, filterMap, paramMap);

        return this.findPage(jpql, paramMap);
    }

    
    // -------------------- 辅助方法  -------------------//

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    
    protected <T> Query setPageParameterToQuery(final Query q, final int first, final int pageSize) {     
    	Validate.isTrue(pageSize > 0, "Page Size must larger than zero");

        //hibernate的firstResult的序号从0开始 primeface lazymodel first从0开始
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q;
    }

    /**
     * 根据查询XSQL与参数结合创建Query对象.
     * 与 findModelByMap() 函数可进行更加灵活的操作.
     *
     * @param xsql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 参数集合，从页面上以Map形式传过来的属性集合.
     * @return 返回 javax.persistence.Query 对象
     */
    public Query createQueryByMap(String xsql, Map<String, Object> filterMap) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
        String jpql = this.buildJpqlAndParams(xsql, filterMap, paramMap);

        return createQuery(jpql, paramMap);
    }

    /**
     * 用法参考
     *
     * @param xsql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 参数集合，从页面上以Map形式传过来的属性集合.
     * @param paramMap  回调的参数列表，Map的key剔除了前缀
     * @return JPQL的查询语句
     * @see StatelessEntityService#findPageByFilter(String, java.util.Map)
     */
    public String buildJpqlAndParams(String xsql, Map<String, Object> filterMap, Map<String, Object> paramMap) {
        // 得到需要动态构建的字段
        List<String> avialableKeys = Lists.newArrayList();

        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(xsql);

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
        this.convertMap(tmpMap, paramMap);

        // 构建 JPQL 语句
        XsqlBuilder builder = new XsqlBuilder();
        String jpql = builder.generateHql(xsql, paramMap).getXsql().toString();

        return jpql;
    }
    
    private Map<String,Object> convertMap(Map<String, Object> filterMap,Map<String, Object> paramMap){
    	//Map<String, Object> paramMap = Maps.newHashMap();
        
    	for (Map.Entry<String, Object> kv : filterMap.entrySet()){
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
