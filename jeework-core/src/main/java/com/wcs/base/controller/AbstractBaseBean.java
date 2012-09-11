package com.wcs.base.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.wcs.base.entity.IdEntity;
import com.wcs.base.service.EntityReader;
import com.wcs.base.service.EntityWriter;
import com.wcs.base.util.ReflectionUtils;
/**
 * 
 * <p>Project: btcbase</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">Chen Long</a>
 */
public abstract class AbstractBaseBean<T extends IdEntity> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;
    protected T instance; // currentEntity
    @Inject
    protected EntityWriter entityWriter;
    
    @EJB(beanName="EntityReader")
    EntityReader entityReader;

    protected void initInstance() {
        if (instance == null) {
            if (id != null) {
                instance = loadInstance();
            } else {
                instance = createInstance();
            }
        }
    }

    public T loadInstance() {
        return entityReader.findUnique(getClassType(), getId());
    }

    public T createInstance() {
        try {
            return getClassType().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveEntity() {
        if (idIsEmpty()) {

            entityWriter.update(getInstance());
        } else {
            this.getInstance().setId(null);// 修改人：liaowei
            entityWriter.create(getInstance());
        }
    }

    /**
     * 判断实体 instance 的id是否有值 
     * @return
     */
    public boolean idIsEmpty() {
        return getInstance().getId() != null && getInstance().getId() > 0l;// 修改人：liaowei
    }

    private Class<T> getClassType() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public T getInstance() {
        initInstance();
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
