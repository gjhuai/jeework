/** * LazyResourceDataModel.java 
* Created on 2011-12-9 下午3:43:37 
*/

package com.wcs.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import com.wcs.base.entity.BaseEntity;

/** 
* <p>Project: btcbase</p> 
* <p>Title: LazyResourceDataModel.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/

@SuppressWarnings("serial")
public class LazyDataModelBase<T extends BaseEntity> extends LazyDataModel<T> implements SelectableDataModel<T> {
    private List<T> datasource;

    public LazyDataModelBase(List<T> datasource) {
        this.datasource = datasource;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getRowData(String rowKey) {
        List<T> types = (List<T>) getWrappedData();  
        for (T type : types) {
            if (type.getId().equals(rowKey)) { 
                return type; 
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(T type) {
        return type.getId();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<T> data = new ArrayList<T>();

        // filter
        for (T type : datasource) {
            boolean match = true;

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                try {
                    String filterProperty = it.next();
                    String filterValue = filters.get(filterProperty);
                    String fieldValue = String.valueOf(type.getClass().getField(filterProperty).get(type));

                    if (filterValue == null || fieldValue.startsWith(filterValue)) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                } catch (Exception e) {
                    match = false;
                }
            }

            if (match) {
                data.add(type);
            }
        }
        // rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        // paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
