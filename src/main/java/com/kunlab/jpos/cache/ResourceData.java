package com.kunlab.jpos.cache;

import com.kunlab.jpos.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 资源数据读取
 * @author likun
 */
public class ResourceData {

    private Logger logger = LogManager.getLogger(this.getClass());

    private String name;
    private String node;

    @SuppressWarnings("unchecked")
    public Map<String, Object> execute() {
        Map<String, Object> map = new HashMap<String, Object>();

        logger.info("name : {}", name);
        logger.info("node : {}", node);

        try{
            ResourceBundle resourceBundle = ResourceBundle.getBundle(name);
            Enumeration<String> enumeration = resourceBundle.getKeys();
            while(enumeration.hasMoreElements())
            {
                String key = enumeration.nextElement();
                String value = resourceBundle.getString(key);

                if(Util.isEmpty(node))
                {
                    map.put(key, value);
                }
                else
                {
                    try{
                        String[] keys = key.split("\\.", 2);
                        Map<String, Object> _map = new HashMap<String, Object>();
                        if(map.containsKey(keys[0]))
                        {
                            _map = (Map<String, Object>)map.get(keys[0]);
                        }
                        _map.put(keys[1], value);

                        map.put(keys[0], _map);
                    }
                    catch(Exception e){
                        map.put(key, value);
                    }
                }
            }
        }
        catch(Exception e){
            logger.error("loading resource data error.", e);
        }

        return map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
