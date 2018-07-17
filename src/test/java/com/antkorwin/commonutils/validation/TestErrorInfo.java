package com.antkorwin.commonutils.validation;

/**
 * Created on 17.07.2018.
 *
 * @author Korovin Anatoliy
 */
public enum TestErrorInfo implements ErrorInfo {
    TEST_ERROR("error"),
    TEST_ERROR_ARG("wrong arg");

    private static final int BASE = 1000;
    private final String msg;

    TestErrorInfo(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    @Override
    public Integer getCode() {
        return BASE + ordinal();
    }
}
