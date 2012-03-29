/** * ReportFileService.java 
* Created on 2011-12-6 下午1:49:25 
*/

package com.wcs.report.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;

import com.wcs.base.LazyDataModelBase;
import com.wcs.base.exception.ServiceException;
import com.wcs.base.message.ExceptionMessage;
import com.wcs.base.service.StatelessEntityService;
import com.wcs.common.model.Resource;
import com.wcs.report.model.ReportFile;
import com.wcs.report.model.ReportMstr;
import com.wcs.report.util.FileUtil;

/**
 * 
 * <p>Project: btcbase</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:chenlong@wcs-global.com">Chen Long</a>
 */
@Stateless
public class ReportFileService {

    /** 文件类型 */
    private static final String FILE_TYPE = "jrxml,jasper";
    /** 文件大小为5M */
    private final Long FILE_SIZE = 5 * 1024 * 1024L;

    public ReportFileService() {

    }

    @Inject
    private StatelessEntityService entityService;

    /**
     * 
     * <p>Description:根据报表主数据ID查询报表文件集合 </p>
     * @param mstrId
     * @return
     * @throws AccessException
     */
    public List<ReportFile> findRepFileByMsId(Long mstrId) throws ServiceException {
        try {
            String sql = "select rpf from ReportFile rpf where rpf.reportMstr.id=? and rpf.defunctInd=false";
            Query query = this.entityService.createQuery(sql);
            query.setParameter(1, mstrId);
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiceException(ExceptionMessage.RPTFILE_MSTRID, e);
        }
    }

    /**
     * 
     * <p>Description:根据报表主数据Id查询主数据对象 </p>
     * @param mstrId
     * @return
     * @throws AccessException
     */
    public ReportMstr findRepMstrById(Long mstrId) throws ServiceException {
        try {
            return this.entityService.findUnique(ReportMstr.class, mstrId);
        } catch (Exception e) {
            throw new ServiceException(ExceptionMessage.RPTFILE_MSTRID, e);
        }
    }

    /**
     * 
     * <p>Description: 根据主数据Id得到最大记录数</p>
     * @param mstrId
     * @return
     * @throws ServiceException
     */
    public int findReptFileNumber(Long mstrId) throws ServiceException {
        try {
            String sql = "select rpf from ReportFile rpf where rpf.reportMstr.id=? and rpf.defunctInd=false";
            Query query = this.entityService.createQuery(sql);
            query.setParameter(1, mstrId);
            return query.getResultList().size();
        } catch (Exception e) {
            throw new ServiceException(ExceptionMessage.RPTFILE_MSTRID, e);
        }
    }

    /**
     * 
     * <p>Description: 上传保存文件</p>
     * @param rptFile
     * @param input
     * @param targertFile
     * @throws ServiceException
     */
    public void saveRptFile(ReportFile rptFile, InputStream input, File targertFile) throws ServiceException {
        try {
            FileUtil.readFile(input, targertFile);
            this.entityService.create(rptFile);
            if (rptFile.getUseInd()) {
                this.updateUseInd(rptFile.getId(),rptFile.getReportMstr().getId());
            }
        } catch (Exception e) {
            throw new ServiceException(ExceptionMessage.RPTFILE_SAVE, e);
        }
    }

    /**
     * 
     * <p>Description: 更新报表模板文件</p>
     * @param rptFile
     */
    public void updateRptFile(ReportFile rptFile,Long mastrId) throws ServiceException {
        try {
            rptFile.setUseInd(true);
            this.entityService.update(rptFile);
            this.updateUseInd(rptFile.getId(), mastrId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 
     * <p>Description: 批量更新报表模板使用状态为不使用</p>
     * @param id
     * @throws ServiceException
     */
    public void updateUseInd(Long id,Long mastrId) throws ServiceException {
        try {
            String sql = "update ReportFile rpf set rpf.useInd=false where rpf.id !=?   and rpf.reportMstr.id=? and rpf.defunctInd=false ";
            this.entityService.batchExecute(sql, id,mastrId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 
     * <p>
     * Description: 是否可以上传文件
     * </p>
     * 
     * @param fileType
     * @param fileSize
     * @return
     * @throws Exception
     */
    public boolean isUpload(String fileType, Long fileSize) throws Exception {
        boolean flag = false;
        if (fileType == null && "".equals(fileType)) { return flag; }
        if (fileSize == 0) { return flag; }
        Long size = sysFileType().get(fileType.toLowerCase());
        if (size == null) {
            flag = false;
            return flag;
        }
        if (fileSize != 0 && fileSize <= size) {
            flag = true;
        }
        return flag;
    }
    
    /**
     * 
     * <p>Description: </p>
     * @param mstrId
     * @return
     * @throws ServiceException
     */
    public LazyDataModel<ReportFile> findRptFileDataModel(Long mstrId) throws ServiceException {
        try {
            List<ReportFile> rsList = this.findRepFileByMsId(mstrId);
            LazyDataModel<ReportFile> lazyMode = new LazyDataModelBase<ReportFile>(rsList);
            return lazyMode;
        } catch (ServiceException e) {
            throw e;
        }

    }

    /**
     * 
     * <p>
     * Description: 文件类型Map
     * </p>
     * 
     * @return
     * @throws Exception
     */
    private Map<String, Long> sysFileType() throws Exception {
        String[] file = FILE_TYPE.split(",");
        int size = file.length;
        Map<String, Long> map = new HashMap<String, Long>();
        for (int i = 0; i < size; i++) {
            map.put(file[i], FILE_SIZE);
        }
        return map;
    }
    
    /**
	 * 获得报表文件
	 * @param reportMstrId
	 * @return
	 */
	public ReportFile getReportFile(Long reportMstrId) {
		String hql = "select rf from ReportFile rf where rf.useInd = true and  rf.reportMstr.id = ?1";
		Query query = entityService.createQuery(hql);
		query.setParameter(1, reportMstrId);
		List<ReportFile> list = query.getResultList();
		if(list != null && list.size() > 0) {
			ReportFile reportFile = list.get(0);
			return reportFile;
		}
		
		return null;		
	}
}
