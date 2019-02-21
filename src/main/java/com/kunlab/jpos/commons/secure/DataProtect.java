package com.kunlab.jpos.commons.secure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likun
 */
public class DataProtect {

    /**
     * 全字符隐藏
     * @param value
     * @return
     */
    public static String all(String value) {
        return value == null ? null : hidden(value, 0, 0, '*');
    }

    /**
     * 银行卡号脱敏
     * @param no
     * @return
     */
    public static String bankCard(String no) {
        return hidden(no, 6, 4, '*');
    }

    /**
     * 证件号脱敏
     * @param no
     * @return
     */
    public static String idCard(String no) {
        return hidden(no, 1, 1, '*');
    }

    /**
     * 手机号
     * @param no
     * @return
     */
    public static String mobile(String no) {
        return hidden(no, 3, 4, '*');
    }

    private static String trim(String value) {
        return (value == null ? "" : value).trim();
    }

    private static String hidden(String no, int b, int e, char c) {
        StringBuffer result = new StringBuffer();

        no = trim(no);
        if(no.length() > (b + e))
        {
            result.append(no.substring(0, b));
            int n = 0;
            while(n < (no.length() - (b + e)))
            {
                result.append(c);
                n ++;
            }
            result.append(no.substring(no.length() - e));
        }
        else
        {
            result.append(no);
        }

        return result.toString();
    }

    /**
     * 数据对象脱敏
     * @param o
     * @return
     */
    public static <O> String toString(O o) {
        StringBuffer sb = new StringBuffer();
        sb.append(o.getClass().getName());
        sb.append("|");

        Map<String, DataProtectEnum> annotationMap = new HashMap<String, DataProtectEnum>();

        Field[] fields = o.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            if(field.getAnnotation(DataProtectType.class) != null)
            {
                annotationMap.put(field.getName().toLowerCase(), field.getAnnotation(DataProtectType.class).value());
            }
        }

        Method[] methods = o.getClass().getMethods();
        for(Method method : methods)
        {
            try{
                method.setAccessible(true);

                String methodName = method.getName();
                if((methodName.indexOf("get") != -1) && !("getClass".equals(methodName)))
                {
                    String name = methodName.substring(3).toLowerCase();
                    Object value = method.invoke(o, new Object[0]);

                    DataProtectEnum type = annotationMap.get(name);
                    if(type != null & value != null)
                    {
                        switch(type)
                        {
                            case OBJECT :
                                value = toString(value);
                                break;
                            case ALL :
                                value = all(value.toString());
                                break;
                            case BANK_CARD :
                                value = bankCard(value.toString());
                                break;
                            case ID_CARD :
                                value = idCard(value.toString());
                                break;
                            case MOBILE :
                                value = mobile(value.toString());
                                break;
                            default :
                                break;
                        }
                    }

                    sb.append("[");
                    sb.append(methodName.substring(3, 4).toLowerCase());
                    sb.append(methodName.substring(4));
                    sb.append(":");
                    sb.append(value == null ? null : value.toString());
                    sb.append("]");
                }
            }
            catch(Exception e){
                sb.append("[");
                sb.append(method);
                sb.append(":");
                sb.append(e.getMessage());
                sb.append("]");
            }
        }

        return sb.toString();
    }
}
