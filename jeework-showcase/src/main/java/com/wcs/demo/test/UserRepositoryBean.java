package com.wcs.demo.test;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: UserRepositoryBean.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@Local(UserRepository.class)
@Stateless
public class UserRepositoryBean implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public String searchUser() {
        TestUser user = new TestUser("Jo Yu", "lastName Test");
        em.persist(user);
       
        return "yes";
    }
    
}
