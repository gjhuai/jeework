/** * ResourceVO.java 
* Created on 2011-12-8 下午4:51:12 
*/

package com.wcs.common.controller.permissions.vo;

import java.io.Serializable;

import javax.inject.Named;

import com.wcs.base.entity.VoKey;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ResourceVO.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/
@SuppressWarnings("serial")
@Named
public class ResourceVO extends VoKey implements Serializable {
    private String name;
    private String isLeaf;                  // 是否为有子节点：有/无
    private String isMenu;                  // 是否为菜单：是/不是
    private String parentName;              // 上级菜单名
    private String url;

    private String searchResourceName;      // 查询资源名条件

    public String getSearchResourceName() {
        return searchResourceName;
    }

    public void setSearchResourceName(String searchResourceName) {
        this.searchResourceName = searchResourceName;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
