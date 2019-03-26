package com.kunlab.jpos.model;

/**
 * @author likun
 */
public class FtpObject extends BaseObject {

    private String path;
    private String name;
    private byte[] bytes;

    public FtpObject() {}

    public FtpObject(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public FtpObject(String path, String name, byte[] bytes) {
        this.path = path;
        this.name = name;
        this.bytes = bytes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
