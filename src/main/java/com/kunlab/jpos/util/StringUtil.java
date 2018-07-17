package com.kunlab.jpos.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 */
public class StringUtil {

    /**
     * 按照正则表达式解析字符串
     * @param str
     * @param regex 正则表达式
     * @param prop 参数值
     * @return str or null
     */
    public static String decorateString(String str, String regex, Properties prop) {
        StringBuffer sb = new StringBuffer(); //存储替换过的内容
        if(str == null) return str;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            String name = matcher.group(1);
            String value = System.getProperty(name) == null ? prop.getProperty(name) : System.getProperty(name);
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb); //将剩余的字符串补上

        return sb.toString();
    }
}
