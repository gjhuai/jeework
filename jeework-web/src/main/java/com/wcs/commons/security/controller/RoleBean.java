/**
 * RoleBean.java Created: 2011-7-8 上午11:04:11
 */
package com.wcs.commons.security.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.conf.SystemConfiguration;
import com.wcs.base.util.MessageUtils;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.User;
import com.wcs.commons.security.service.RoleService;

/**
 * 
 * @author Chris Guan
 */
@ManagedBean
@ViewScoped
public class RoleBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(RoleBean.class);

    public enum OpMode{
        ADD("新增"),EDIT("修改"),VIEW("查看");
        private final String displayText;
        OpMode(String displayText){ this.displayText = displayText; }
        @Override public String toString(){return displayText;}
    }

	@Inject
	private RoleService roleService;
	
	private OpMode opMode;		// 操作模式
	private Role instance = new Role(); // 当前角色对象
	private List<Role> roles;// 数据模型

	private TreeNode root;// 资源树
	private TreeNode[] selectedNodes;// 节点数组
	
	private DualListModel<Role> listModel = new DualListModel<Role>();
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		this.list();
	}
	
	
	public void list() {
		this.roles = roleService.findAllRoles();
	}
	
	
	public void toAdd(){
		this.instance = new Role();
		this.opMode = OpMode.ADD;
	}
	

	public void add() {
		logger.info("创建Role");
		try {
			roleService.createRole(this.instance);
			roles.add(0, instance);	// 供页面table刷新之用
			MessageUtils.addSuccessMessage("msgMain", "角色添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtils.addErrorMessage("msgMain", "角色添加失败");
		}
	}
	
	
	public void edit() {
		try {
			roleService.updateRole(instance);
			MessageUtils.addSuccessMessage("msgMain", "角色更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtils.addErrorMessage("msgMain", "角色更新失败");
		}

	}	

	
	public void delete() {
		try {
			this.roleService.deleteRole(instance);
			roles.remove(instance);   // 供页面table刷新之用
			MessageUtils.addSuccessMessage("msgMain", "角色删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtils.addErrorMessage("msgMain", "删除失败");
		}
	}
	
	/**
	 * 装载选定用户所拥有的 Role，采用 DualListModel 来展示。
	 * 
	 * 1.查询出系统所有的Role
	 * 2.查询出给定用户所有拥有的Role
	 * 3.在 DualListModel 的左边显示未分配给给定用户的Role，在右边显示用户拥有的 Role
	 * 
	 * @param selectedUser
	 */
	public void toAllocRoles(User selectedUser){
        List<Role> allRoles = roleService.findAllRoles();
        List<Role> userRoles = roleService.findRoles(selectedUser);
        for (Role role : userRoles) {
            allRoles.remove(role);
        }
        listModel = new DualListModel<Role>(allRoles, userRoles);
	}
	
    /**
     * 在进行用户所有拥有的 Role 变动后，将新的Role分配给用户。
     * 
     * @return
     */
	public void allocRoles(User selectedUser) {
        List<Role> roleList = listModel.getTarget();
        // 新分配当前用户角色
        roleService.allocRoles(selectedUser, roleList);
	}
    
	
	//-------------------------------- setter & getter -----------------------------//
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public OpMode getOpMode() {
		return opMode;
	}

	public void setOpMode(OpMode opMode) {
		this.opMode = opMode;
	}

	public TreeNode getRoot() {
		return root;
	}
	
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public Role getInstance() {
		return instance;
	}

	public void setInstance(Role instance) {
		this.instance = instance;
	}

	public String getRowsPerPageTemplate() {
		return SystemConfiguration.ROWS_PER_PAGE_TEMPLATE;
	}


	public DualListModel<Role> getListModel() {
		return listModel;
	}


	public void setListModel(DualListModel<Role> listModel) {
		this.listModel = listModel;
	}
	
}
