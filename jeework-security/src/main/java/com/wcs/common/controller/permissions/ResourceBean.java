/**
 * ResourceBean.java
 * Created: 2011-7-26 下午08:36:54
 */
package com.wcs.common.controller.permissions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.controller.ViewBaseBean;
import com.wcs.base.util.JSFUtils;
import com.wcs.base.util.MessageUtils;
import com.wcs.common.model.Resource;
import com.wcs.common.service.permissions.ResourceService;
import com.wcs.common.test.CustomAuthorizer;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: ResourceBean.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ResourceBean extends ViewBaseBean<Resource> {  
    private static final Logger log = LoggerFactory.getLogger(CustomAuthorizer.class);
    
    @EJB
    private ResourceService resourceService;
    
    private LazyDataModel<Resource> lazyModel;
    private Resource selectedResource;                                   // 节点操作资源
    
    private TreeNode root;                                              // 菜单资源
    private TreeNode selectedNode;                                      // 选中节点
    List<Resource> resList;                                              // 所有菜单资源
    private Boolean addBtnDisplay = false;
    private Boolean modBtnDisplay = true;
    private Boolean delBtnDisplay = true;
    
    public ResourceBean() {}
    
    @PostConstruct
    public void initResource() {
        this.lazyModel = resourceService.searchAllResource();
        this.initResourceTree();
    }
    
    /**
     * 根据条件查询资源
     */
    public void searchResource() {
        log.info("Search Resource start.");
        String searchResourceName = JSFUtils.getRequestParam("resName");
        this.lazyModel = this.resourceService.searchResourceByName(searchResourceName);
    }
    
    /**
     * 清除
     */
    public void clean() {
        this.selectedResource = null;
        this.setInstance(null);
        addBtnDisplay = false;
        modBtnDisplay = true;
        delBtnDisplay = true;
        
        // 刷新
        refresh();
    }
    
    /**
     * 添加资源
     */
    public void addResource() {
        // 判断上级菜单是否为空
       if (selectedResource == null) {
           MessageUtils.addSuccessMessage("resMsg", "上级菜单不能为空，请选择上级菜单！");
           return;
       }
        
        // 判断选择菜单是否为叶子节点，是则更新为包节点
        Boolean isLeaf = selectedResource.getIsLeaf();
        if (isLeaf) {
            this.selectedResource.setIsLeaf(false);
            resourceService.updateCurrentResource(this.selectedResource);
        }
        
        // 判断关键字是否唯一
        String keyName = JSFUtils.getRequestParam("keyName");
        Boolean isUnique = this.resourceService.judgeKeyNameUnique(keyName);
        if (!isUnique) {
            MessageUtils.addSuccessMessage("resMsg", "关键字已经存在,请从新输入！");
            return;
        }
        
        // 构建新建资源
        buildNewResource(selectedResource);
        try { 
            this.save();
            MessageUtils.addSuccessMessage("resMsg", "添加资源成功！");
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("resMsg", "添加资源失败，请检查！");
        }
       
        // 更新页面数据
        refresh();
    }
    
    /**
     * 构建新建资源
     * @param selectedResource
     */
    private void buildNewResource(Resource selectedResource) {
        // 上级菜单ID
        getInstance().setParentId(selectedResource.getId());
       
        // 菜单级别
        int level = selectedResource.getLevel() + 1;
        getInstance().setLevel(level);
        
        // 设置默认isLeaf
        getInstance().setIsLeaf(true);
    }
   
    /**
     * 删除资源
     */
    public void deleteResource() {
        Resource resource = getInstance();
        if (resource.getId() < 7) {
            MessageUtils.addSuccessMessage("resMsg", "初始保护资源不能删除,请选择序号大于6的资源！");
            return;
        }
  
        try {
            this.resourceService.deleteResource(resource);
            MessageUtils.addSuccessMessage("resMsg", "删除资源成功！");
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("resMsg", "删除资源失败, 请检查！");
        }
        
        // 更新页面数据
        refresh();
    }
    
    public void onRowSelect(SelectEvent event) {
        if (this.selectedResource == null) {
            this.selectedResource = new Resource();
        }
        
        //设置上级菜单
        Resource resource = (Resource)event.getObject();
        this.setInstance(resource);
        this.selectedResource.setName(getInstance().getParentName());
        this.selectedResource.setId(getInstance().getParentId());
       
        addBtnDisplay = true;
        modBtnDisplay = false;
        delBtnDisplay = false;
    }
    
    /**
     * 修改资源
     */
    public void modResource() {
        Resource resource = getInstance();
        if (resource.getId() < 7) {
            MessageUtils.addSuccessMessage("resMsg", "初始保护资源不能修改,请选择序号大于6的资源！");
            return;
        }
        
        // 判断上级菜单是否为空
        if (selectedResource == null) {
            MessageUtils.addSuccessMessage("resMsg", "上级菜单不能为空，请选择上级菜单！");
            return;
        }

        // 判断关键字是否唯一
        String keyName = JSFUtils.getRequestParam("keyName");
        Boolean isUnique = this.resourceService.judgeKeyNameUnique(keyName);
        if (!isUnique) {
            // 判断keyName对象是否为同一资源
            Resource resByKeyName = resourceService.findResourceByKeyName(keyName);
            if (resByKeyName.getId().longValue() != resource.getId().longValue()) {
                MessageUtils.addSuccessMessage("resMsg", "关键字已经存在,请从新输入！");
                return;
            }
        }
        
        // 更新上级菜单不是叶子节点
        Boolean isLeaf = selectedResource.getIsLeaf();
        if (isLeaf) {
            this.selectedResource.setIsLeaf(false);
            resourceService.updateCurrentResource(this.selectedResource);
        }
        
        // 修改当前资源
        try {
            Long parentId = resource.getParentId();
            Long selectedParentId = selectedResource.getId();
            if (selectedParentId != parentId) {
               resource.setParentId(selectedParentId); 
            }
            this.resourceService.modResource(resource);
            
            // 检查旧的上级菜单是否应该为叶子节点
            Boolean checkIsLeaf = resourceService.checkCurrentResIsLeaf(parentId);
            if (checkIsLeaf) {
                Resource res = new Resource();
                res.setId(parentId);
                res.setIsLeaf(true);
                resourceService.updateCurrentResource(res);
            }
            
            MessageUtils.addSuccessMessage("resMsg", "修改资源成功！");
        } catch(Exception e) {
           e.printStackTrace();
           MessageUtils.addErrorMessage("resMsg", "修改资源失败！");
        }
        
        // 更新页面数据
        refresh();
    }
    
    /**
     * 树节点点击事件
     * @param event
     */
    public void selectedNodeResource() {
        if(selectedNode != null) { 
            this.selectedResource = (Resource) this.selectedNode.getData();
            if (this.selectedResource.getIsmenu()) {
                addBtnDisplay = false;
            } else {
                addBtnDisplay = true;
            }
        }  
    }

    /**
     * 初始化树资源
     */
    private void initResourceTree() {
        // 查找所有资源
        if (this.resList == null) {
            this.resList = new ArrayList<Resource>();
        }
        this.resList = resourceService.findAllResource();
        
        //构建资源树
        createResourceTree();
    }

    /**
     * 构建资源树
     */
    private void createResourceTree() {
        root = new DefaultTreeNode("Root", null);
        for (int i = 0; i < resList.size(); i++ ) {
            Resource resource = resList.get(i);
            if (resource.getParentId() == 0) {
                TreeNode node = new DefaultTreeNode(resource, root);
                if (resource.getIsLeaf() == false) {
                    findChildResource(resource, node);
                }
            }
        }
    }

    /**
     * 查找子菜单资源 
     * @param resource
     * @param node
     */
    private void findChildResource(Resource resource, TreeNode node) {
        // 得到当前菜单的子菜单
        Long currentId = resource.getId();
        for (Resource r : resList) {
            if (r.getParentId() == currentId) {
                TreeNode childNode = new DefaultTreeNode(r, node);
                if (r.getIsLeaf() == false) {
                    findChildResource(r, childNode);
                }
            }
        }
    }
    
    /**
     * 刷新
     */
    private void refresh() {
        this.lazyModel = resourceService.searchAllResource();
        initResourceTree();
    }

    public LazyDataModel<Resource> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Resource> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Boolean getAddBtnDisplay() {
        return addBtnDisplay;
    }

    public void setAddBtnDisplay(Boolean addBtnDisplay) {
        this.addBtnDisplay = addBtnDisplay;
    }

    public Boolean getModBtnDisplay() {
        return modBtnDisplay;
    }

    public void setModBtnDisplay(Boolean modBtnDisplay) {
        this.modBtnDisplay = modBtnDisplay;
    }

    public Boolean getDelBtnDisplay() {
        return delBtnDisplay;
    }

    public void setDelBtnDisplay(Boolean delBtnDisplay) {
        this.delBtnDisplay = delBtnDisplay;
    }
}
