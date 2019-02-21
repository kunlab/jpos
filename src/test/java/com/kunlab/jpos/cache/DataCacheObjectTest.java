package com.kunlab.jpos.cache;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author likun
 */
public class DataCacheObjectTest {

    @Test
    public void execute() {
        List list = new ArrayList();
        Map map = new HashMap<String, Object>();
        ResourceData cacheData = new ResourceData();
        cacheData.setName("cache");
        map.put("name", "cache");
        map.put("object", cacheData);
        map.put("method", "execute");

        list.add(map);
        try {
            DataCacheObject dataCacheObject = new DataCacheObject(list);
            Map<String, Map<String, Object>> result = dataCacheObject.execute();
            Map<String, Object> _map = result.get("cache");
            Assert.assertEquals("00", (String)_map.get("respcode.00000000"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void execute1() {
    }
}