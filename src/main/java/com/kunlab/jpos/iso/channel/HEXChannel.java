package com.kunlab.jpos.iso.channel;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * sends a couple of bytes indicating message length
 *
 * @author likun
 */
public class HEXChannel extends BaseChannel {
    public HEXChannel() {
        super();
    }

    /**
     * Construct client ISOChannel
     * @param host  server TCP Address
     * @param port  server port number
     * @param p     an ISOPackager
     * @param TPDU  an optional raw header (i.e. TPDU)
     * @see ISOPackager
     */
    public HEXChannel(String host, int port, ISOPackager p, byte[] TPDU) {
        super(host, port, p);
        this.header = TPDU;
    }

    /**
     * Construct server ISOChannel
     * @param p     an ISOPackager
     * @param TPDU  an optional raw header (i.e. TPDU)
     * @exception IOException
     * @see ISOPackager
     */
    public HEXChannel(ISOPackager p, byte[] TPDU) throws IOException {
        super(p);
        this.header = TPDU;
    }

    /**
     * constructs server ISOChannel associated with a Server Socket
     * @param p     an ISOPackager
     * @param TPDU  an optional raw header (i.e. TPDU)
     * @param serverSocket where to accept a connection
     * @exception IOException
     * @see ISOPackager
     */
    public HEXChannel(ISOPackager p, byte[] TPDU, ServerSocket serverSocket)
            throws IOException
    {
        super(p, serverSocket);
        this.header = TPDU;
    }

    protected void sendMessageLength(int len) throws IOException {
        serverOut.write (len >> 8);
        serverOut.write (len);
    }

    protected int getMessageLength() throws IOException, ISOException {
        byte[] b = new byte[2];
        serverIn.readFully(b,0,2);
        return ISOUtil.byte2int(b);
    }

    public void setHeader(String header) { super.setHeader(ISOUtil.str2bcd(header, false)); }
}
