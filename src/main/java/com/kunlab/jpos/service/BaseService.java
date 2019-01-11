package com.kunlab.jpos.service;

import com.kunlab.jpos.exception.ServiceException;

/**
 * @author likun
 *
 * @param <Req>  请求参数对象
 * @param <Resp> 响应参数对象
 */
public interface BaseService<Req, Resp> {
    public Resp execute(Req req) throws ServiceException;
}
