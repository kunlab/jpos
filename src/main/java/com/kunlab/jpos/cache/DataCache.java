package com.kunlab.jpos.cache;

import java.util.Map;

/**
 * 数据缓存单例
 * @author likun
 */
public class DataCache {

    private static volatile DataCache ourInstance = null;

    private DataCacheObject dataCacheObject;
    private Map<String, Map<String, Object>> map;

    private DataCache(DataCacheObject dataCacheObject) {
        this.dataCacheObject = dataCacheObject;
    }

    public static DataCache getInstance(DataCacheObject dataCacheObject) {
        if(ourInstance == null) {
            synchronized (DataCache.class) {
                if(ourInstance == null) {
                    ourInstance = new DataCache(dataCacheObject);
                    ourInstance.init();
                }
            }
        }
        return ourInstance;
    }

    /**
     * 加载所有缓存对象
     */
    public void init() {
        map = dataCacheObject.execute();
    }

    /**
     * 加载指定缓存对象
     */
    public void init(String name) {
        Map<String, Object> _map = dataCacheObject.execute(name);
        if(_map != null && !_map.isEmpty())
        {
            map.remove(name);
            map.put(name, _map);
        }
    }

    /**
     * 获取指定缓存对象数据
     */
    @SuppressWarnings("unchecked")
    public Object get(String... keys) {
        Object object = map;

        if(keys != null && keys.length > 0)
        {
            for(String key : keys)
            {
                object = ((Map<String, Object>)object).get(key);
            }
        }

        return object;
    }
}
