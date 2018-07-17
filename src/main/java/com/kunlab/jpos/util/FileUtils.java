package com.kunlab.jpos.util;

import java.io.*;
import java.util.Properties;

/**
 * 文件操作工具
 */
public class FileUtils {

    /**
     * 文件转化为字符串
     * @param f 文件
     * @return String file context or null
     */
    public static String fileToStr(File f) {
        String str = null;
        InputStream in = null;
        try {
            in = new FileInputStream(f);
            int len = in.available();
            byte[] buffer = new byte[len];
            in.read(buffer);
            str = new String(buffer);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("找不到文件，请检查文件路径：" + f.getAbsolutePath(), e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    /**
     * 文件转化为字符串
     * @param pathname 文件路径名
     * @return String file context or null
     */
    public static String fileToStr(String pathname) {
        File f = new File(pathname);
        return fileToStr(f);
    }

    /**
     * 文件转化为字符串
     * @param parent 文件所在文件夹
     * @param child 文件名
     * @return String file context or null
     */
    public static String fileToStr(String parent, String child) {
        File f = new File(parent, child);
        return fileToStr(f);
    }

    /**
     * 解析文件，替换特殊字符
     * @param f 待解析文件
     * @param regex 正则表达式
     * @param prop 参数值
     * @return 文件解析后的字符串表示
     */
    public static String decorateFile(File f, String regex, Properties prop) {
        String str = fileToStr(f);
        return StringUtil.decorateString(str, regex, prop);
    }

    /**
     * 解析文件，替换特殊字符
     * @param pathname 文件路径名称
     * @param regex 正则表达式
     * @param prop 参数值
     * @return 文件解析后的字符串表示
     */
    public static String decorateFile(String pathname, String regex, Properties prop) {
        String str = fileToStr(pathname);
        return StringUtil.decorateString(str, regex, prop);
    }

    /**
     * 解析文件，替换特殊字符
     * @param parent 文件上级目录
     * @param child 文件名
     * @param regex 正则表达式
     * @param prop 参数值
     * @return 文件解析后的字符串表示
     */
    public static String decorateFile(String parent, String child, String regex, Properties prop) {
        String str = fileToStr(parent, child);
        return StringUtil.decorateString(str, regex, prop);
    }
}
