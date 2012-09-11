package com.wcs.commons.security.service;

import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.wcs.base.service.EntityReader;
import com.wcs.base.service.EntityWriter;
import com.wcs.base.util.CollectionUtils;
import com.wcs.base.util.Validate;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.User;

/**
 * 
 * @author Chris Guan
 */
@Stateless
public class RoleService {

	@EJB(beanName="EntityReader")
	private EntityReader entityReader;
	
	@EJB
	private EntityWriter entityWriter;


    /**
     * 根据用户得到当前用户角色
     */
    public List<Role> findAllRoles() {
    	return entityReader.findList("SELECT r FROM Role r WHERE r.defunctInd='N'");
    }
    
    
	/**
	 * 得到用户的角色列表
	 */
	public List<Role> findRoles(User user) {
		return entityReader.findList("SELECT r FROM User u JOIN u.roleList r WHERE r.defunctInd='N' AND u.id=?1", user.getId());
	}
	
	public void updateRole(Role role)  {
		entityWriter.update(role);
	}

	public void deleteRole(Role role){
		role.setDefunctInd('Y');
		entityWriter.update(role);
	}

	public void activateRole(Role role){
		role.setDefunctInd('Y');
		entityWriter.update(role);
	}
	
	public void createRole(Role role){
		entityWriter.create(role);
	}
	
    /**
     * 给用户分配新的 Role 列表
     * 
     * 1.获取用户当前拥有的 Role List
     * 2.删除原有的Role中，不在新的Role列表中的Role
     * 3.添加新增的Role
     * 
     * @param roleList 新分配的 Role 列表
     */
    public void allocRoles(User user, List<Role> roleList) {
    	Validate.isTrue(user!=null);
    	User u = entityReader.findUnique(User.class, user.getId());  // 得到持久化的User
    	List<Role> userRoles = u.getRoleList();
    	
    	// 删除未被选中的Role
    	ListIterator<Role> roles = userRoles.listIterator();
		while(roles.hasNext()) {
			Role r = roles.next();
    		if (!roleList.contains(r)){
    			roles.remove();
    		}
		}
    	
    	// 剔除已有的角色
    	List<Role> appendedRoles = (List<Role>) CollectionUtils.subtract(roleList, userRoles);
    	// 给User添加新的Role（这个appendedRoles是非持久化的，保存（更新）可能会出错）
    	userRoles.addAll(appendedRoles);
    	
    	entityWriter.update(u);
    }

}
