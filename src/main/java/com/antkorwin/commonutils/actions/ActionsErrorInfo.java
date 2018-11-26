package com.antkorwin.commonutils.actions;

import com.antkorwin.commonutils.validation.ErrorInfo;

/**
 * Created on 23.07.2018.
 *
 * @author Korovin Anatoliy
 */
public enum ActionsErrorInfo implements ErrorInfo {

    ILLEGAL_ARGUMENTS_VALUE("Ilegal argument value."),
    ACTION_ARGUMENT_IS_MANDATORY("action argument is mandotory.");

    private static final int BASE = 10;
    private final String message;

    ActionsErrorInfo(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return BASE + ordinal();
    }
}
