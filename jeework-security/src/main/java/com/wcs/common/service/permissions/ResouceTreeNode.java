package com.wcs.common.service.permissions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.google.common.collect.Iterators;

/**
 * 
 * <p>Project: ncp</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:Chen Long@wcs-global.com">Chen Long</a>
 */
public class ResouceTreeNode implements TreeNode {
    /** 节点ID*/
	private Long id;
	/** 节点名称*/
	private String name;
	private String url;
	public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    /** 孩子节点集合*/
	private List<ResouceTreeNode> children = new ArrayList<ResouceTreeNode>();
	/** 是否选中*/
	private boolean selected = false;
	/** 父节点对象*/
	private ResouceTreeNode parentTree;

	public ResouceTreeNode() {

	}

	public ResouceTreeNode getParentTree() {
		return parentTree;
	}

	public void setParentTree(ResouceTreeNode parentTree) {
		this.parentTree = parentTree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ResouceTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ResouceTreeNode> children) {
		this.children = children;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * 根据位置找孩子节点
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int childIndex) {

		return children.get(childIndex);
	}
	
	/**
	 * 得到孩子节点数量
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	@Override
	public int getChildCount() {

		return children.size();
	}
	
	/**
	 * 得到父节点
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public TreeNode getParent() {

		return this.parentTree;
	}
	
	public void setParent(ResouceTreeNode parent){
		this.parentTree = parent;
	}
	
	/**
	 *根据节点找到所在索引位置 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getIndex(TreeNode node) {
		
		return children.indexOf(node);
	}
	
	/**
	 * 得到所有的孩子节点
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		
		return true;
	}
	
	@Override
	public boolean isLeaf() {
		
		return children.isEmpty();
	}

	/**
	 * 返回孩子节点迭代接口
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration children() {
		return Iterators.asEnumeration(children.iterator());
	}

	

}
