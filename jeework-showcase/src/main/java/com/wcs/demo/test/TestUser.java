package com.wcs.demo.test;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Project: btcbase</p> 
 * <p>Title: User.java</p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright .All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author <a href="mailto:yujingu@wcs-gloabl.com">Yu JinGu</a>
 */
@SuppressWarnings("serial")
@Entity
@Table
public class TestUser implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    protected TestUser() {
    }

    public TestUser(String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
