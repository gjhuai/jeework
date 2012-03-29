/** * ReportParameterService.java 
* Created on 2011-12-6 下午1:49:05 
*/

package com.wcs.report.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.wcs.base.service.StatelessEntityService;
import com.wcs.report.model.ReportParameter;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ReportParameterService.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright 2011.All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yangshiyun@wcs-global.com">Yang Shiyun</a> 
*/

@Stateless
public class ReportParameterService {
	@Inject
	public StatelessEntityService entityService;
	
	/**
	 * 根据报表reportMstrId查询报表参数
	 * @param reportMstrId
	 * @return List<ReportParameter>
	 */
	public List<ReportParameter> findReportParameterList(Long reportMstrId) {
		String hql = "select r from ReportParameter r where r.defunctInd = false and r.reportMstr.id = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, reportMstrId);
		List<ReportParameter> list = query.getResultList();
		
		return list;
	}
	
	/**
	 * 根据报表参数reportParameterId查询报表参数
	 * @param reportParameterId
	 * @return
	 */
	public ReportParameter findReportParameter(Long reportParameterId) {
		String hql = "select r from ReportParameter r where r.defunctInd = false and r.id = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, reportParameterId);
		List<ReportParameter> list = query.getResultList();
		if(list != null && list.size() != 0) {
			ReportParameter reportParameter = list.get(0);
			return reportParameter;
		} else {
			return null;
		}
	}
	
	//-------------------- setter & getter --------------------//

	public StatelessEntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(StatelessEntityService entityService) {
		this.entityService = entityService;
	}

}
