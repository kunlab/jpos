package com.kunlab.jpos.exception;

/**
 * base exception extended by all exception
 * @author likun
 */
public class BaseException extends Exception {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable t) {
        super(t);
    }

    public BaseException(String message, Throwable t) {
        super(message, t);
    }
}
