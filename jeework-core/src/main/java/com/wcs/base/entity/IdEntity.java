package com.wcs.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
public abstract class IdEntity implements java.io.Serializable {
	private Long id;
    
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="myGen")
	@TableGenerator(name="myGen", table="ID_GEN",initialValue=5000)
	
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SRC") 
    //@SequenceGenerator(name="SRC", sequenceName="SRC", allocationSize=20)   
	// for db2 : CREATE SEQUENCE ROOT.SRC START WITH 500 INCREMENT BY 1 MAXVALUE 999999999 CYCLE NOCACHE
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdEntity other = (IdEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}	
}
