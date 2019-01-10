package com.kunlab.jpos.tlv;

import org.junit.Test;

/**
 * @author likun
 */
public class TLVMsgTest {

    @Test
    public void getTLV() throws Exception{
        TLVMsg t1 = new TLVMsg("42","商户编号", "UTF-8");
        System.out.println(t1.getTLV(2));
    }
}