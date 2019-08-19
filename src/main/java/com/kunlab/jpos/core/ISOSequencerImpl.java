package com.kunlab.jpos.core;

import org.jpos.core.VolatileSequencer;

import java.util.UUID;

/**
 * only generate UUID
 * @author likun
 */
public class ISOSequencerImpl extends VolatileSequencer implements ISOSequencer {
    @Override
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

}
