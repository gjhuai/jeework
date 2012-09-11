package com.wcs.commons.security.event;

import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.util.JSFUtils;
import com.wcs.commons.conf.WebappConfig;
import com.wcs.commons.security.vo.ResourceNode;
import com.wcs.commons.security.vo.ResourceTree;

public class ResourceMonitorPhaseListener implements PhaseListener {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void beforePhase(PhaseEvent pe) {
		String resCode = (String)JSFUtils.getSession().get(WebappConfig.SESSION_NEXT_DISPLAY_RES_CODE);
		String viewId = JSFUtils.getViewId();
		logger.debug("Before - " + pe.getPhaseId().toString() + ", 访问页面 : "+viewId);
		
		ResourceTree tree = (ResourceTree)JSFUtils.getApplicationMap().get( WebappConfig.RES_TREE );
		if (tree==null) return;

		ResourceNode node = null;
		
		if (StringUtils.isNotEmpty(resCode)){
			node = tree.findByCode(resCode);	// 通过root 和 resource Code 查找节点
		} else if (StringUtils.isNotEmpty(viewId)){
			node = tree.findByUri(viewId);		// 通过root 和 resource Uri 查找节点
		} 
		
		if (node==null){
			node = (ResourceNode)tree.getRoot();
			logger.warn(resCode+"不能定位到有效的菜单项");
			//throw new IllegalArgumentException("不能定位到有效的菜单项");
		}
		
		// 从root节点到本节点的节点链
		List<String> elementIdChain = tree.findParentChain(node);
		JSFUtils.getSession().put(WebappConfig.SESSION_RES_ID_CHAIN, elementIdChain);
		logger.debug("被定位的菜单链 => "+elementIdChain);

		//将要定位菜单（父节点链）的节点设为 located =true（未实现）
//		tree.clearAllLocated();
//		tree.setLocatedNode(node);
	}

	public void afterPhase(PhaseEvent pe) {}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE; 
	}
}
