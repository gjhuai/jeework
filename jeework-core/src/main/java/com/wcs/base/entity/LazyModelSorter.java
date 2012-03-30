/** * LazyModelSorter.java 
* Created on 2011-12-11 下午7:35:43 
*/

package com.wcs.base.entity;

import java.util.Comparator;

import org.apache.poi.hssf.record.formula.functions.T;
import org.primefaces.model.SortOrder;

/** 
* <p>Project: btcbase</p> 
* <p>Title: LazyModelSorter.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

public class LazyModelSorter implements Comparator<T> {
    private String sortField;
    private SortOrder sortOrder;

    @SuppressWarnings("unchecked")
    @Override
    public int compare(T o1, T o2) {
        try {
            Object value1 = T.class.getField(this.sortField).get(o1);
            Object value2 = T.class.getField(this.sortField).get(o2);
            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
