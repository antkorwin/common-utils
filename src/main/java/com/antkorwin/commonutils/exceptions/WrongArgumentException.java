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
}
