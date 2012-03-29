package com.wcs.demo.controller;


import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.wcs.demo.model.Person;

public class EntityManagerMain {
    public static void main(String[] a) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Collection emps = em.createQuery("selete p from Person p where defunctInd=true").getResultList();
        for (Iterator i = emps.iterator(); i.hasNext();) {
        	Person e = (Person) i.next();
          System.out.println("Professor " + e.getId() + ", " + e.getName());
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
  }
}
