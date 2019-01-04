package com.kunlab.jpos.remote;

import com.kunlab.jpos.exception.RemoteException;
import com.kunlab.jpos.util.enums.ResultEnum;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;

/**
 * tcp/ip  ISO8583报文
 * @author likun
 */
public class ISOChannelRemote extends BaseRemoteImpl<ISOMsg, ISOMsg> {

    private BaseChannel channel;
    private QMUX qmux;

    public ISOChannelRemote() {}

    public ISOChannelRemote(String channel_name, String mux_name) {
        super();
        try {
            this.channel = (BaseChannel) NameRegistrar.getIfExists("channel." + channel_name);
            if(channel == null)
                channel = (BaseChannel) NameRegistrar.get("channel." + channel_name + "0");
            host = channel.getHost();
            port = channel.getPort();
            timeout = channel.getTimeout();
            this.qmux = (QMUX) QMUX.getMUX(mux_name);
        } catch (NameRegistrar.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ISOMsg execute(ISOMsg isoMsg) throws RemoteException {
        ISOMsg resp = null;

        long time = preReq(isoMsg);
        logger.info("{}:{} | {}ms", host, port, time);

        try {
            ISOMsg req = (ISOMsg) isoMsg.clone();
            resp = qmux.request(req, timeout);
        } catch (ISOException e) {
            logger.error(e);
            throw new RemoteException(ResultEnum.RS_96.getCode(), ResultEnum.RS_96.getDesc(), e);
        } finally {
            logger.info("remote finish, {}ms", new Object[]{preResp(resp) - time});
        }

        return resp;
    }


    public BaseChannel getChannel() {
        return channel;
    }

    public void setChannel(BaseChannel channel) {
        this.channel = channel;
    }

    public QMUX getQmux() {
        return qmux;
    }

    public void setQmux(QMUX qmux) {
        this.qmux = qmux;
    }
}
