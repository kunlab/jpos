package com.kunlab.jpos.util;

import org.junit.Test;

import java.util.Date;

/**
 * Created by 2206391776@qq.com on 2019/8/16
 */
public class DateUtilTest {

    @Test
    public void formatDate() {
        System.out.println(DateUtil.formatDate(new Date(), DateUtil.FORMAT_01));
    }

    @Test
    public void parse() {
        Date date = DateUtil.parse("2019-08-16 11:54:17.830");
        System.out.println(date);
    }
}