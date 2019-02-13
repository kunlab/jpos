package com.kunlab.jpos.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;

/**
 * 日志行为分等级记录，按照从低到高支持 debug < info < warn < error < fatal
 * 默认日志行为 info
 * @author likun
 */
public class Log4jListener implements LogListener{

    private Logger logger;

    public Log4jListener() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public LogEvent log(LogEvent ev) {
        String tag = ev.getTag();
        if(StringUtils.isBlank(tag))
            logger.info(ev);
        else {
            tag = tag.trim().toLowerCase();
            switch (tag) {
                case "debug":
                    logger.debug(ev);
                    break;
                case "info":
                    logger.info(ev);
                    break;
                case "warn":
                    logger.warn(ev);
                    break;
                case "error":
                    logger.error(ev);
                    break;
                case "fatal":
                    logger.fatal(ev);
                    break;
                default:
                    logger.info(ev);
                    break;
            }
        }
        return ev;
    }
}
