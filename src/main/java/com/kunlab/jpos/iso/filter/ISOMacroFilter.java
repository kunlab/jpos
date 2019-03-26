package com.kunlab.jpos.iso.filter;

import com.kunlab.jpos.core.ISOSequencer;
import com.kunlab.jpos.core.ISOSequencerImpl;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.*;
import org.jpos.util.LogEvent;

import java.util.Date;

/**
 * @author likun
 */
public class ISOMacroFilter implements ISOFilter, Configurable {

    private ISOSequencer seq;
    private Configuration cfg;
    private int dateField;
    private int traceField;
    private int[] unsetFields  = new int[0];
    private int[] validFields  = new int[0];

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;

        dateField = cfg.getInt("date", 0);
        traceField = cfg.getInt("id", 0);
        unsetFields     = ISOUtil.toIntArray (cfg.get ("unset", ""));
        validFields     = ISOUtil.toIntArray (cfg.get ("valid", ""));
        String seqName  = cfg.get ("sequencer", null);
        if(seqName != null) {
            //TODO
        } else if(seq == null) {
            seq = new ISOSequencerImpl();
        }

    }

    @Override
    public ISOMsg filter(ISOChannel channel, ISOMsg m, LogEvent evt) {
        if (dateField != 0 && traceField != 0)
            applyProps(m, dateField, traceField);
        if (validFields.length > 0)
            m = (ISOMsg) m.clone (validFields);
        if (unsetFields.length > 0)
            m.unset (unsetFields);
        return m;
    }

    private void applyProps(ISOMsg m, int dateField, int traceField) {
        m.set(dateField, ISODate.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
        m.set(traceField,seq.getUUID());
    }
}
