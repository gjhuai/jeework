package com.wcs.commons.security;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.faces.context.FacesContext;

import com.wcs.commons.conf.WebappConfig;
import com.wcs.commons.security.vo.ResourceNode;
import com.wcs.commons.security.vo.ResourceTree;

public class Navigator {

	public static String to(String resCode){
		if (resCode==null || "".equals(resCode.trim())) throw new IllegalArgumentException("resCode 不能为空。");
		Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
		ResourceTree tree = (ResourceTree)params.get( WebappConfig.RES_TREE );
		ResourceNode node = tree.findByCode(resCode.trim());
		if (node==null) throw new NoSuchElementException("没有这个resource code 对应的资源。");
		return node.getData().getUri();
	}
}
