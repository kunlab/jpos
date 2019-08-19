package com.kunlab.jpos.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 2206391776@qq.com on 2019/8/16
 */
public class DateUtil {

    public static final String FORMAT_01 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_02 = "yyyyMMddHHmmssSSS";

    public static final String FORMAT_03 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_04 = "yyyyMMddHHmmss";

    public static final String FORMAT_05 = "yyyy-MM-dd";

    public static final String FORMAT_06 = "HH:mm:ss";

    /**
     * Formats a date object
     * @param d date object to be formatted
     * @param pattern to be used for formatting
     */
    public static String formatDate (Date d, String pattern) {
        SimpleDateFormat df =
                (SimpleDateFormat) DateFormat.getDateTimeInstance();
        df.applyPattern(pattern);
        return df.format(d);
    }

    /**
     * converts a string in yyyy-MM-dd HH:mm:ss.SSS format to a Date object
     * @param s String in yyyy-MM-dd HH:mm:ss.SSS recorded
     * @return parsed Date (or null)
     */
    public static Date parse(String s) {
        return parse(s,FORMAT_01);
    }

    public static Date parse(String s, String pattern) {
        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_01);
        try {
            date = df.parse(s);
        } catch (ParseException e) {}
        return date;
    }

}
