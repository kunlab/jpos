package com.kunlab.jpos.iso.filter;

import com.kunlab.jpos.core.BaseSequencer;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOMsg;
import org.jpos.util.LogEvent;

/**
 * useful to set sequencers, date, unset iso fields, etc.
 * @author likun
 */
public class MacroFilter implements ISOFilter, Configurable {

    BaseSequencer seq;
    Configuration cfg;

    @Override
    public ISOMsg filter(ISOChannel channel, ISOMsg m, LogEvent evt) throws VetoException {
        return m;
    }

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;
    }
}
