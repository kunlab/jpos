package com.kunlab.jpos.iso;

import org.jpos.iso.*;

/**
 * ISOFieldPackager ASCII variable
 * Right padder with space, ASCII Interpretation, and no length prefix.
 * @author likun
 */
public class IFA_CHAR extends ISOStringFieldPackager {
    /** Used for the GenericPackager. */
    public IFA_CHAR() { super(RightTPadder.SPACE_PADDER, AsciiInterpreter.CHARSET_GBK, NullPrefixer.INSTANCE); }

    public IFA_CHAR(int len, String description) {
        super(len, description, RightTPadder.SPACE_PADDER,  AsciiInterpreter.CHARSET_GBK, NullPrefixer.INSTANCE);
    }

    @Override
    public void setLength(int len) {
        super.setLength(len);
    }
}
