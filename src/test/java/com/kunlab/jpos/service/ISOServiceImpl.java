package com.kunlab.jpos.service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

/**
 * @author likun
 */
public class ISOServiceImpl extends BaseServiceImpl<ISOMsg, ISOSource> implements ISOService{

    @Override
    public void execute(ISOMsg msg, ISOSource source) throws Exception {
        ISOMsg resp = (ISOMsg) msg.clone();

        resp.setDirection(ISOMsg.OUTGOING);
        resp.setResponseMTI();
        resp.set(39, "00");

        source.send(resp);
    }
}