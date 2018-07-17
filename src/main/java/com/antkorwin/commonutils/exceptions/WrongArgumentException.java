package com.antkorwin.commonutils.exceptions;

import com.antkorwin.commonutils.validation.ErrorInfo;

/**
 * Created on 05.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class WrongArgumentException extends BaseException {

    public WrongArgumentException(String message, Integer code) {
        super(message, code);
    }

    public WrongArgumentException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public WrongArgumentException(Throwable cause) {
        super(cause);
    }

    public WrongArgumentException(String message, Integer code, Throwable cause) {
        super(message, code, cause);
    }

    public WrongArgumentException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
