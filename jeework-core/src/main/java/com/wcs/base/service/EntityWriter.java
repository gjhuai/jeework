package com.wcs.base.service;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.util.Validate;


/**
 * JPA Service 基类.
 * <p/>
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可直接使用,也可以扩展EntityService
 *
 * @author chris
 */
@Stateless
public class EntityWriter extends QueryCreator {
	private static final long serialVersionUID = 1L;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    // ----------------------------- 基本 CRUD 操作方法 ------------------------------//

    /**
     * 保存新增的对象.
     */
    public <T> T create(final T entity) {
        Validate.notNull(entity, "entity不能为空");
        entityManager.persist(entity);
       // logger.debug("create-create entity: {}", entity);
        return entity;
    }

    /**
     * 保存修改的对象.
     */
    public <T> T update(final T entity) {
        Validate.notNull(entity, "entity不能为空");
        entityManager.merge(entity);
        // logger.debug("update entity: {}", entity);
        return entity;
    }

    /**
     * 删除对象.
     *
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public <T> void delete(final T entity) {
        Validate.notNull(entity, "entity不能为空");
        entityManager.remove(entity);
        // logger.debug("delete entity: {}", entity);
    }

    /**
     * 按id删除对象.
     */
    public void delete(Class<?> entityClass, final Serializable id) {
        Validate.notNull(id, "id不能为空");
        delete( entityManager.find(entityClass, id) );
       // logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
    }
    
    public boolean isManaged(Object entity) {
		return entityManager.contains(entity);
	}


    // ---------------------------- execute 及其他方法 ------------------------------ //

    /**
     * 执行JPQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */

    public int batchExecute(final String jpql, final Object... values) {
        return createQuery(jpql, values).executeUpdate();
    }

    /**
     * 执行JPQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String jpql, final Map<String, ?> values) {
        return createQuery(jpql, values).executeUpdate();
    }

}
