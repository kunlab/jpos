package com.kunlab.jpos.exception;

/**
 * @author likun
 */
public class DaoException extends BaseException {

    private String code;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable t) {
        super(t);
    }

    public DaoException(String message, Throwable t) {
        super(message, t);
    }

    public DaoException(String code, String message) {
        super(code + ":" + message);
        this.code = code;
    }

    public DaoException(String code, String message, Throwable t) {
        super(code + ":" + message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
