/** * ReportRoleService.java 
* Created on 2011-12-6 下午1:49:41 
*/

package com.wcs.report.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.wcs.base.service.StatelessEntityService;
import com.wcs.report.model.ReportRole;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportRoleService.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class ReportRoleService {
	@Inject
	public StatelessEntityService entityService;
	
	/**
	 * 根据reportMstrId获得查看该报表所具有的角色(包括删除的)
	 * @param reportMstrId
	 * @return List<ReportRole>
	 */
	public List<ReportRole> findRoleList(Long reportMstrId) {
		String hql = "select rr from ReportRole rr where  rr.reportMstr.id = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, reportMstrId);
		List<ReportRole> list = query.getResultList();
		
		return list;
	}
	
	/**
	 * 根据reportMstrId获得查看该报表所具有的角色(不包括删除的)
	 * @param reportMstrId
	 * @return List<ReportRole>
	 */
	public List<ReportRole> getReportRoleList(Long reportMstrId) {
		String hql = "select rr from ReportRole rr where rr.defunctInd = false and rr.reportMstr.id = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, reportMstrId);
		List<ReportRole> list = query.getResultList();
		
		return list;
	}
	
	
	//-------------------- setter & getter --------------------//
	
	public StatelessEntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(StatelessEntityService entityService) {
		this.entityService = entityService;
	}

}
