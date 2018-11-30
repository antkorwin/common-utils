package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.antkorwin.commonutils.validation.InternalErrorInfo.EXCEPTION_CONSTRUCTOR_NOT_FOUND;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 */
public class Guard {

    public static void checkEntityExist(Object entity,
                                        ErrorInfo errorInfo) {

        if (entity == null) throw new NotFoundException(errorInfo);
    }

    public static void checkArgumentExist(Object argument,
                                          ErrorInfo errorInfo) {

        if (argument == null) throw new WrongArgumentException(errorInfo);
    }

    public static void check(boolean condition,
                             ErrorInfo errorInfo) {

        if (!condition) throw new ConditionValidationException(errorInfo);
    }

    public static void check(boolean condition,
                             Class<? extends RuntimeException> exceptionClass) {
        if (!condition) {
            try {
                throw exceptionClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void check(boolean condition,
                             Class<? extends RuntimeException> exceptionClass,
                             String message) {
        if (!condition) {
            try {
                Constructor<?> ctor = exceptionClass.getConstructor(String.class);
                RuntimeException object = (RuntimeException) ctor.newInstance(new Object[]{message});
                throw object;
            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InstantiationException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void check(boolean condition,
                             Class<? extends RuntimeException> exceptionClass,
                             String message,
                             Exception cause) {
        if (!condition) {
            try {
                Constructor<?> ctor = exceptionClass.getConstructor(String.class);
                RuntimeException object = (RuntimeException) ctor.newInstance(new Object[]{message});
                object.initCause(cause);
                throw object;
            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InstantiationException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check condition, throws exception when condition not equal "true"
     *
     * @param condition condition (must be true)
     * @param exceptionType exception class that will throw from guard (must be inherit from the BaseException)
     * @param errorInfo error details for exception
     */
    public static <T extends RuntimeException> void check(boolean condition, Class<T> exceptionType, ErrorInfo errorInfo) {

        if (!condition) {
            RuntimeException exception;
            try {
                if (BaseException.class.isAssignableFrom(exceptionType)) {
                    exception = exceptionType.getConstructor(ErrorInfo.class).newInstance(errorInfo);
                } else {
                    exception = exceptionType.getConstructor(String.class).newInstance(errorInfo.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalException(EXCEPTION_CONSTRUCTOR_NOT_FOUND);
            }
            throw exception;
        }
    }
}
