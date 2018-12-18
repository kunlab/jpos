package com.kunlab.jpos.iso;

import org.jpos.iso.BcdPrefixer;
import org.jpos.iso.HEXInterpreter;
import org.jpos.iso.ISOStringFieldPackager;
import org.jpos.iso.NullPadder;

/**
 * @author likun
 */
public class IFB_LLTrace extends ISOStringFieldPackager {

    /** Used for the GenericPackager. */
    public IFB_LLTrace() {
        super(NullPadder.INSTANCE, HEXInterpreter.RIGHT_PADDED, BcdPrefixer.LL);
    }

    /**
     * @param len - field len
     * @param description symbolic descrption
     */
    public IFB_LLTrace(int len, String description, boolean isLeftPadded) {
        super(len, description, NullPadder.INSTANCE,
                isLeftPadded ? HEXInterpreter.LEFT_PADDED : HEXInterpreter.RIGHT_PADDED,
                BcdPrefixer.LL);
        checkLength(len, 99);
    }

    public void setLength(int len) {
        checkLength(len, 99);
        super.setLength(len);
    }

    /** Must override ISOFieldPackager method to set the Interpreter correctly */
    public void setPad(boolean pad) {
        setInterpreter(pad ? HEXInterpreter.LEFT_PADDED : HEXInterpreter.RIGHT_PADDED);
        this.pad = pad;
    }

}
