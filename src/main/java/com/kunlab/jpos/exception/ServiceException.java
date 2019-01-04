package com.kunlab.jpos.exception;

/**
 * @author likun
 */
public class ServiceException extends BaseException {

    private String code;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable t) {
        super(t);
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }

    public ServiceException(String code, String message) {
        super(code + ":" + message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable t) {
        super(code + ":" + message, t);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
