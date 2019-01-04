package com.kunlab.jpos.exception;

/**
 * @author likun
 */
public class RemoteException extends BaseException {

    private String code;

    public RemoteException(String message) {
        super(message);
    }

    public RemoteException(Throwable t) {
        super(t);
    }

    public RemoteException(String message, Throwable t) {
        super(message, t);
    }

    public RemoteException(String code, String message) {
        super(code + ":" + message);
        this.code = code;
    }

    public RemoteException(String code, String message, Throwable t) {
        super(code + ":" + message, t);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
