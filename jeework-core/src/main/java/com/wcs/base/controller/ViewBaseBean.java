package com.wcs.base.controller;

import com.wcs.base.entity.BaseEntity;

@SuppressWarnings("serial")
public abstract class ViewBaseBean<T extends BaseEntity> 
		extends BaseBean<T> implements IBaseBean{

	public String save() {
		super.saveEntity();
		return listPage ==null?"/faces/debug/failed.xhtml": listPage;
	}

	public String delete() {
		super.deleteEntity();
		return listPage ==null?"/faces/debug/failed.xhtml": listPage;
	}

}
