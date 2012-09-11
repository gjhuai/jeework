package com.wcs.base.service;

import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wcs.base.util.Validate;

public abstract class QueryCreator {

	@PersistenceContext(unitName = "pu")
	protected EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}

    /**
     * 根据查询JPQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Query createQuery(final String queryString, final Object... values) {
        Validate.hasText(queryString, "queryString不能为空");
        Query query = entityManager.createQuery(queryString);

        if (values != null && values.length>0) {
            for (int i = 1; i <= values.length; i++) {
                query.setParameter(i, values[i - 1]);
            }
        }
        return query;
    }

    /**
     * 根据查询JPQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param queryString jpql 查询语句
     * @param values      命名参数,按名称绑定.
     * @return 返回 javax.persistence.Query 对象.
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Validate.hasText(queryString, "queryString不能为空");
        Query query = entityManager.createQuery(queryString);
        for (Map.Entry<String, ?> kv : values.entrySet()) {
            query.setParameter(kv.getKey(), kv.getValue());
        }
        return query;
    }
    
    public EntityManager getEM(){
    	return entityManager;
    }

}