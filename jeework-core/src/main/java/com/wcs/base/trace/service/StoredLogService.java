package com.wcs.base.trace.service;

import java.io.Serializable;

import javax.ejb.Stateless;

@Stateless
public class StoredLogService implements Serializable {

	private static final long serialVersionUID = 1L;


    public enum LogLevel {
                DEBUG, INFO, WARN, ERROR, TRACE;
        }

    public <T> T save(final T objectToSave){
    	return null;
    }
}
