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
public class DataCacheTest {

    @Test
    public void get() {

        ResourceData cacheData = new ResourceData();
        cacheData.setName("cache");

        List list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "cache");
        map.put("object", cacheData);
        map.put("method", "execute");

        list.add(map);

        try {
            DataCacheObject dataCacheObject = new DataCacheObject(list);

            DataCache instance = DataCache.getInstance(dataCacheObject);
            Assert.assertEquals("00", (String)instance.get(new String[]{"cache", "respcode.00000000"}));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}