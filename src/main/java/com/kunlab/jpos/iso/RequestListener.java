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
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q2:
 * <pre>
 *     <request-listener class="com.kunlab.jpos.iso.RequestListener" logger="Q2">
 *         <property name="spring" value="spring instance name" />
 *         <property name="service" value="ISOService..." />
 *     </request-listener>
 * </pre>
 *
 * @author likun
 */
public class RequestListener implements ISORequestListener, Configurable {
    private Logger logger;
    private static final String CFG_SPRING= "spring";
    private static final String CFG_SERVICE = "service";
    private Boolean useThreadPool;
    private ExecutorService threadPool;
    private Configuration cfg;

    private Spring instance;
    private ISOService isoService;


    public RequestListener() {
        logger = LogManager.getLogger(RequestListener.class);
    }

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;
        useThreadPool = cfg.getBoolean("useThreadPool", true);
        if(useThreadPool)
            threadPool = Executors.newCachedThreadPool(new NamedThreadFactory("requestListener"));

        instance = (Spring) NameRegistrar.getIfExists(cfg.get(CFG_SPRING));
        if(instance == null)
            throw new ConfigurationException("no spring instance found, cfg the spring instance name: '" + cfg.get(CFG_SPRING) + "'");

        isoService = (ISOService) instance.getBean(cfg.get(CFG_SERVICE));
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
        try {
            isoService.execute(m, source);
        } catch (Exception e) {
            logger.error(e);
        }
    }


    static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private NamedThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-" + name + "-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }



}
