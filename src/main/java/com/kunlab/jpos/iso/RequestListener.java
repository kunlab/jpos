package com.kunlab.jpos.iso;

import com.kunlab.jpos.service.ISOService;
import com.kunlab.jpos.util.Spring;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.util.NameRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Q2:
 * <pre>
 *     <request-listener class="com.kunlab.jpos.iso.RequestListener" logger="Q2">
 *         <property name="spring" value="spring实例name" />
 *         <property name="service" value="ISOService..." />
 *     </request-listener>
 * </pre>
 *
 * @author likun
 */
public class RequestListener implements ISORequestListener, Configurable {
    private Logger logger = null;
    private static final String CFG_SPRING= "spring";
    private static final String CFG_SERVICE = "service";
    private Boolean useThreadPool;
    private ExecutorService threadPool;
    private Configuration cfg;
    private Spring instance;

    public RequestListener() {
        logger = LogManager.getLogger(RequestListener.class);
    }

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;
        useThreadPool = cfg.getBoolean("useThreadPool", true);
        if(useThreadPool)
            threadPool = Executors.newCachedThreadPool();

        instance = (Spring) NameRegistrar.getIfExists(cfg.get(CFG_SPRING));
        if(instance == null)
            throw new ConfigurationException("no spring instance found, spring name '" + cfg.get(CFG_SPRING) + "'");
    }

    @Override
    public boolean process(ISOSource source, ISOMsg m) {
        if(useThreadPool)
            threadPool.submit(() -> service(source, m));
        else
            service(source, m);
        return true;
    }


    private void service(ISOSource source, ISOMsg m) {
        ISOService isoService = (ISOService) instance.getBean(cfg.get(CFG_SERVICE));
        try {
            isoService.execute(m, source);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
