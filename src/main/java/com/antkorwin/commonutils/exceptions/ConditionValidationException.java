package com.antkorwin.commonutils.exceptions;


import com.antkorwin.commonutils.validation.ErrorInfo;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 * @version 1.0
 */
public class ConditionValidationException extends BaseException {

    public ConditionValidationException(String message, Integer code) {
        super(message, code);
    }

    public ConditionValidationException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public ConditionValidationException(Throwable cause) {
        super(cause);
    }

    public ConditionValidationException(String message, Integer code, Throwable cause) {
        super(message, code, cause);
    }

    public ConditionValidationException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
