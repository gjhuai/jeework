/** * MathImpl.java 
* Created on 2011-11-9 下午1:47:24 
*/

package com.wcs.showcase.test;

/** 
* <p>Project: btcbase</p> 
* <p>Title: MathImpl.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2005-2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class MathImpl implements MyMath {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }

}
