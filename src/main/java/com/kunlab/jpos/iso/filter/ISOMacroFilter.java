package com.kunlab.jpos.iso.filter;

import com.kunlab.jpos.core.ISOSequencer;
import com.kunlab.jpos.core.ISOSequencerImpl;
import com.kunlab.jpos.util.DateUtil;
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
        traceField = cfg.getInt("trace", 0);
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
        applyProps(m);
        if (validFields.length > 0)
            m = (ISOMsg) m.clone (validFields);
        if (unsetFields.length > 0)
            m.unset (unsetFields);
        return m;
    }

    private ISOMsg applyProps(ISOMsg m) {
        if(dateField != 0)
            m.set(dateField, DateUtil.formatDate(new Date(), DateUtil.FORMAT_02));
        if(traceField != 0)
            m.set(traceField,seq.getUUID());
        return m;
    }
}
