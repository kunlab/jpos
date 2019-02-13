package com.kunlab.jpos.util;

import org.jpos.util.LogEvent;
import org.junit.Test;

/**
 * @author likun
 */
public class Log4jListenerTest {

    @Test
    public void log() {
        Log4jListener log4jListener = new Log4jListener();
        LogEvent ev = new LogEvent("error", "test");

        log4jListener.log(ev);
        System.out.println("------------------------");
    }
}