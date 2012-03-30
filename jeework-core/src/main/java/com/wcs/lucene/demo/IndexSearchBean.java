/** * IndexSearchBean.java 
* Created on 2012-2-21 上午9:55:28 
*/

package com.wcs.lucene.demo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.wcs.base.util.JSFUtils;

/** 
* <p>Project: btcbase</p> 
* <p>Title: IndexSearchBean.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2012-2020.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

@ManagedBean
@RequestScoped
public class IndexSearchBean {
    
    /**
     * 字符串搜索
     */
    public void indexSearch() {
        String searchStr = JSFUtils.getRequestParam("searchStr");
        System.out.println("==========================" + searchStr);
    }
}
