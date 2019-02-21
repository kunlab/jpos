package com.kunlab.jpos.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据缓存对象
 *
 * <bean id="dataCacheObject" class="com.zrj.pay.core.cache.DataCacheObject">
 *     <constructor-arg>
 *         <list>
 *             <map>
 *                 <entry key="name" value="缓存对象名1"/>
 *                 <entry key="object" value-ref="缓存对象数据源1 spring bean id"/>
 *                 <entry key="method" value="缓存对象数据源1执行方法名"/>
 *             </map>
 *             <map>
 *                 <entry key="name" value="缓存对象名2"/>
 *                 <entry key="object" value-ref="缓存对象数据源2 spring bean id"/>
 *                 <entry key="method" value="缓存对象数据源2执行方法名"/>
 *             </map>
 *         </list>
 *     </constructor-arg>
 * </bean>
 *
 * @author likun
 */
public class DataCacheObject {

    private Logger logger = null;

    private List<Map<String, Object>> list = null;

    /**
     * 数据缓存对象定义
     *
     * @param list
     * @throws Exception
     */
    public DataCacheObject(List<Map<String, Object>> list) throws Exception {
        logger = LogManager.getLogger(this.getClass());

        if(list != null && !list.isEmpty())
        {
            Map<Object, Object> _map = new HashMap<Object, Object>();
            for(Map<String, Object> map : list)
            {
                Object name = map.get("name");
                Object object = map.get("object");

                if(_map.containsKey(name))
                {
                    throw new Exception("key is exist : " + name);
                }
                else
                {
                    if(object == null)
                    {
                        throw new Exception("object is not exist : " + name);
                    }
                    else
                    {
                        try{
                            object.hashCode();
                        }
                        catch(Exception e){
                            throw new Exception("object is not exist : " + name);
                        }

                        _map.put(name, object);
                    }
                }
            }

            this.list = list;
        }
    }

    /**
     * 数据缓存对象加载
     *
     * @return Map<String , Map<String, Object>>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, Object>> execute() {
        Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

        try{
            logger.info("data cache init...");

            if(list != null && !list.isEmpty())
            {
                logger.info("data cache size : {}", list.size());

                int n = 1;
                for(Map<String, Object> map : list)
                {
                    Object name = map.get("name");
                    Object object = map.get("object");
                    Object method = map.get("method");
                    logger.info("{} : {} | {} . {}", n, name, object, method);

                    Method _method = object.getClass().getMethod(method.toString(), new Class[0]);
                    Object _object = _method.invoke(object, new Object[0]);
                    logger.info("{} = {}", name, _object);

                    result.put(name.toString(), (Map<String, Object>)_object);

                    n ++;
                }
            }
        }
        catch(Exception e){
            logger.error(list, e);
        }

        return result;
    }

    /**
     * 指定数据缓存对象加载
     *
     * @param key 数据缓存对象名
     * @return Map<String, Object>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> execute(String key) {
        Map<String, Object> result = new HashMap<String, Object>();

        try{
            logger.info("data cache init : {}", key);

            if(list != null && !list.isEmpty())
            {
                for(Map<String, Object> map : list)
                {
                    Object name = map.get("name");
                    if(key.equals((String)name))
                    {
                        Object object = map.get("object");
                        Object method = map.get("method");
                        logger.info("{} | {} . {}", name, object, method);

                        Method _method = object.getClass().getMethod(method.toString(), new Class[0]);
                        Object _object = _method.invoke(object, new Object[0]);
                        logger.info(name + " = " + _object);

                        result = (Map<String, Object>)_object;

                        break;
                    }
                }
            }
        }
        catch(Exception e){
            logger.error(list, e);
        }

        return result;
    }
}
