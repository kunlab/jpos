package com.kunlab.jpos.util;

import org.apache.commons.lang3.StringUtils;
import org.jpos.core.ConfigurationException;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;

/**
 * jpos driver spring
 *
 * <b>Sample Configuration</b>
 * <spring-init name="jpos-spring" class="com.kunlab.jpos.util.JPOSSpring" logger="Q2">
 *     <property name="spring" value="..."/>
 * </spring-init>
 *
 */
public class JPOSSpring extends QBeanSupport {
    private static final String CFG_SERVER_SPRING = "spring";

    @Override
    public void initService() throws ConfigurationException {
        String cfg_spring = cfg.get(CFG_SERVER_SPRING);
        log.info("loading spring application: " + cfg_spring);
        if(StringUtils.isBlank(cfg_spring))
            throw new ConfigurationException("spring cfg must be not null!");
    }

    @Override
    public void startService(){
        Spring spring = Spring.getInstance(cfg.get(CFG_SERVER_SPRING));
        NameRegistrar.register(getName(), spring);
    }

    @Override
    public void destroyService(){
        NameRegistrar.unregister(getName());
    }
}
