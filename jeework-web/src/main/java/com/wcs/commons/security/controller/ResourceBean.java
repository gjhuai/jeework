/**
 * ResourceBean.java
 * Created: 2011-7-26 下午08:36:54
 */
package com.wcs.commons.security.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.exception.TransactionException;
import com.wcs.base.service.EntityWriter;
import com.wcs.base.util.CollectionUtils;
import com.wcs.base.util.MessageUtils;
import com.wcs.commons.conf.WebappConfig;
import com.wcs.commons.security.model.Resource;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.service.ResourceCache;
import com.wcs.commons.security.service.ResourceService;
import com.wcs.commons.security.vo.ResourceNode;

/**
 * 
 * @author Chris Guan
 */
@ManagedBean
@ViewScoped
public class ResourceBean implements Serializable {
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

    public enum OpMode{
        ADD("新增"),EDIT("修改"),VIEW("查看");
        private final String displayText;
        OpMode(String displayText){ this.displayText = displayText; }
        @Override public String toString(){return displayText;}
    }

	@EJB
	public ResourceCache resourceCache;
	@EJB
	private ResourceService resourceService;
	@EJB
	public EntityWriter entityWriter;
	@Inject
	WebappConfig config;

    private TreeNode root = null; // 资源树
	private TreeNode selectedNode; // 选中节点
	private TreeNode[] selectedNodes; // checkbox
    public Resource selectedResource; // 节点操作资源
    
    private OpMode opMode;

	@PostConstruct
	public void init() {
		//logger.debug("@PostConstruct init()");
//        root = new DefaultTreeNode("root", null);
//        this.buildTree(resourceCache.loadSubResources(0L), root);
		root = (ResourceNode)config.getTree().getRoot();
	}

	/**
	 * 用来维护Resource，采用递归方式实现
	 * @param subResList 给定的父节点的儿子资源列表
	 * @param parentNode 父节点
	 */
    private void buildTree(List<Resource> subResList,TreeNode parentNode){
    	
        for (Resource r : subResList){
            TreeNode node = new DefaultTreeNode(r, parentNode);
            List<Resource> subList = resourceCache.loadSubResources(r.getId());
            logger.debug("buildTreeTable:: parentId=%d, subList.size=%d", r.getId(), subList.size());
            if (CollectionUtils.isNotEmpty(subList))
                buildTree(subList,node);
        }
    }
    
    public void toAllocResources(Role role){
    	clearChecked(root);	// 清空 Tree 原有所有节点的 check 状态
    	List<Resource> allocatedResList = resourceService.findResources(role);
    	for (Resource res : allocatedResList){
    		setSelectedNode(res,root);
    	}
    }
	/**
	 * 为给定的 Role 分配 Resource
	 * 1.从选中的节点列表中，获取新的 Resource 列表，这个列表将会分配给 role
	 * 2.获取给定的 Role 现有的 Resource 列表
	 * 3.剔除没有被反选的原有的 Resource，增加新加入 Resource
	 * TODO:SQL多次查询，可以优化
	 * 
	 */
	public void allocResources(Role role) {
		// 将选中的 TreeNode 节点列表转换成 List<Resource>  
		List<Resource> selectedResource = new ArrayList<Resource>();//Lists.newArrayList();
		for(TreeNode node : selectedNodes) {
			selectedResource.add((Resource)node.getData());
		}
		// 按照新的 Resource 列表重新给 role 分配 Resource
		resourceService.allocResources(role,selectedResource);
	}
	
	/**
	 * 将给定的 Resource 在 Resource Tree 中的节点设置为 checked
	 * @param res 给定的 Resource
	 * @param parentNode 从这个节点开始，遍历其所有子孙节点
	 */
    private void setSelectedNode(Resource res,TreeNode parentNode){
    	Object obj = parentNode.getData();
    	if (obj instanceof Resource && res.equals((Resource)obj)){
    		parentNode.setSelected(true);
    	}
    	List<TreeNode> children = parentNode.getChildren();
    	if (CollectionUtils.isNotEmpty(children)){
    		for (TreeNode node : children){
    			setSelectedNode(res,node);
    		}
    	}
    }
    
    private void clearChecked(TreeNode parentNode){
    	parentNode.setSelected(false);   // 清空原有的 check 状态
    	
    	List<TreeNode> children = parentNode.getChildren();
    	if (CollectionUtils.isNotEmpty(children)){
    		for (TreeNode node : children){
    			clearChecked(node);
    		}
    	}
    }

    public void toAdd(){
        Long parentId = selectedResource.getId();
        this.selectedResource = new Resource();
        this.selectedResource.setParentId(parentId);
        this.opMode = OpMode.ADD;
    }
    /**
     * 添加子资源
     */
    public void add() {
        try {
            resourceService.addResource(selectedResource);
            //config.initResTree();
            init();	// 更新页面数据(Resource-Tree)
            MessageUtils.addSuccessMessage("resMsg", "添加资源成功！");
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("resMsg", "添加资源失败，请检查！");
        }
    }
    /**
	 * 修改资源
	 */
	public void edit() {
	    try {
	        // 修改当前资源
	        resourceService.updateResource(selectedResource);
	        //config.initResTree();
	        init();	// 更新页面数据(Resource-Tree)
	        MessageUtils.addSuccessMessage("resMsg", "修改资源成功！");
	    }catch (TransactionException e) {
	        //e.printStackTrace();
	        MessageUtils.addErrorMessage("resMsg", e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        MessageUtils.addErrorMessage("resMsg", "修改资源失败！");
	    }
	}	
	
    /**
     * 删除资源
     */
    public void delete() {
        try {
            this.resourceService.deleteResource(selectedResource);
            //config.initResTree();
            init();	// 更新页面数据(Resource-Tree)
            MessageUtils.addSuccessMessage("resMsg", "删除资源成功！");
        } catch(TransactionException te){
            MessageUtils.addErrorMessage("resMsg", "删除资源失败, 请检查！"+te.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("resMsg", "删除资源失败, 请检查！");
        }

        
        
    }

    /**
     * 初始化 SelectOneMenu 组件
     */
    public Resource.ResourceType[] getResourceTypeValues() {
        return Resource.ResourceType.values();
    }

    //-------------------------- setter & getter -----------------------//
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public OpMode getOpMode() {
        return opMode;
    }

    public void setOpMode(OpMode opMode) {
        this.opMode = opMode;
    }
    
}
