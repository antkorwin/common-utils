package com.antkorwin.commonutils.validation;

/**
 * Created on 01.12.2018.
 *
 * @author Korovin Anatoliy
 */
public enum InternalErrorInfo implements ErrorInfo {

    EXCEPTION_CONSTRUCTOR_NOT_FOUND("Not found required exception constructor in guard.");

    private final String message;
    private final int base = 10000;

    InternalErrorInfo(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return base + ordinal();
    }
}
