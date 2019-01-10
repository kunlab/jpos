package com.kunlab.jpos.tlv;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOException;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考TLV规范，T and L 为固定长度
 * @author likun
 */
public class TLVList implements Serializable {

    private List<TLVMsg> tags = new ArrayList<>();
    private String prefix;
    private String suffix;


    public void append(TLVMsg tlvToAppend) {
        tags.add(tlvToAppend);
    }

    public void append(String tag, String value) {
        this.tags.add(new TLVMsg(tag, value));
    }

    public void append(String tag, String value, String charset) {
        tags.add(new TLVMsg(tag, value, charset));
    }

    public void deleteByTag(String tag) {
        List<TLVMsg> t = new ArrayList<>();
        for(TLVMsg tlv2 : tags) {
            if(tlv2.getTag().equals(tag))
                t.add(tlv2);
        }

        tags.removeAll(t);
    }

    public void deleteByIndex(int index) {
        tags.remove(index);
    }

    public List<TLVMsg> getTags() {
        return tags;
    }

    public TLVMsg find(String tag) {
        for(TLVMsg tlv : tags) {
            if(tlv.getTag().equals(tag))
                return tlv;
        }

        return null;
    }

    public String pack(int length) throws Exception{
        StringBuffer sb = new StringBuffer();

        if(StringUtils.isNotBlank(prefix)) sb.append(prefix);

        for(TLVMsg tlv : tags) {
            sb.append(tlv.getTLV(length));
        }

        if(StringUtils.isNotBlank(suffix)) sb.append(suffix);

        return sb.toString();
    }


    public void unpack(String str, int tagLength, int lenLength) throws Exception {
        unpack(str, 0, 0, null, tagLength, lenLength);
    }

    public void unpack(String str, int offsetRight, int offsetLeft, String charset, int tagLength, int lenLength) throws Exception {
        offsetLeft = offsetLeft < 0 ? 0 : offsetLeft;
        offsetRight = offsetRight < 0 ? 0 : offsetRight;

        if(charset == null) {
            CharBuffer prefixBuffer = CharBuffer.wrap(str, 0, offsetRight);
            char[] _prefix = new char[offsetRight];
            prefixBuffer.get(_prefix);
            this.prefix = new String(_prefix);

            CharBuffer suffixBuffer = CharBuffer.wrap(str, str.length() - offsetLeft, str.length());
            char[] _suffix = new char[offsetLeft];
            suffixBuffer.get(_suffix);
            this.suffix = new String(_suffix);

            CharBuffer buffer = CharBuffer.wrap(str, offsetRight, str.length() - offsetLeft - offsetRight);

            while(buffer.hasRemaining()) {
                char[] _tag = new char[tagLength];
                buffer.get(_tag);
                String tag = new String(_tag);

                char[] _length = new char[lenLength];
                buffer.get(_length);
                String length = new String(_length);
                int len = Integer.parseInt(length);
                if(len > buffer.remaining()) {
                    throw new ISOException("BAD TLV FORMAT - tag (" + tag + ") length (" + length + ") exceeds available data.");
                }

                char[] _value = new char[len];
                buffer.get(_value);
                String value = new String(_value);

                append(tag, value);
            }
        } else {
            ByteBuffer prefixBuffer = ByteBuffer.wrap(str.getBytes(charset), 0, offsetRight);
            byte[] _prefix = new byte[offsetRight];
            prefixBuffer.get(_prefix);
            this.prefix = new String(_prefix, charset);

            ByteBuffer suffixBuffer = ByteBuffer.wrap(str.getBytes(charset), str.getBytes(charset).length - offsetLeft, offsetLeft);
            byte[] _suffix = new byte[offsetLeft];
            suffixBuffer.get(_suffix);
            this.suffix = new String(_suffix, charset);

            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes(charset), offsetRight, str.getBytes(charset).length - offsetLeft - offsetRight);

            while(buffer.hasRemaining()) {
                byte[] _tag = new byte[tagLength];
                buffer.get(_tag);
                String tag = new String(_tag, charset);

                byte[] _length = new byte[lenLength];
                buffer.get(_length);
                String length = new String(_length, charset);
                int len = Integer.parseInt(length);
                if(len > buffer.remaining()) {
                    throw new ISOException("BAD TLV FORMAT - tag (" + tag + ") length (" + length + ") exceeds available data.");
                }

                byte[] _value = new byte[len];
                buffer.get(_value);
                String value = new String(_value, charset);

                append(tag, value, charset);
            }
        }
    }

}
