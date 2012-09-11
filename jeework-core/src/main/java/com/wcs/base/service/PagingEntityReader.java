package com.wcs.base.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.ql.util.XqlBuilder;
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
public class PagingEntityReader extends XqlEntityReader {
	
    abstract class  PageDataModel<T> extends LazyDataModel<T> {
		private static final long serialVersionUID = 1L;
		private String xql = null;
		private Map<String,Object> values = null;
		
//		/**
//		 * 供使用xql进行查询使用
//		 * @param xql
//		 */
//		public PageDataModel(String xql, Map<String,Object> values){
//			this.xql = xql;
//			this.values =values;
//		}

		/**
		 * 可以根据需要自己实现
		 */
//		@Override  
//        public Object getRowKey(T entity) {
//			if (entity instanceof IdEntity){
//				return ((IdEntity)entity).getId();
//			}
//			throw new UnsupportedOperationException("BTCBASE:getRowData(String rowKey) must be implemented when basic rowKey algorithm is not used.");
//        }
        
        @Override
        public int getPageSize(){
        	return SystemConfiguration.PAGE_SIZE;
        }
        
		protected List<T> getPageData(final String jpql, int first, int pageSize, final Object... values) {
			// 得到总记录数
			Integer count = Long.valueOf(countHqlResult(jpql, values)).intValue();
            this.setRowCount(count);
            // 得到查询结果
            Query q = createQuery(jpql, values);
            setPageParameterToQuery(q, first, pageSize);
            List<T> result = q.getResultList();
            return result;
		}
		
		protected List<T> getPageData(final String jpql, int first, int pageSize, final Map<String, Object> values) {
			// 得到总记录数
            Integer count = Long.valueOf(countHqlResult(jpql, values)).intValue();
            this.setRowCount(count);
            // 得到查询结果
            Query q = createQuery(jpql, values);
            setPageParameterToQuery(q, first, pageSize);
            List<T> result = q.getResultList();
            return result;
		}

		public String getXql() {
			return xql;
		}

		public void setXql(String xql) {
			this.xql = xql;
		}

		public Map<String, Object> getValues() {
			return values;
		}

		public void setValues(Map<String, Object> values) {
			this.values = values;
		}
		
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
    protected Long countHqlResult(final String jpql, final Map<String, ?> values) {
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
    	boolean distinct= false;
		if(jpql.toUpperCase().contains("DISTINCT")){
			distinct = true;
		}
        String fromQl = jpql.substring(jpql.toUpperCase().indexOf("FROM"));

        int pos = fromQl.toUpperCase().indexOf("ORDER BY");
        if (pos != -1) {
            fromQl = fromQl.substring(0, pos);
        }
        String[] fromQls =fromQl.split("[,\\s]+");//多个空格
        String fromQl1 ="";
		if("AS".equals(fromQls[2].toUpperCase())){
			fromQl1= fromQls[3];
		}else{
			fromQl1= fromQls[2];
		}
		StringBuilder countOfQuery = new StringBuilder("SELECT COUNT("+ (distinct ? "DISTINCT "+fromQl1+".id" : fromQl1+".id")+") ");
        countOfQuery.append(fromQl);
        return countOfQuery.toString();
    }
    

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    
    protected <T> Query setPageParameterToQuery(final Query q, final int first, final int pageSize) {     
    	Validate.isTrue(pageSize > 0, "Page Size must larger than zero");

        // JPA的firstResult的序号从0开始 primeface lazymodel first从0开始
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q;
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
    public <T> LazyDataModel<T> findPage(final String jpql, final Object... values) {
    	PageDataModel<T> lazyModel = new PageDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, String> filters) {
                return getPageData(jpql, first, pageSize, values);
			}


        };

        return lazyModel;
    }


    /**
     * 按JPQL分页查询. 仅供 PrimeFaces 的 <p:dataTable> 实现分页使用。
     *
     * @param jpql   jpql语句.
     * @param values 命名参数,按名称绑定.
     * @return 分页查询结果, 以 PrimeFaces 的LazyDataModel 形式返回.
     */
    @SuppressWarnings("unchecked")
    public <T> LazyDataModel<T> findPage(final String jpql, final Map<String, Object> values) {
    	PageDataModel<T> lazyModel = new PageDataModel<T>() {
            @Override
            public List<T> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, String> filters) {
                return getPageData(jpql, first, pageSize, values);
            }
        };

        return lazyModel;
    }

    /**
     * <p>按 XSQL 分页查询，参数以 Map 形式提供，仅供 PrimeFaces 的 <p:dataTable> 实现分页使用。
     * xsql的写法参考：http://code.google.com/p/rapid-xsqlbuilder/，例如：
     * String xsql = "select b from Book b where 1=1"  + " /~ and name = {name}~/ " ;
     * 位于/~ ~/之间的语句为可选部分，{name}表示变量值，当 map 中没有 name值或name为空（null或“”）时，/~ ~/之间的语句被忽略。
     * xsqlFilterMap 为jsf页面与Bean的传值容器，命名方法示例：EQL_id, LIKES_name等
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
     * @param xql      基于 xsqlbuilder 样式的类SQL语句.
     * @param filterMap 从页面上以Map形式传过来的属性集合.
     * @return 分页的查询结果.  以 PrimeFaces 的LazyDataModel 形式返回。
     */
    @SuppressWarnings("unchecked")
    public <T> LazyDataModel<T> findXqlPage(final String xql, final Map<String, Object> filterMap) {
    	XqlBuilder builder = new XqlBuilder();
    	String jpql = builder.makeJpql(xql, filterMap);
    	
        //Map<String, Object> paramMap = new HashMap<String,Object>();      
        //paramMap = this.buildParamMap(xql, filterMap);
        
        // 构建 JPQL 语句
        //XsqlBuilder builder = new XsqlBuilder();
        //String jpql = builder.generateHql(xql, paramMap).getXsql().toString();
        
        return this.findPage(jpql, filterMap);
    }
//    
//    public Object[] xqlToJpql(final String xql, final Map<String, Object> filterMap){
//    	XqlBuilder builder = new XqlBuilder();
//    	String jpql = builder.makeJpql(xql, filterMap);
//    	
//        //Map<String, Object> paramMap = new HashMap<String,Object>();      
//        //paramMap = this.buildParamMap(xql, filterMap);
//        
//        // 构建 JPQL 语句
//        //XsqlBuilder builder = new XsqlBuilder();
//        //String jpql = builder.generateHql(xql, paramMap).getXsql().toString();
//        return new Object[]{jpql,filterMap};
//    }

}
