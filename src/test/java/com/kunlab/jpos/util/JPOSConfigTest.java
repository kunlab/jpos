package com.kunlab.jpos.util;

import org.junit.Test;

import java.io.File;

/**
 * @author likun
 */
public class JPOSConfigTest {

    @Test
    public void initialize() {
        File deployDir = new File("src/test/resources/deploy");
        JPOSConfig config = new JPOSConfig();
        try {
            config.initialize(deployDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decorateFile() {
    }
}