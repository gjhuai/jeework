/**
 * SysErrorListener.java
 * Created: 2011-10-26 上午10:17:27
 */
package com.wcs.base.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

/**
 * <p>Project: cmdpms</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2011 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yourname@wcs-global.com">Your Name</a>
 */
public class SysErrorListener implements PhaseListener{

    private static final Logger logger = Logger.getLogger(SysErrorListener.class);
    /**
     * 
     */
    private static final long serialVersionUID = 9036688944413815353L;

    /**
     * 
     */
    public SysErrorListener() {
    }

    @Override
    public void afterPhase(PhaseEvent event){
//        logger.info("阿光");
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
