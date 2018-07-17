package com.antkorwin.commonutils.validation;


import org.junit.Assert;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Sergey Vdovin
 * @author Korovin Anatolii
 * @version 1.0
 */
public class GuardCheck {


    public static void check(Runnable action, Class<? extends Throwable> expectedExceptionClass, ErrorInfo errorInfo) {

        boolean fail = false;
        try {
            action.run();
            fail = true;
        } catch (Throwable t) {
            // находим первопричину
            Throwable cause = t;
            while (cause.getCause() != null) cause = cause.getCause();

            if (isSameException(expectedExceptionClass, cause)) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getMessage());
            } else if (expectedExceptionClass.isInstance(cause)) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getMessage());
            } else {
                Assert.fail(String.format("Bad exception type.\n\tExpected: %s,\n\tRecieved: %s",
                                          expectedExceptionClass.getName(),
                                          cause.getClass().getName()));
            }
        }

        if (fail) Assert.fail(String.format("No exception was thrown, but expected: %s(%s)",
                                            expectedExceptionClass.getSimpleName(),
                                            errorInfo));
    }


    public static void check(Runnable action, Class<? extends Throwable> expectedExceptionClass) {
        boolean fail = false;
        try {
            action.run();
            fail = true;
        } catch (Throwable t) {
            // находим первопричину
            Throwable cause = t;
            while (cause.getCause() != null) cause = cause.getCause();

            if (!isSameException(expectedExceptionClass, cause)) {
                Assert.fail(String.format("Bad exception type.\n\tExpected: %s,\n\tRecieved: %s",
                                          expectedExceptionClass.getName(),
                                          cause.getClass().getName()));
            }
        }

        if (fail) Assert.fail(String.format("No exception was thrown, but expected: %s",
                                            expectedExceptionClass.getSimpleName()));
    }


    private static boolean isSameException(Class<? extends Throwable> expectedClass, Throwable cause) {
        return expectedClass.isInstance(cause);
    }
}
