package com.wcs.common.service.permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;

import com.google.common.collect.Lists;
import com.wcs.base.service.StatelessEntityService;
import com.wcs.base.util.CollectionUtils;
import com.wcs.common.model.Resource;
import com.wcs.common.model.Role;
import com.wcs.common.model.User;
import com.wcs.common.model.UserRole;
import com.wcs.common.util.LazyModelUtil;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: UserService.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Stateless
public class UserService implements Serializable {
    @Inject
    StatelessEntityService entityService;

    @Inject
    ResourceService resourceService;
    
    public int ttt=1987;

    public UserService() {}

   /**
    * 通过用户名查询唯一用户
    * @param userName
    * @return
    */
    public User findUniqueUser(String loginName) {
        String sql = "select u from User u where u.loginName ='" + loginName + "'";
        List<?> list = entityService.createQuery(sql).getResultList();
        User u = null;
        if (!list.isEmpty()) {
            u = (User) list.get(0);
        }
        return u == null ? null : u;
    }

    /**
     * 查询用户角色
     * @param user
     * @return
     * @throws Exception
     */
    public List<UserRole> getRoleByUser(User user) throws Exception {
        try {
            if (user != null) {
                String sql1 = "from UserRole urole where   urole.user.id='" + user.getId() + "')";
                return this.entityService.findList(sql1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /**
    * 查询所有资源根据角色
    * @param roleList
    * @return
    * @throws Exception
    */
    public List<Resource> findAllResouceOfRoleList(List<Role> roleList) throws Exception {
        List<Resource> distinctResource = new ArrayList<Resource>();
        try {
            if (roleList.isEmpty()) { return distinctResource; }
            String jpql = "select res from RoleResource rr join rr.resource res join rr.role role where role in (?1)";
            List<Resource> resourceList = entityService.findList(jpql, roleList);
            for (Resource resource : resourceList) {
                if (!distinctResource.contains(resource)) {
                    /*if ("3".equals(resource.getLevel())) {
                        if (!distinctResource.contains(resourceService.loadTree(resource.getParentId()))) {
                            distinctResource.add(resourceService.loadTree(resource.getParentId()));
                        }
                    }*/
                    distinctResource.add(resource);
                }
            }
            return distinctResource;
        } catch (Exception e) {
            throw e;
        }
    }

   /**
    * 根据用户查询所拥有的角色
    * @param user
    * @return
    * @throws Exception
    */
    public List<Role> findAllRoleOfUser(User user) throws Exception {
        String jpql = "select r from UserRole ur join ur.user u join ur.role r where r.state=1 and u.id=" + user.getId();
        return entityService.findList(jpql);
    }

   /**
    * 将资源集合转换成ID集合
    * @param resouceList
    * @return
    */
    public Long[] getResouceId(List<Resource> resouceList) {
        try {
            Long[] idArray = null;
            if (!CollectionUtils.isEmpty(resouceList)) {

                int size = resouceList.size();
                idArray = new Long[size];
                for (int i = 0; i < size; i++) {
                    idArray[i] = resouceList.get(i).getId();
                }
            }
            return idArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

  /**
   * 根据用户对象返回角色ID数组
   * @param user
   * @return
   */
    public Long[] getRoleIdByUser(User user) {
        try {
            List<UserRole> userRolelist = getRoleByUser(user);
            if (userRolelist != null) {
                int size = userRolelist.size();
                Long[] l = new Long[size];
                for (int i = 0; i < size; i++) {
                    Role role = userRolelist.get(i).getRole();
                    if (role.getState() == 1) {
                        l[i] = role.getId();
                    }
                }
                return l;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户账户输入匹配
     * @param account
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getUserAccountByInput(String account) {
        try {
            String sql = "from User u where  u.defunctInd=false and u.userName like :account";
            Query query = this.entityService.createQuery(sql);
            query.setParameter("account", "%" + account + "%");
            List<User> ulist = query.getResultList();
            if (ulist.size() != 0) {
                List<String> rlist = Lists.newArrayList();
                for (User user : ulist) {
                    rlist.add(user.getUserName());
                }
                return rlist;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

  /**
   * 根据账户和电子邮件查询User
   * @param account
   * @param emial
   * @return
   */
    public User getUserByEmail(String account, String emial) {
        try {
            String sql = "from User u where u.defunctInd=false and u.userName = :uname and u.email = :Email";
            Query query = this.entityService.createQuery(sql);
            query.setParameter("uname", account);
            query.setParameter("Email", emial);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到所有菜单
     * @return
     */
    public List<Resource> findAllResources() {
        String sql = "SELECT r FROM Resource r WHERE r.ismenu = 1";
        Query query = entityService.createQuery(sql);

        @SuppressWarnings("unchecked")
        List<Resource> resourceList = query.getResultList();

        return resourceList;
    }

    /**
     * 查询用户列表
     * @param loginName
     * @return
     */
    @SuppressWarnings("unchecked")
    public LazyDataModel<User> searchUserByName(String loginName) {
        String sql = "SELECT u FROM User u WHERE u.loginName LIKE :loginName";
        Query query = this.entityService.createQuery(sql);
        query.setParameter("loginName", "%" + loginName + "%");
        List<User> rsList = query.getResultList();
        
        // 转换成LazyModel
        LazyDataModel<User> lazyMode = LazyModelUtil.getLazyUserDataModel(rsList);
        
        return lazyMode;
    }
    
    /**
     * 查找所有用户列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public LazyDataModel<User> findAllUser() {
        String sql = "SELECT u FROM User u";
        Query query = this.entityService.createQuery(sql);
        List<User> rsList = query.getResultList();
        
        // 转换成LazyModel
        LazyDataModel<User> lazyMode = LazyModelUtil.getLazyUserDataModel(rsList);
        
        return lazyMode;
    }

    /**
     * 初始化角色列表
     * @return
     */
    public List<SelectItem> initRoleList() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        String sql = "select r from Role r where r.state =1";
        List<Role> roleList = this.entityService.findList(sql);
        for (Role role : roleList) {
            list.add(new SelectItem(role.getId(), role.getRoleName()));
        }

        return list;
    }

    @SuppressWarnings({ "unused", "null" })
    public Long[] assignUserRole(User user) {
        User currentUser = this.findUniqueUser(user.getLoginName());
        Set<Role> roles = null; // currentUser.getRole();
        Object[] userRole = roles.toArray();
        Long[] uRole = new Long[userRole.length];
        for (int i = 0; i < userRole.length; i++) {
            Role role = (Role) userRole[i];
            if (role.getState() == 1) {
                uRole[i] = role.getId();
            }
        }

        return uRole;
    }

    /**
     * 修改用户
     * @param user
     */
    public void modUser(User user) {
       this.entityService.update(user);
    }

    /**
     * 删除当前选中用户
     * @param user
     */
    public Boolean delUser(User user) {
        // 删除用户对应中间表
        String sql = "DELETE FROM UserRole ur WHERE ur.user.id = ?1";
        Query query = entityService.createQuery(sql,  user.getId());
        int rs = query.executeUpdate();
        
        // 删除用户
        sql = "DELETE FROM User u WHERE u.id=?1";
        int rs1 = this.entityService.batchExecute(sql, user.getId());
        if (rs > 0 && rs1 > 0) {
            return true;  
        }
        
        return false;
    }
    
    /**
     * 根据用户得到当前用户角色
     * @param user
     * @return
     */
    public List<Role> findRolesByUser(User user) {
        String sql = "select r from UserRole ur join ur.user u join ur.role r where r.state=1 and u.id=" + user.getId();
        return entityService.findList(sql);
    }

    /**
     * 根据角色ID找到角色
     * @param object
     * @return
     */
    public Role findRoleById(Long roleId) {
        String sql = "SELECT r FROM Role r WHERE r.id = " + roleId;
        Query query = entityService.createQuery(sql);
        Role role = (Role) query.getSingleResult();
        
        return role;
    }
}
