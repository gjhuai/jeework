/** * LazyModelUtil.java 
* Created on 2011-12-11 下午4:58:27 
*/

package com.wcs.common.util;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.wcs.base.LazyDataModelBase;
import com.wcs.common.model.Resource;
import com.wcs.common.model.User;

/** 
* <p>Project: btcbase</p> 
* <p>Title: LazyModelUtil.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class LazyModelUtil {
    /**
     * 得到资源LazyModel列表
     * @param rsList
     * @return
     */
    public static LazyDataModel<Resource> getLazyResourceDataModel(List<Resource> rsList) {
        LazyDataModel<Resource> lazyMode = new LazyDataModelBase<Resource>(rsList);
        
        return lazyMode;
    }

    /**
     * 得到用户LazyModel列表
     * @param rsList
     * @return
     */
    public static LazyDataModel<User> getLazyUserDataModel(List<User> rsList) {
        LazyDataModel<User> lazyMode = new LazyDataModelBase<User>(rsList);
        
        return lazyMode;
    }
    
    
}
