package com.wcs.commons.security.service;

import java.util.List;

import javax.ejb.EJB;

import com.wcs.base.service.EntityReader;
import com.wcs.commons.security.model.Resource;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.RoleResource;
import com.wcs.commons.security.model.User;

/**
 * 
 * @author Chris Guan
 */
public abstract class AbstractUserService {
	
	@EJB(beanName="EntityReader")
	protected EntityReader entityReader;


	/**
	 * 通过用户id 查询唯一用户
	 */
	public User findUser(Long userId) {
		return entityReader.findUnique(User.class, userId);
	}
      
   /**
    * 通过用户名(adAccount)查询唯一用户
    */
    public User findUser(String adAccount) {
        String jpql = "SELECT new com.wcs.commons.security.model.User(u.id, u.adAccount,u.defunctInd,p.id, p.bukrs, p.celno, p.defunctInd,p.email, p.gesch, p.icnum, p.kostl,p.nachn, p.name2, p.orgeh, p.telno)"
            + " FROM User u, Person p"
        	+ " WHERE p.id=u.pernr AND u.defunctInd='N' AND p.defunctInd='N' and u.adAccount = ?1";
        return entityReader.findUnique(jpql, adAccount);
    }
    
   /**
    * 根据用户id 查询所拥有的角色
    */
    public List<Role> findRoles(Long userId) {
        String jpql = "SELECT r FROM User u JOIN u.roleList r WHERE u.id= ?1";
        return entityReader.findList(jpql, userId);
    }
    
    /**
     * 根据用户adAccount 查询所拥有的角色
     */
     public List<Role> findRoles(String adAccount) {
         String jpql = "SELECT r FROM User u JOIN u.roleList r WHERE u.adAccount= ?1";
         return entityReader.findList(jpql, adAccount);
     }

   /**
    * 查询角色列表的所有资源
    */
	public List<Resource> findResources(List<Role> roleList) {
		String jpql = "select res from RoleResource rr join rr.resource res join rr.role role where role in (?1)";
		List<Resource> resourceList = entityReader.findList(jpql, roleList);
		return resourceList;
	}
	
    /**
     * 查找某一 Role 的所有可访问的 Resource
     */
    public List<RoleResource> findResources(Long roleId) {
        String jpql = "SELECT p FROM RoleResource p WHERE p.role.id = ?1";
        List<RoleResource> permissions = entityReader.findList(jpql, roleId);
        return permissions;
    }

}
