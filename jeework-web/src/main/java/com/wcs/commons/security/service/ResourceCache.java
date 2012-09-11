package com.wcs.commons.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.wcs.base.service.EntityReader;
import com.wcs.base.util.CollectionUtils;
import com.wcs.commons.security.model.Resource;
import com.wcs.commons.security.vo.ResourceNode;

/**
 * 
 * @author Chris Guan
 */
@Singleton
@Startup
@Lock(LockType.READ)
public class ResourceCache {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<Resource> cache = new ArrayList<Resource>();
	
	@EJB(beanName="EntityReader")
	private EntityReader entityReader;
	
	@PostConstruct
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void initResourceCache() {
		String sql = "SELECT r FROM Resource r";
		cache = this.entityReader.findList(sql);
	}
	
	public void buildTree(ResourceNode root){
		this.buildTree(this.loadSubResources(0L), root);	// 第一级Resource的parentId默认为0L 
	}
	/**
	 * 用来维护Resource，采用递归方式实现
	 * @param subResList 给定的父节点的儿子资源列表
	 * @param parentNode 父节点
	 */
	private void buildTree(List<Resource> subResList,ResourceNode parentNode){
        for (Resource r : subResList){        	
        	// 生成 elementId (将 ':' 替换成 '_')
        	r.setElementId( r.getCode().replaceAll(":", "_") );
        	ResourceNode node = new ResourceNode(r, parentNode);
            List<Resource> subList = this.loadSubResources(r.getId());
            //logger.debug("buildTree:: parentId={}, subList.size={}", r.getId(), subList.size());
            if (CollectionUtils.isNotEmpty(subList))
                buildTree(subList,node);
        }
    }
	
	/**
	 * 装载系统的资源
	 */
	public List<Resource> loadAllResources() {
		return cache;
	}
	
	public Resource loadResource(String code){
		for (Resource r : cache){
			if (r.getCode().equals(code)){
				return r;
			}
		}
		return null;
	}
	
 
	/**
	 * 得到某一个父Id parentId 下的资源列表
	 * @param parentId
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Resource> loadSubResources(Long parentId){
        List<Resource> list = Lists.newArrayList();
        for (Resource r : cache){
            if (parentId.equals(r.getParentId())){
                list.add(r);
            }
        }
        return list;
    }
    

    public List<Resource> findBrotherNode(Resource resource){
        return entityReader.findList("SELECT r FROM Resource r WHERE r.parentId=?1 ",resource.getParentId());
    }
    /**
     * check 同级中的其他节点的seqNo，是否有与给定Resouce相同的
     * @param resource 原有节点
     * @return
     */
    public boolean isUniqueSeqNo(Resource resource){
        // 获取同级节点
        List<Resource> brotherResList = this.findBrotherNode(resource);
        // check 同级中是否有相同的seqNo
        for (Resource r : brotherResList){
            // 和原seqNo不相同，且此节点不是本原点
            if (r.getSeqNo().equals(resource.getSeqNo()) && !r.getId().equals(resource.getId())){
            	logger.debug("节点SeqNo不唯一");
                return false;
            }
        }
        return true;
    }

    public boolean isUniqueCode(Resource resource){
        for (Resource r : cache){
            if (r.getCode().equals(resource.getCode()) && !r.getId().equals(resource.getId())){
                return false;
            }
        }
        return true;
    }
    
}
