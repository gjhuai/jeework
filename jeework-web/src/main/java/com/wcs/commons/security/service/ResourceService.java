package com.wcs.commons.security.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.exception.TransactionException;
import com.wcs.base.service.EntityReader;
import com.wcs.base.service.EntityWriter;
import com.wcs.base.util.CollectionUtils;
import com.wcs.commons.security.model.Resource;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.RoleResource;

@Stateless
public class ResourceService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@EJB(beanName="EntityReader")
	private EntityReader entityReader;
	
	@EJB
	private EntityWriter entityWriter;
	@EJB
	private ResourceCache resourceCache;
	
	/**
	 * 为给定的role分配资源
	 * @param role  给定某一个角色
	 * @param allocatedResources 选选定的resource
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void allocResources(Role role,List<Resource> allocatedResources){
		role = this.entityReader.findUnique(Role.class, role.getId());
		//原有为A，新的为B；
		
		// 1. delete A-B （删除被取消掉的 Resource）
		List<Resource> oldResList = this.findResources(role);   // 得到原有的 List<Resource>
		for (Resource res : oldResList){
			if (!allocatedResources.contains(res)){
				entityWriter.batchExecute("DELETE FROM RoleResource rr WHERE rr.resource=?", res);
			}
		}
		
		// 2. add B-A（添加新增的 Resource）
		List<Resource> list = (List<Resource>)CollectionUtils.subtract(allocatedResources, oldResList);
		for (Resource r : list){
			RoleResource rr = new RoleResource();
			rr.setRole(role);
			rr.setResource(r);
			rr.setCode(r.getCode());
			entityWriter.create(rr);
			role.getRoleResources().add(rr);
		}
		entityWriter.update(role);
	}
    
	public List<Resource> findResources(Role role){
		return entityReader.findList("SELECT res FROM RoleResource rr JOIN rr.resource res WHERE rr.role.id=?1", role.getId());
	}

	/**
	 * 添加资源，并更新缓存
	 */
	public void addResource(Resource resource){
        // code 唯一判断 (暂不做)

        // seqNo 唯一判断 (暂不做)
		
		this.entityWriter.create(resource);
		resourceCache.initResourceCache();	//更新Resource Cache
	}
	
	/**
	 * 添加资源，并更新缓存
	 */
	public void updateResource(Resource resource){
        // code 唯一判断
        if (!resourceCache.isUniqueCode(resource)){
        	throw new TransactionException("code 不唯一！");
        }
        // seqNo 唯一判断
        if (!resourceCache.isUniqueSeqNo(resource)){
        	throw new TransactionException("seqNo 不唯一！");
        }
        // 修改当前资源
        entityWriter.update(resource);
        resourceCache.initResourceCache();	//更新Resource Cache
	}
	
	/**
	 * 删除当前选中资源
	 */
	public void deleteResource(Resource resource) throws TransactionException{
        // 若为父节点，则不能删除
        if ( CollectionUtils.isNotEmpty(resourceCache.loadSubResources(resource.getId())) ){
            throw new TransactionException("父节点不能删除");
        }

        // 删除节点
        String jpql = "DELETE FROM Resource r WHERE r.id=?1";
		this.entityWriter.batchExecute(jpql, resource.getId());

		// 从缓存中删除这个Resource
		for (Resource r: resourceCache.loadAllResources()){
			if (r.getId().equals(resource.getId())){
				resourceCache.loadAllResources().remove(r);
				break;
			}
		}
	}

	
}
