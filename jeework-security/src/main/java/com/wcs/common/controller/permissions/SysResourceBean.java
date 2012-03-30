package com.wcs.common.controller.permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wcs.common.model.Resource;
import com.wcs.common.service.permissions.ResourceService;

/**
 * 
 * <p>Project: cmdpms</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yourname@wcs-global.com">Your Name</a>
 */

@Named
@ApplicationScoped
//@Singleton
public class SysResourceBean implements Serializable {
    private static final long serialVersionUID = 1L;
    final Logger logger = LoggerFactory.getLogger(LoginBean.class);

    @EJB
    private ResourceService resourceService;

    /** 系统的全部资源 */
    private List<List<Resource>> allResList = Lists.newArrayList();

    public SysResourceBean() {}

    @PostConstruct
    private void initAllResources() {
        logger.info("ResourceBean => initAllResources()");
        List<Resource> resList = resourceService.findAllSysResource();        
        Map<String,List<Resource>> allResMap = Maps.newHashMap();
        
        for (Resource r : resList){
        	String level = "level"+r.getLevel();
        	
        	List<Resource> list = allResMap.get(level);
        	
        	if (CollectionUtils.isEmpty(list)){
        		allResMap.put(level, new ArrayList<Resource>());
        	}
        	
        	allResMap.get(level).add(r);
        }
        
        for (int i=0;i<allResMap.size();i++){
        	allResList.add(allResMap.get("level"+i));
        }
    }

    //--------------------------------------------setter & getter---------------------------------------------------//

    public List<List<Resource>> getAllResList() {
        return allResList;
    }

    public void setAllResList(List<List<Resource>> allResList) {
        this.allResList = allResList;
    }

}
