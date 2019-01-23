package com.kunlab.jpos.service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

/**
 * @author likun
 */
public interface ISOService extends BaseService<ISOMsg, ISOSource> {
    public void execute(ISOMsg msg, ISOSource source) throws Exception;
}
