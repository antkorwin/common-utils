package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.ConditionValidationException;
import com.antkorwin.commonutils.exceptions.NotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 */
public class Guard {

    public static void checkEntityExist(Object entity, ErrorInfo errorInfo) {
        if (entity == null) throw new NotFoundException(errorInfo);
    }

    public static void check(boolean condition, ErrorInfo errorInfo) {
        if (!condition) throw new ConditionValidationException(errorInfo);
    }

    public static void check(boolean condition, Class<? extends RuntimeException> exceptionClass) {
        if (!condition) {
            try {
                throw exceptionClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void check(boolean condition, Class<? extends RuntimeException> exceptionClass,
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
}
