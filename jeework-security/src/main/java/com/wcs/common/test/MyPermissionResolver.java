/** * MyPermissionResolver.java 
* Created on 2011-11-29 下午5:56:12 
*/

package com.wcs.common.test;

import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.PermissionResolverAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* <p>Project: btcbase</p> 
* <p>Title: MyPermissionResolver.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class MyPermissionResolver implements PermissionResolverAware {
    private static final Logger log = LoggerFactory.getLogger(CustomAuthorizer.class);
    
    public MyPermissionResolver() {
        log.info("Permisssion Resolver start.");
    }

    @Override
    public void setPermissionResolver(PermissionResolver pr) {
        // TODO Auto-generated method stub
        
    }

}
