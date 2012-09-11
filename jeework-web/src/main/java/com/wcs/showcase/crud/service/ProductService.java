/** * ProductService.java 
* Created on 2011-11-10 下午3:29:27 
*/

package com.wcs.showcase.crud.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.LazyDataModel;

import com.wcs.base.service.PagingEntityReader;
import com.wcs.showcase.crud.model.Product;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ProductService.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class ProductService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	public PagingEntityReader reader;
	
    /**
     * 查询所有的商品信息
     * @return List<Product>
     */
    public List<Product> search() {
    	List<Product> list = reader.findList("SELECT p FROM Product p");
    	
		return  list;	
	}	

    /**
     * 动态分页， XSQL 查询 （推荐使用）
     * @param filterMap
     */
	public LazyDataModel<Product> findModelByMap(Map<String, Object> filterMap) {
		String hql = "SELECT p FROM Product p WHERE p.defunctInd = false";
		StringBuilder xsql =  new StringBuilder(hql);
	    xsql.append(" /~ and p.name like {name} ~/ ")
	        .append(" /~ and p.category like {category} ~/ ")
	        .append(" /~ and p.price >= {smallPrice} ~/")
	        .append(" /~ and p.price <= {bigPrice} ~/");
	    
	    return reader.findXqlPage(xsql.toString(), filterMap);
	}

}
