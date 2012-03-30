package com.wcs.base.trace.service;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wcs.base.service.StatelessEntityService;

/**
 * Created by IntelliJ IDEA.
 * User: Chris
 * Date: 11-7-8
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
@Named
@ConversationScoped
public class StoredLogService implements Serializable {
//    @Inject
//	private EntityService entityService;
	
	@Inject
    StatelessEntityService entityService;

    public enum LogLevel {
                DEBUG, INFO, WARN, ERROR, TRACE;
        }

    public <T> T save(final T objectToSave){
    	return null;
//			return entityService.create(objectToSave);
    }
}
