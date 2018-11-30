package com.antkorwin.commonutils.exceptions;

import com.antkorwin.commonutils.validation.ErrorInfo;

/**
 * Created on 01.12.2018.
 *
 * @author Korovin Anatoliy
 */
public class InternalException extends BaseException {

    public InternalException(String message, Integer code) {
        super(message, code);
    }

    public InternalException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String message, Integer code, Throwable cause) {
        super(message, code, cause);
    }

    public InternalException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
