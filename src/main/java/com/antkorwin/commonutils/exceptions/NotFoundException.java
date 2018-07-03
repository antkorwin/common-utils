package com.antkorwin.commonutils.exceptions;


import com.antkorwin.commonutils.validation.ErrorInfo;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 * @version 1.0
 */
public class NotFoundException extends BaseException {

    public NotFoundException(String message, Integer code) {
        super(message, code);
    }

    public NotFoundException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
