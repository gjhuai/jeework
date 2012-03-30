package com.wcs.common.service.permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.wcs.base.service.StatelessEntityService;
import org.primefaces.model.TreeNode;

import com.google.common.collect.Lists;
import com.wcs.base.util.ResourcesNode;
import com.wcs.common.model.Resource;
import com.wcs.common.model.Role;
import com.wcs.common.model.UserRole;

/**
 * 
 * <p>Project: cmdpms</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yourname@wcs-global.com">Your Name</a>
 */
@Stateless
public class RoleService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    StatelessEntityService entityService;
    @Inject
    ResourceService resourceService;
  
    public RoleService() {
        System.out.println("start roleservice....");
    }

    /*
     * 返回角色列表
     * 
     * @return role list
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoleList() {
        Query query = entityService.createQuery("from Role r order by roleName asc");
        return (List<Role>) query.getResultList();
    }

    /**
     * 
     * <p>
     * Description:得到最顶层资源
     * </p>
     * 
     * @return
     */
    public List<Resource> getSysTopResource() {
        String sql = "SELECT r FROM Resource r WHERE r.parentId = 0";
        List<Resource> list = this.entityService.findList(sql);
        System.out.println(list);
        return list;
    }

    /**
     * 
     * <p>
     * Description: 角色列表分页
     * </p>
     * 
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filtermap
     * @return
     */
    public List<Role> getResultByPage(int first, int pageSize, String sortField, boolean sortOrder,
            Map<String, String> filtermap) {
        try {
            String sql = "from Role  role where role.state=1 ";
            Query query = this.entityService.createQuery(sql);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return (List<Role>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * <p>
     * Description:得到角色总记录
     * </p>
     * 
     * @return
     */

    public int getRowCount() {
        try {
            return this.entityService.findAll(Role.class).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     * <p>
     * Description: 根据角色名查询匹配的角色列表
     * </p>
     * 
     * @param rolename
     * @return
     */

    public List<String> searchRole(String rolename) {
        List<String> rnamelist = Lists.newArrayList();
        try {
            Query query = entityService.createQuery("from Role r where r.roleName like :roleNames order by roleName asc");
            query.setParameter("roleNames", "%" + rolename + "%");
            List<Role> rlist = query.getResultList();
            for (Role r : rlist) {
                rnamelist.add(r.getRoleName());
            }
            return rnamelist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rnamelist;
    }

    /**
     * 
     * <p>
     * Description: 根据角色查询角色对象
     * </p>
     * 
     * @param name
     * @return
     */

    public Role getRoleByName(String name) {
        try {
            String sql = "from Role r where r.roleName = ?1";
            Query query = this.entityService.createQuery(sql);
            query.setParameter(1, name);
            List st = query.getResultList();
            return (Role) st.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * <p>
     * Description: 修改角色信息
     * </p>
     * 
     * @param role
     * @throws Exception 
     */

    public void updateRole(Role role) throws Exception {
        try {
            entityService.update(role);
        } catch (Exception e) {
           throw e;
        }
    }
    
    /**
     * 
     * <p>Description: 删除角色</p>
     * @param role
     */
    public void deleteRole(Role role) throws Exception{
        resourceService.deleteRoleResource(role);
        String sql1 = "delete from UserRole ur where ur.role.id=?";
        String sql2 = "delete from Role r where r.id=?";
        this.entityService.batchExecute(sql1, role.getId());
        this.entityService.batchExecute(sql2, role.getId());
    }
    
    /**
     * 
     * <p>
     * Description: 根据角色Id数组得到角色集合列表
     * </p>
     * 
     * @param roleId
     * @return
     */

    public List<Role> getSelectRoleById(Long[] roleId) throws Exception {
        List<Role> list = Lists.newArrayList();
        if (roleId == null || roleId.length == 0) {
            return list;
        }
        int length = roleId.length;
        for (int i = 0; i < length; i++) {
            list.add(this.entityService.findUnique(Role.class, roleId[i]));
        }
        return list;
    }

    /**
     * 
     * <p>
     * Description: 根据角色id和用户Id查询UserRole
     * </p>
     * 
     * @param userId
     * @param roleId
     * @return
     */

    public UserRole findByURId(Long userId, Long roleId) throws Exception {
        if ((userId != null && userId != 0) && (roleId != null && roleId != 0)) {
            return null;
        }
        String sql = "from UserRole ur where  ur.user.id=?1 and ur.role.id=?2";
        Query query = this.entityService.createQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, roleId);
        List list = query.getResultList();
        if (list.isEmpty() && list.size() > 1) {
            return null;
        }
        return (UserRole) list.get(0);
    }

    /**
     * 
     * <p>
     * Description: 删除用户角色
     * </p>
     * 
     * @param userId
     * @throws Exception
     */

    public void deleteUserRole(Long userId) throws Exception {
        if (userId == 0) {
            return;
        }
        String sql = "from UserRole ur where  ur.user.id=?1 ";
        Query query = this.entityService.createQuery(sql);
        query.setParameter(1, userId);
        List<UserRole> userRoleList = query.getResultList();
        if (userRoleList.isEmpty()) {
            return;
        }
        for (UserRole userRole : userRoleList) {
            this.entityService.delete(userRole);
        }

    }

    /**
     * 
     * <p>
     * Description: 初始化父级资源
     * </p>
     */
    public void initFatherNode(TreeNode root, List<Resource> sysResource, Map<Long, Resource> map) {
        List<Resource> flist = this.findTopResource(sysResource);
        for (Resource father : flist) {
            ResourcesNode fnode = new ResourcesNode(father.getName(), root);
            if (map.get(father.getId()) != null) {
                fnode.setSelected(true);
            }
            fnode.setId(father.getId());
            fnode.setUrl(father.getUrl());
            initChildNode(father.getId(), fnode, sysResource, map);
        }
    }

    /**
     * 
     * <p>
     * Description: 初始化下级资源
     * </p>
     * 
     * @param id
     * @param father
     */
    public void initChildNode(Long id, TreeNode father, List<Resource> sysResource, Map<Long, Resource> map) {
        List<Resource> chillist = this.findChildResource(id, sysResource);
        boolean flag = false;
        for (Resource child : chillist) {
            List<Resource> sercondlist = this.findChildResource(child.getId(), sysResource);
            if (!sercondlist.isEmpty()) {
                ResourcesNode childnode = new ResourcesNode(child.getName(), father);
                if (map.get(child.getId()) != null) {
                    childnode.setSelected(true);
                }
                childnode.setId(child.getId());
                childnode.setUrl(child.getUrl());
                initChildNode(child.getId(), childnode, sysResource, map);
            } else {

                ResourcesNode fnode = new ResourcesNode(child.getName(), father);
                if (map.get(child.getId()) != null) {
                    fnode.setSelected(true);
                }
                // fnode.setSelected(flag);
                fnode.setId(child.getId());
                fnode.setUrl(child.getUrl());
            }

        }
    }

    /**
     * 
     * <p>
     * Description: 根据id得到孩子节点
     * </p>
     * 
     * @param id
     * @param sysResource
     * @return
     */
    public List<Resource> findChildResource(Long id, List<Resource> sysResource) {
        List<Resource> childResource = new ArrayList<Resource>();
        for (Resource rs : sysResource) {
            if (id.equals(rs.getParentId())) {
                childResource.add(rs);
            }
        }
        return childResource;
    }

    /**
     * 
     * <p>
     * Description: 得到顶级资源
     * </p>
     * 
     * @param sysResource
     * @return
     */
    public List<Resource> findTopResource(List<Resource> sysResource) {
        List<Resource> topResource = new ArrayList<Resource>();
        for (Resource rs : sysResource) {
            if (rs.getParentId() == null || rs.getParentId() == 0L) {
                topResource.add(rs);
            }
        }
        return topResource;
    }

    /**
     * 
     * <p>
     * Description:
     * </p>
     * 
     * @param sysResource
     * @param role
     * @throws Exception
     */

    public void isSelectedResourceByRole(TreeNode root, List<Resource> sysResource, Role role) throws Exception {
        List<Resource> roleResource = this.resourceService.findResouceByRole(role);
        Map<Long, Resource> map = new HashMap<Long, Resource>();
        if (!roleResource.isEmpty()) {
            for (Resource r : roleResource) {
                map.put(r.getId(), r);
            }

        }
        this.initFatherNode(root, sysResource, map);
    }

 
    /**
     * 查找所有资源
     * @return
     */
    public List<Resource> getAllResource() {
        String sql = "SELECT r FROM Resource r";
        Query query = entityService.createQuery(sql);

        @SuppressWarnings("unchecked")
        List<Resource> resourceList = query.getResultList();

        return resourceList;
    }
    
    /**
     * 根据用户id查询该用户所具有的角色
     * @param userId
     * @return
     */
    public List<UserRole> getUserRoleList(Long userId) {
    	String hql ="select ur from UserRole ur where ur.user.id = ?1";
    	Query query = entityService.createQuery(hql);
    	query.setParameter(1, userId);
    	
    	List<UserRole> list = query.getResultList();
    	
    	return list;
    }
}
