package com.wcs.commons.conf;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.wcs.commons.security.model.Resource;
import com.wcs.commons.security.service.ResourceCache;
import com.wcs.commons.security.vo.ResourceNode;
import com.wcs.commons.security.vo.ResourceTree;

/**
 * 
 * @author Chris Guan
 */
@ManagedBean(name="config", eager=true)
@ApplicationScoped
public class WebappConfig {

	public final static String RES_TREE = "resTree";		// 整个系统的资源树
	public final static String SESSION_RES_ID_CHAIN = "resIdChain";	// 从root节点到本节点的节点链
	
	public final static String SESSION_NEXT_DISPLAY_RES_CODE = "nextDisplayResCode";
	public final static String SESSION_CURRENT_USER = "currentUser";
	
	private Map<String, Object> appMap;
	
	private ResourceTree tree = null;
	
	@EJB
	ResourceCache resourceCache;
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init(){
		initResTree();
	}

	public void initResTree() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();
		this.appMap = ec.getApplicationMap();
		
		/**
		 * 1.构建root节点
		 * 2.从root节点开始，递归构建tree
		 */
		tree = new ResourceTree();		// tree初始化
		ResourceNode root = new ResourceNode(new Resource(0L,"root","root",null), null);  // root
		resourceCache.buildTree(root);
		tree.setRoot(root);
		
		appMap.put(WebappConfig.RES_TREE, tree);
	}
	

	public Map<String, Object> getAppMap() {
		return appMap;
	}
	
	public ResourceTree getTree() {
		return tree;
	}

	public void setTree(ResourceTree tree) {
		this.tree = tree;
	}
}
