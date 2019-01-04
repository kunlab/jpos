package com.kunlab.jpos.remote;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author likun
 *
 * @param <Req>
 * @param <Resp>
 */
public abstract class BaseRemoteImpl<Req, Resp> implements BaseRemote<Req, Resp>{
    protected Logger logger = null;

    protected String host;
    protected int port;
    protected long timeout;

    public BaseRemoteImpl() {
        logger = LogManager.getLogger(this.getClass());
    }


    public long preReq(Req req) {
        return System.currentTimeMillis();
    }

    public long preResp(Resp resp) {
        return System.currentTimeMillis();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
