package com.kunlab.jpos.core;


import java.util.UUID;

/**
 * @author likun
 */
public class UUIDSequencer implements BaseSequencer {

    @Override
    public String getUUID() {
        return UUID.randomUUID().toString().toUpperCase();
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
