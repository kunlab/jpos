package com.kunlab;

import org.jpos.iso.ISODate;

import java.util.Date;

/**
 * @author likun
 */
public class PubTest {

    public static void main(String[] args) {
        String dateStr = ISODate.formatDate(new Date(), "yyyyMMddHHmmssSSS");

        Date date = ISODate.parseISODate(dateStr);
        System.out.println(date);
        System.out.println(new Date());
    }
}
