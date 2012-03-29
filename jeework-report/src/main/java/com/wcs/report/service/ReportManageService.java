/** * ReportManageService.java 
* Created on 2011-12-6 下午1:48:45 
*/

package com.wcs.report.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;

import com.wcs.base.dict.model.Dict;
import com.wcs.base.service.StatelessEntityService;
import com.wcs.common.model.Role;
import com.wcs.report.model.ReportMstr;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportManageService.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class ReportManageService {
	@Inject
	public StatelessEntityService entityService;
	
	 /**
     * 查询所有的角色
     * @return
     */
    public List<Role> findAllRoles() {
    	String jpql = "select r from Role r order by r.roleName asc";
    	List<Role> rolesList = entityService.findList(jpql);
    	return rolesList;
    }
    
    /**
     * 根据角色id查询角色
     * @param id
     * @return String
     */
    public Role findRole(Long roleId) {
    	Role role = entityService.findUnique(Role.class, roleId);
    	return role;
    }
    
	
	 /**
     * 动态分页， XSQL 查询 （推荐使用）
     * @param filterMap
     * @return LazyDataModel<ReportMstr>
     */
	public LazyDataModel<ReportMstr> findModelByMap(Map<String, Object> filterMap) {
		String hql = "select rm from ReportMstr rm where rm.defunctInd = false";
		StringBuilder xsql =  new StringBuilder(hql);
	    xsql.append(" /~ and rm.reportCode like {reportCode} ~/ ")
	        .append(" /~ and rm.reportName like {reportName} ~/ ")
	        .append(" /~ and rm.reportMode = {reportMode} ~/")
	        .append(" /~ and rm.reportCategory = {reportCategory} ~/");
	    
	    return entityService.findPageByFilter(xsql.toString(), filterMap);
	}
	
	/**
	 * 查询报表分类
	 * @return
	 */
	public List<Dict> getReportCategory(String parentCode) {
		String hql = "select d from Dict d where d.defunctInd = false and d.parentCode = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, parentCode);
		List<Dict> list = query.getResultList();
		
		return list;
	}
	
	/**
	 * 根据报表分类查询报表列表
	 * @param category
	 * @return
	 */
	public List<ReportMstr> getReportByCategory(String category) {
		String hql = "select rm from ReportMstr rm where rm.defunctInd = false and rm.reportCategory = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, category);
		List<ReportMstr> list = query.getResultList();
		
		return list;
	}
	
	
	//-------------------- setter & getter --------------------//
	
	public void setEntityService(StatelessEntityService entityService) {
		this.entityService = entityService;
	}

}
