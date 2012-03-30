/**
 * ResourcesNode.java
 * Created: 2011-7-8 下午03:24:39
 */
package com.wcs.common.util;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * 
 * <p>
 * Project: cmdpms
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2011 Wilmar Consultancy Services
 * </p>
 * <p>
 * All Rights Reserved.
 * </p>
 * 
 * @author <a href="mailto:chenlong@wcs-global.com">chenlong</a>
 */
public class ResourcesNode extends DefaultTreeNode {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public ResourcesNode() {
        super();
    }

    public ResourcesNode(String name, TreeNode parent) {
        super(name, parent);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourcesNode other = (ResourcesNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

}
