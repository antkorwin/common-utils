package com.antkorwin.commonutils.exceptions;


import com.antkorwin.commonutils.validation.ErrorInfo;
import lombok.Getter;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 * @version 1.0
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public BaseException(ErrorInfo errorInfo){
        this(errorInfo.getMessage(), errorInfo.getCode());
    }
}
