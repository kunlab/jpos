package com.kunlab.jpos.service;

import com.kunlab.jpos.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 逻辑服务抽象
 * 所有逻辑service需要继承BaseServiceImpl
 *
 * @author likun
 *
 * @param <Req>
 * @param <Resp>
 */
public abstract  class BaseServiceImpl<Req, Resp> implements BaseService<Req, Resp> {
    protected Logger logger = null;

    public BaseServiceImpl() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public Resp execute(Req req) throws ServiceException {
        return null;
    }
}
