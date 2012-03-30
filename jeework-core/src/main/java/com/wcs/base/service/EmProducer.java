package com.wcs.base.service;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;


public class EmProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	
	//@Produces
	//@PersistenceUnit(unitName = "pu")
	//@ConversationScoped
	EntityManagerFactory em;

	public EmProducer() {
		System.out.println("start em producer");
	}

}
