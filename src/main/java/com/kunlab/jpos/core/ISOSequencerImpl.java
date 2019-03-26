package com.kunlab.jpos.core;

import java.util.UUID;

/**
 * only generate UUID
 * @author likun
 */
public class ISOSequencerImpl implements ISOSequencer {
    @Override
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    @Override
    public int get(String counterName) {
        return 0;
    }

    @Override
    public int get(String counterName, int add) {
        return 0;
    }

    @Override
    public int set(String counterName, int value) {
        return 0;
    }
}
