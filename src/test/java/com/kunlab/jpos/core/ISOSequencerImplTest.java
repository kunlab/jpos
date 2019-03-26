package com.kunlab.jpos.core;

import org.junit.Test;

/**
 * @author likun
 */
public class ISOSequencerImplTest {

    @Test
    public void getUUID() {
        ISOSequencer seq = new ISOSequencerImpl();
        System.out.println(seq.getUUID());
    }
}