package com.kunlab.jpos.tlv;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author likun
 */
public class AsciiTLVListTest {

    @Test
    public void pack() throws Exception{
        AsciiTLVList list = new AsciiTLVList();
        list.append("42", "888888888888888");

        System.out.println(list.pack(2));
    }

    @Test
    public void unpack() throws Exception{
//        AsciiTLVList list = new AsciiTLVList();
//        list.append("42", "商户编号", "UTF-8");
//        String str = list.pack(2);
//
//        AsciiTLVList l2 = new AsciiTLVList();
//        l2.unpack(str, 0,0,"UTF-8",2, 2);
//        for(AsciiTLVMsg tlv : l2.getTags()) {
//            System.out.println(tlv.getTag());
//            System.out.println(tlv.getValue());
//        }

        String str = "114212商户编号11";
//        ByteBuffer buffer = ByteBuffer.
//        ByteBuffer suffixBuffer = ByteBuffer.wrap(str.getBytes("UTF-8"), str.getBytes("UTF-8").length - 2, str.getBytes("UTF-8").length);
        ByteBuffer suffixBuffer = ByteBuffer.wrap(str.getBytes("UTF-8"), str.getBytes("UTF-8").length - 2, 2);
        byte[] _prefix = new byte[2];
        suffixBuffer.get(_prefix);
        System.out.println(new String(_prefix, "UTF-8"));

    }
}