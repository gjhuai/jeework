package com.wcs.commons.security.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wcs.base.collections.GenericTree;
import com.wcs.base.collections.GenericTreeNode;
import com.wcs.commons.security.model.Resource;
/**
 * 
 * @author Chris Guan
 */
public class ResourceTree extends GenericTree<Resource>{
	
    public Resource findResource(Resource res){
    	GenericTreeNode<Resource> node = this.find(res);
    	return node.getData();
    }
    
    public ResourceNode findByCode(String resCode) {
        ResourceNode node = null;

        if(this.getRoot() != null) {
            node = findByCode((ResourceNode)this.getRoot(), resCode);
        }

        return node;
    }
    
	private ResourceNode findByCode(ResourceNode currentNode, String resCode) {
		ResourceNode returnNode = null;
		int i = 0;

		if (currentNode.getData().getCode().equals(resCode)) {
			returnNode = currentNode;
		}

		else if (currentNode.hasChildren()) {
			i = 0;
			while (returnNode == null && i < currentNode.getChildCount()) {
				returnNode = findByCode((ResourceNode)currentNode.getChildAt(i),resCode);
				i++;
			}
		}

		return returnNode;
	}
	
    public ResourceNode findByUri(String uri) {
    	ResourceNode node = null;

        if(this.getRoot() != null) {
            node = findByUri((ResourceNode)this.getRoot(), uri);
        }

        return node;
    }
	
	private ResourceNode findByUri(ResourceNode currentNode, String uri) {
		ResourceNode returnNode = null;
		int i = 0;

		if (uri.equals(currentNode.getData().getUri())) {
			returnNode = currentNode;
		}

		else if (currentNode.hasChildren()) {
			i = 0;
			while (returnNode == null && i < currentNode.getChildCount()) {
				returnNode = findByUri((ResourceNode)currentNode.getChildAt(i),uri);
				i++;
			}
		}

		return returnNode;
	}
	
	/**
	 * 
	 * @param currentNode
	 * @return 按照从父到子顺序排列
	 */
	public List<String> findParentChain(GenericTreeNode<Resource> currentNode){
		List<String> chain = new ArrayList<String>();
		
		if (currentNode!=null){
			// 获得父节点列表
			this.findParentChain(chain,currentNode);
			if (chain.size()>=1){
				chain.remove(chain.size()-1);  // 去掉 root （Resource(0,'root','root',null) )
				Collections.reverse(chain);
			}
		}
		return chain;
	}
	/**
	 * 
	 * @param chain 输出参数,parent 按照从子到父倒序排列
	 * @param currentNode
	 */
	private void findParentChain(List<String> chain,GenericTreeNode<Resource> currentNode){
		chain.add(((Resource)currentNode.getData()).getElementId());
		GenericTreeNode<Resource> parent = currentNode.getParent();
		
		if (parent!=null && parent.getData() instanceof Resource){
			//chain.add(((Resource)parent.getData()).getCode());
			findParentChain(chain,parent);
		}
	}
	
	/**
	 * 设置当前节点的所有父节点被选中（包括其本身）
	 * @param currentNode 
	 */
	public void setLocatedNode(ResourceNode currentNode){
		currentNode.setLocated(true);
		ResourceNode parent = currentNode.getParent();
		
		if (parent!=null){
			setLocatedNode(parent);
		}
	}
	
	public void clearAllLocated(){
		List<GenericTreeNode<Resource>> list = this.build();
		for (GenericTreeNode<Resource> node: list){
			((ResourceNode)node).setLocated(false);
		}
	}
}
