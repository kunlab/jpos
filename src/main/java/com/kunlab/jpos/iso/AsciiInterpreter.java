package com.kunlab.jpos.iso;

import org.jpos.iso.ISOException;
import org.jpos.iso.Interpreter;

import java.nio.charset.Charset;

/**
 * Implements ASCII Interpreter. Strings are converted to and from ASCII bytes.
 * @author likun
 */
public class AsciiInterpreter implements Interpreter {
    private Charset charset;

    public static final AsciiInterpreter CHARSET_ISO_8859_1 = new AsciiInterpreter("ISO-8859-1");
    public static final AsciiInterpreter CHARSET_GBK = new AsciiInterpreter("GBK");
    public static final AsciiInterpreter CHARSET_UTF_8 = new AsciiInterpreter("UTF-8");

    public AsciiInterpreter(String charset) {
        this.charset = Charset.forName(charset);
    }

    @Override
    public void interpret(String data, byte[] b, int offset) throws ISOException {
        System.arraycopy(data.getBytes(charset), 0, b, offset, data.length());
    }

    @Override
    public String uninterpret(byte[] rawData, int offset, int length) throws ISOException {
        byte[] ret = new byte[length];
        System.arraycopy(rawData, offset, ret, 0, length);
        return new String(ret, charset);
    }

    @Override
    public int getPackedLength(int nDataUnits) {
        return nDataUnits;
    }
}
