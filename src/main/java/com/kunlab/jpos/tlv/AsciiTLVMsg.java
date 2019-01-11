package com.kunlab.jpos.tlv;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOUtil;

import java.io.Serializable;

/**
 * @author likun
 */
public class AsciiTLVMsg implements Serializable {

    private String tag;
    private String value;
    private String charset;


    public AsciiTLVMsg() {
        super();
    }

    public AsciiTLVMsg(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public AsciiTLVMsg(String tag, String value, String charset) {
        this.tag = tag;
        this.value = value;
        this.charset = charset;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    protected String getL(int length) throws Exception {
        return StringUtils.isBlank(charset)
                ? ISOUtil.padleft(Integer.toString(this.value.length()), length, '0')
                : ISOUtil.padleft(Integer.toString(this.value.getBytes(charset).length), length, '0');
    }

    public String getTLV(int length) throws Exception{
        return this.tag + getL(length) + this.value;
    }

}
