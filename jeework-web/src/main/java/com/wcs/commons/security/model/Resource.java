package com.wcs.commons.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wcs.base.entity.IdEntity;


/**
 * 
 * @author Chris Guan
 *
 */
@Entity
@Table(name="resource")
public class Resource extends IdEntity {
	
    public enum ResourceType {
    	MENU, COMPONENT
    }
    
	@Column(nullable=false, length=50)
	private String code;

	@Column(nullable=false, length=20)
	private String name;
	
	@Column(name="PARENT_ID")
	private Long parentId;	
	
	@Column(name="SEQ_NO", length=255)
	private Short seqNo;
	
	@Column(nullable=false, length=50)
	@Enumerated(EnumType.STRING)
	private ResourceType type;	//MENU,LEAF_MENU,COMPONENT
	
	@Column(length=200)
	private String uri;
	
/*	@OneToMany(mappedBy="resource", fetch=FetchType.EAGER)
	private List<RoleResource> roleResources;*/
	
	@Transient
	private String elementId;
	
	public Resource(){}
	
	public Resource(Long id, String code, String name, Long parentId){
		this(code,name,parentId,null,ResourceType.MENU,null);
		this.setId(id);
	}
	
	public Resource(String code, String name, Long parentId, Short seqNo,
			ResourceType type, String uri) {
		super();
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.seqNo = seqNo;
		this.type = type;
		this.uri = uri;
	}

	//--------------------- setter & getter -------------------//
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Short getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Short seqNo) {
		this.seqNo = seqNo;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

/*	public List<RoleResource> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(List<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}*/

}