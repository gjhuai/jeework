package com.wcs.base.trace.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by IntelliJ IDEA.
 * User: Chris
 * Date: 11-7-8
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "StoredLog")
public class StoredLog {

    private Long id;

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    private static final long serialVersionUID = -5765589801658858248L;

    private Date logdttm;

    private String logLevel;

    private String whereClass;

    private String userMessage;

    private String throwAbleMessage;


    @Temporal(TemporalType.TIMESTAMP)
    public Date getLogdttm() {
        return logdttm;
    }


    public void setLogdttm(Date logdttm) {
        this.logdttm = logdttm;
    }


    public String getLogLevel() {
        return logLevel;
    }


    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }


    public String getWhereClass() {
        return whereClass;
    }


    public void setWhereClass(String whereClass) {
        this.whereClass = whereClass;
    }


    public String getUserMessage() {
        return userMessage;
    }


    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }


    public String getThrowAbleMessage() {
        return throwAbleMessage;
    }


    public void setThrowAbleMessage(String throwAbleMessage) {
        this.throwAbleMessage = throwAbleMessage;
    }

}