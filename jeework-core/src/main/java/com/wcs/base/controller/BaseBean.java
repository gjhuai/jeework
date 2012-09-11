package com.wcs.base.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.entity.BaseEntity;

public class BaseBean<T extends BaseEntity>  extends AbstractBaseBean<T> {
	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String inputPage = null;
	protected String listPage = null;
	protected String viewPage = null;
    
    //分页选择模板
    private String rowsPerPageTemplate;


	

    
	/**
     *
     * @param listPage  如果为null，则不改变LIST页面
     * @param inputPage 如果为null，则不改变INPUT页面
     * @param viewPage  如果为null，则不改变VIEW页面
     */
    public void setupPage(String listPage, String inputPage, String viewPage){
        if (!StringUtils.isEmpty(listPage)) this.listPage = listPage;
        if (!StringUtils.isEmpty(inputPage)) this.inputPage = inputPage;
        if (!StringUtils.isEmpty(viewPage)) this.viewPage = viewPage;
    }

    
    public void deleteEntity() {
        T entity = getInstance();
        entity.setDefunctInd('Y');
        entityWriter.update(entity);
    }
	
	/**
	 * 判断 instance 是否处于EJB容器托管状态
	 * @return
	 */
	public boolean isManaged() {
		return entityWriter.isManaged(getInstance());
	}


	public void setUpdatedBy(String userName){
            this.instance.setUpdatedBy(userName);
    }

    public void setCreatedBy(String userName){
            this.instance.setUpdatedBy(userName);
    }
	
	
	
	
	//----------------------------- set & get --------------------------------//

	
	
    public String getInputPage() {
        return inputPage;
    }

    public void setInputPage(String inputPage) {
        this.inputPage = inputPage;
    }

    public String getListPage() {
        return listPage;
    }

    public void setListPage(String listPage) {
        this.listPage = listPage;
    }

    public String getViewPage() {
        return viewPage;
    }

    public void setViewPage(String viewPage) {
        this.viewPage = viewPage;
    }

	public String getRowsPerPageTemplate() {
		return SystemConfiguration.ROWS_PER_PAGE_TEMPLATE;
	}
}
