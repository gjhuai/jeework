package com.wcs.common.service.permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.wcs.base.service.StatelessEntityService;
import com.wcs.base.util.ResourcesNode;
import com.wcs.common.model.Resource;
import com.wcs.common.model.Role;
import com.wcs.common.model.RoleResource;
import com.wcs.common.util.LazyModelUtil;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: ResourceService.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Stateless
public class ResourceService implements Serializable{
		
	public ResourceService(){}

	@Inject
    StatelessEntityService entityService;
	
	public List<Resource> findAllSysResource(){
	    String sql = "SELECT rs FROM Resource rs  ORDER BY rs.number";
        return this.entityService.findList(sql);
	}
	
	/**
	 * 
	 * <p>Description: 根据菜单Id得到孩子菜单</p>
	 * @param parentid
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
    public List<Resource> getChildListById(Long parentid){
		try{
			Query query = entityService.createQuery("from Resource r where r.parentId = ?");
			query.setParameter(1, parentid);
			return (List<Resource>)query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	 /**
	  * 
	  * <p>Description: 得到所有资源</p>
	  * @return
	  */
	
	@SuppressWarnings("unchecked")
    public List<Resource> getAllTree(){
		try{
			return (List<Resource>)entityService.createQuery("from Resource").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * <p>Description:得到父级资源集合 </p>
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
    public List<Resource> getFatherTree(){
	    try{
	         String sql = "from Resource t where t.parentId is null";
	         return (List<Resource>)entityService.createQuery(sql).getResultList();
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	
	 /**
	  * 
	  * <p>Description: 通过ID加载资源</p>
	  * @param id
	  * @return
	  */
	
	public Resource loadTree(Long id){
		try{
			
			return entityService.findUnique(Resource.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * <p>Description: 得到选中节点资源对象集合</p>
	 * @param selectedNodes
	 * @return
	 */
	
	public List<Resource> getSelectResource(TreeNode[] selectedNodes){
		List<Resource> list = new ArrayList<Resource>();
		if(selectedNodes != null){
			int size = selectedNodes.length;
			if(size != 0){
				for(int i = 0 ; i < size ; i++){
					 ResourcesNode node = (ResourcesNode) selectedNodes[i];
					 Resource rs = this.entityService.findUnique(Resource.class,node.getId());
					 list.add(rs);
				}
				return list;
			}else{
				return list;
			}
		}else{
			return list;
		}	
	}
	
	/**
	 * 
	 * <p>Description: 查询角色的资源中间表</p>
	 * @param role
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
    public List<RoleResource> getResourceListByRole(Role role){
		try{
			String sql = "from RoleResource rs where rs.role= ?1 ";
			Query query = entityService.createQuery(sql);
			query.setParameter(1, role);
			return (List<RoleResource>)query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * <p>Description: 查询角色的资源</p>
	 * @param role
	 * @return
	 * @throws Exception
	 */
	
	public List<Resource> findResouceByRole(Role role) throws Exception{
		List<RoleResource> roleResourceList = this.getResourceListByRole(role);
		List<Resource> resourceList = new ArrayList<Resource>();
		if(!roleResourceList.isEmpty()){
			try{
				for(RoleResource roleResource :roleResourceList){
					resourceList.add(roleResource.getResource());
				}
			}catch(Exception e){
				throw e;
			}
			
		}
		return resourceList;
	}
	
	/**
	 * 
	 * <p>Description: 删除角色资源</p>
	 * @param role
	 */
	
	public void deleteRoleResource(Role role){
		String sql = "delete RoleResource rs where rs.role.id=?1";
		this.entityService.batchExecute(sql, role.getId());
	}
	
	/**
	 * 
	 * <p>Description: 根据id查找资源</p>
	 * @param sysResouce
	 * @param compoent
	 * @return
	 * @throws Exception
	 */
	public Resource findResourceByCompenet(List<Resource> sysResouce,String compoent,String currentUrl) throws Exception{
		if(currentUrl == null ||  "".equals(currentUrl)){
			return null;
		}
		if(compoent!=null&&! "".equals(compoent) && sysResouce != null){
			
			for(Resource rs :sysResouce){
				if(currentUrl.equals(rs.getUrl())){
					compoent = rs.getNumber()+compoent;
				}
			}
			for(Resource rs :sysResouce){
					if(compoent.equals(rs.getNumber())){
						return  rs;
					}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * <p>Description: 根据Uri查找资源菜单 针对菜单级别</p>
	 * @param sysResouce
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Resource findResourceByUrl(List<Resource> sysResouce,String url) throws Exception{
		if(url != null && url.length() != 0 && sysResouce != null){
			for(Resource r  : sysResouce){
				if(url.equals(r.getUrl())){
					return r;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * <p>Description: 查询菜单资源下的组件资源</p>
	 * @param sysResouce
	 * @param rs
	 * @return
	 */
    public List<Resource> findMenuComponentResouce(List<Resource> sysResouce,Resource rs) throws Exception{
    	
    	List<Resource> compoentResource = new ArrayList<Resource>();
    	if(rs != null){
    		for(Resource r : sysResouce){
    			if(r.getParentId().equals(rs.getId())){
    				compoentResource.add(r);
    			}
    		}
    	}
		return compoentResource;
	}
    
    /**
     * 
     * <p>Description:查询当前用户所拥有的组件资源 </p>
     * @param allUserResource
     * @param compoentResouce
     * @return
     */
    public List<Resource> findUserCompoentResource(List<Resource> allUserResource,List<Resource> compoentResouce){
    	List<Resource> userCompoentList = new ArrayList<Resource>();
    	if(!compoentResouce.isEmpty()){
    		for(Resource r:compoentResouce){
    			
    				if(allUserResource.contains(r)){
    					userCompoentList.add(r);
    				}
    			
    		}
    	}
    	return userCompoentList;
    }
    
    /**
     * 查找所有资源
     * @return
     */
    public LazyDataModel<Resource> searchAllResource() {
        String sql = "SELECT r FROM Resource r WHERE r.id > 1";
        Query query = this.entityService.createQuery(sql);
        List<?> queryList = query.getResultList();
        List<Resource> rsList = getResourceList(queryList);
        
        // 转换成LazyModel
        LazyDataModel<Resource> lazyMode = LazyModelUtil.getLazyResourceDataModel(rsList);
        
        return lazyMode;
    }
    
    /**
     * 根据条件查询资源
     * @param searchResourceName
     * @return
     */
    public LazyDataModel<Resource> searchResourceByName(String searchResourceName) {
        List<Resource> rsList = findResourceByName(searchResourceName);
        
        // 转换成LazyModel
        LazyDataModel<Resource> lazyMode = LazyModelUtil.getLazyResourceDataModel(rsList);
        
        return lazyMode;
    }

    /**
     * 根据资源名查找资源列表
     * @param searchResourceName
     * @return
     */
    private List<Resource> findResourceByName(String searchResourceName) {
        String sql = "SELECT r FROM Resource r WHERE r.id > 1 and r.name LIKE :name";
        Query query = this.entityService.createQuery(sql);
        query.setParameter("name", "%" + searchResourceName + "%");
        List<?> queryList = query.getResultList();
        
        return getResourceList(queryList);
    }

    /**
     * 无参查找资源列表
     * @return
     */
    public List<Resource> findAllResource() {
       String sql = "SELECT r FROM Resource r";
       Query query = this.entityService.createQuery(sql);
       List<?> queryList = query.getResultList();
       
       return getResourceList(queryList);
    }
    
    

    private List<Resource> getResourceList(List<?> queryList) {
        List<Resource> resourceList = new ArrayList<Resource>();
        for (int i = 0; i < queryList.size(); i++) {
            Resource resource = (Resource) queryList.get(i);
            // 是否有子菜单
            if (resource.getIsLeaf()) {
                resource.setIsLeafLang("无");
            } else {
                resource.setIsLeafLang("有");
            }
            
            // 是否为菜单
            if (resource.getIsmenu()) {
                resource.setIsMenuLang("是");
            } else {
                resource.setIsMenuLang("否");
            }
            
            // 上级菜单名
            String parentName = this.findParentName(resource);
            if (parentName != null) {
                resource.setParentName(parentName);
            } 
            
            resourceList.add(resource);
        }
        
        return resourceList;
    }

    /**
     * 根据parentId查找上级菜单名
     * @param resource
     * @return
     */
    private String findParentName(Resource resource) {
        String sql = "SELECT r FROM Resource r WHERE r.id = :id";
        Query query = this.entityService.createQuery(sql);
        query.setParameter("id", resource.getParentId());
        List<?> resourceList =  query.getResultList();
        if (resourceList.isEmpty()) {
            return null;
        }
        
        // 得到上级菜单名
        resource = (Resource) resourceList.get(0);
        String parentName = resource.getName();
        
        return parentName;
    }

    public void deleteRolePermission(Role role) {
        String sql = "delete Permission p where p.role.id=?1";
        this.entityService.batchExecute(sql, role.getId());
    }

    /**
     * 删除当前选中资源
     * @param currentResource
     */
    public void deleteResource(Resource res) {
        String sql = "DELETE FROM Resource r WHERE r.id=?1";
        this.entityService.batchExecute(sql, res.getId());
    }

    /**
     * 修改当前选中资源
     * @param currentResource
     */
    public void modResource(Resource res) {
        this.entityService.update(res);
    }

    /**
     * 更新当前资源有子菜单:isLeaf:0
     * @param selectedResource
     */
    public void updateCurrentResource(Resource selectedResource) {
        String sql = "Update Resource SET IS_LEAF= :IsLeaf WHERE id= :id";
        Query query = this.entityService.createQuery(sql);
        if (selectedResource.getIsLeaf()) {
            query.setParameter("IsLeaf", 1);
        } else {
            query.setParameter("IsLeaf", 0);
        }
        query.setParameter("id", selectedResource.getId());
        query.executeUpdate();
    }

    /**
     * 判断关键字是否唯一
     * @param keyName
     * @return
     */
    public Boolean judgeKeyNameUnique(String keyName) {
        String sql = "SELECT COUNT(*) FROM Resource r WHERE r.keyName = :keyName";
        Query query = this.entityService.createQuery(sql);
        query.setParameter("keyName", keyName);
        Long count = (Long) query.getSingleResult();
        if (count > 0) {
            return false;
        }
        
        return true;
    }

    /**
     * 检查更新前资源上级菜单是否应该为叶子节点
     * @param parentId
     * @return
     */
    public Boolean checkCurrentResIsLeaf(Long parentId) {
        String sql = "SELECT COUNT(*) FROM Resource r WHERE r.parentId = :parentId";
        Query query = this.entityService.createQuery(sql);
        query.setParameter("parentId", parentId);
        Long count = (Long) query.getSingleResult();
        if (count > 0) {
            return false;
        }
        
        return true;
    }

    /**
     * 判断keyName对象是否为同一资源
     * @param keyName
     * @return
     */
    public Resource findResourceByKeyName(String keyName) {
        String sql = "SELECT r FROM Resource r WHERE r.keyName = :keyName";
        Query query = entityService.createQuery(sql);
        query.setParameter("keyName", keyName);
        Resource resource = (Resource) query.getSingleResult();
        
        return resource;
    }
}
