/** * CustomerUrlFilter.java 
* Created on 2011-11-29 上午11:10:19 
*/

package com.wcs.demo.test;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* <p>Project: btcbase</p> 
* <p>Title: CustomerUrlFilter.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class CustomerUrlFilter extends PathMatchingFilter {
    private static final Logger log = LoggerFactory.getLogger(CustomerUrlFilter.class);

    public CustomerUrlFilter() {
        log.info("Url Filter start.");
    }
}
