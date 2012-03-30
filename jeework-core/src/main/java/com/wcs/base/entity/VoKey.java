/** * RowKeyId.java 
* Created on 2011-12-11 下午12:48:13 
*/

package com.wcs.base.entity;

import java.io.Serializable;

/** 
* <p>Project: btcbase</p> 
* <p>Title: RowKeyId.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

@SuppressWarnings("serial")
public abstract class VoKey implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
