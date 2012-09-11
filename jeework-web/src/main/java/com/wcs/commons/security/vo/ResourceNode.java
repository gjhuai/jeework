/**
 * ResourcesNode.java
 * Created: 2011-7-8 下午03:24:39
 */
package com.wcs.commons.security.vo;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.wcs.base.collections.GenericTreeNode;
import com.wcs.commons.security.model.Resource;

/**
 * 采用Adapter模式，使得ResourceNode能直接被Primefaces使用
 * 
 * @author Chris Guan
 */
public class ResourceNode extends GenericTreeNode<Resource> implements TreeNode{
	
	public static final String DEFAULT_TYPE = "default";
	
	private String type;
	
	private boolean expanded;

    private boolean selected;
    
    private boolean selectable = true;
    
    private boolean located = true;
    
    //public GenericTreeNode(T data, GenericTreeNode<T> parent) 
	public ResourceNode(Resource data, TreeNode parent) {
    	super(data,(ResourceNode)parent);
		this.type = DEFAULT_TYPE;
//		this.setData(data);
//		this.setParent(parent);
    }

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
	@Override
    public ResourceNode getParent() {
        return (ResourceNode)super.getParent();
    }

    /**
     * Java 不认为 List<ResourceNode> 是 List<GenericTreeNode<T>> 子类，
     * 所以，public List<ResourceNode> getChildren() 无法覆盖 public List<GenericTreeNode<T>> getChildren()
     * 所以只能返回 List 了
     */
	@Override
    public List getChildren() {
    	return super.getChildren();
    }

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setParent(TreeNode treeNode) {
		super.setParent((GenericTreeNode)treeNode);
	}

	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	@Override
	public boolean isLeaf() {
		return super.isLeaf();
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isLocated() {
		return located;
	}

	public void setLocated(boolean located) {
		this.located = located;
	}

}
