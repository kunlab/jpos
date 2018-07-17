package com.kunlab.jpos.util;

import org.jpos.q2.ConfigDecorationProvider;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author likun
 */
public class JposConfigDecorationProvider implements ConfigDecorationProvider {
    private Properties prop;

    @Override
    public void initialize(File deployDir) throws Exception {
        InputStream in = JposConfigDecorationProvider.class.getClassLoader().getResourceAsStream("conf.properties");

        try {
            if(in != null) {
                prop = new Properties();
                prop.load(in);
            }
        } finally {
            if(in != null) in.close();
        }
    }

    @Override
    public void uninitialize() {

    }

    @Override
    public String decorateFile(File f) throws Exception {
        String regex = "\\$\\{(.+?)\\}";
        return FileUtils.decorateFile(f, regex, prop);
    }
}
