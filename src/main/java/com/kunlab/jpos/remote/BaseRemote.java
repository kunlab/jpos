package com.kunlab.jpos.remote;

import com.kunlab.jpos.exception.RemoteException;

/**
 * @author likun
 *
 * @param <Req>  请求参数对象
 * @param <Resp> 响应参数对象
 */
public interface BaseRemote<Req, Resp> {
    public Resp execute(Req req) throws RemoteException;
}
