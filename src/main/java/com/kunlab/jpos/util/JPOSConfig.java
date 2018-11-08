package com.kunlab.jpos.util;

import org.jpos.q2.ConfigDecorationProvider;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author likun
 */
public class JPOSConfig implements ConfigDecorationProvider {
    private Properties prop;

    @Override
    public void initialize(File deployDir) throws Exception {
        try(InputStream in = JPOSConfig.class.getClassLoader().getResourceAsStream("conf.properties")) {
            if (in != null) {
                prop = new Properties();
                prop.load(in);
            }
        }
    }

    @Override
    public void uninitialize() {

    }

    @Override
    public String decorateFile(File f) {
        String regex = "\\$\\{(.+?)\\}";
        return FileUtils.decorateFile(f, regex, prop);
    }
}
