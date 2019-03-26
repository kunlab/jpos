package com.kunlab.jpos.iso.filter;

import org.jpos.iso.ISODate;
import org.junit.Test;

import java.util.Date;

/**
 * @author likun
 */
public class ISOMacroFilterTest {

    @Test
    public void setConfiguration() {
        String date = ISODate.formatDate(new Date(), "yyyyMMddHHmmssSSS");
        System.out.println(date);


    }

    @Test
    public void filter() {
    }
}