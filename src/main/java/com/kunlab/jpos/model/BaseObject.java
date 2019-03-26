package com.kunlab.jpos.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 存在get/set方法的对象可以继承BaseObject
 * @author likun
 */
public class BaseObject implements Serializable{

    /**
     * 格式化所有参数为String类型
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append("|");

        try{
            sb.append(JSON.toJSONString(this));
        }
        catch(Exception e){
            sb.append(e.getMessage());
            sb.append("|");

            Method[] methods = this.getClass().getMethods();
            for(Method method : methods)
            {
                try{
                    method.setAccessible(true);

                    String methodName = method.getName();
                    if((methodName.contains("get")) && !("getClass".equals(methodName)))
                    {
                        String name = methodName.substring(3).toLowerCase();
                        Object value = method.invoke(this);

                        sb.append("[");
                        sb.append(name);
                        sb.append(":");
                        sb.append(value == null ? null : value.toString());
                        sb.append("]");
                    }
                }
                catch(Exception _e){
                    sb.append("[");
                    sb.append(method);
                    sb.append(":");
                    sb.append(_e.getMessage());
                    sb.append("]");
                }
            }
        }

        return sb.toString();
    }

}
