package com.kunlab.jpos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;

/**
 * @author likun
 */
public class Log4jListener implements LogListener{

    private Logger logger;

    public Log4jListener() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public LogEvent log(LogEvent ev) {
        logger.info(ev);
        return ev;
    }
}
