package com.antkorwin.commonutils.validation;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Assert;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Sergey Vdovin
 * @author Korovin Anatolii
 * @version 1.0
 */
public class GuardCheck {

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

    /**
     * Check throw exception type and details, when run supplied action.
     * Verified only top level of thrown exception(not check nested cause of exception).
     *
     * @param action         method witch throw exception
     * @param exceptionClass expected exception class
     * @param errorInfo      expected exception details
     */
    public static void check(Runnable action, Class<? extends Throwable> exceptionClass, ErrorInfo errorInfo) {

        try {
            action.run();

            Assert.fail(String.format("No exception was thrown, but expected: %s(%s)",
                                      exceptionClass.getSimpleName(),
                                      errorInfo));
        } catch (Throwable cause) {
            if (cause instanceof RuntimeException && exceptionClass.isInstance(cause)) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getMessage());
            } else {
                Assert.fail(String.format("Bad exception type.\n\tExpected: %s,\n\tRecieved: %s \nStackTrace:",
                                          exceptionClass.getName(),
                                          cause.getClass().getName(),
                                          ExceptionUtils.getStackTrace(cause)));
            }
        }
    }


    /**
     * Check the exception type thrown by the action.
     * With the checking a lower level of exception, by the chain of causes.
     *
     * @param action         method which throws an exception
     * @param exceptionClass expected exception class
     * @param errorInfo      expected exception details
     */
    public static void checkNested(Runnable action, Class<? extends Throwable> exceptionClass, ErrorInfo errorInfo) {

        try {
            action.run();
            Assert.fail(String.format("No exception was thrown, but expected: %s(%s)",
                                      exceptionClass.getSimpleName(),
                                      errorInfo));
        } catch (Throwable t) {

            Throwable cause = t;

            if (cause instanceof RuntimeException && exceptionClass.isInstance(cause)) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getMessage());
                return;
            }

            // find source of exception (top level by cause->...->cause)
            while (cause.getCause() != null) cause = cause.getCause();

            if (cause instanceof RuntimeException && exceptionClass.isInstance(cause.getCause())) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getCause().getMessage());
            } else if (exceptionClass.isInstance(cause)) {
                Assert.assertEquals(errorInfo.getMessage(), cause.getMessage());
            } else {
                Assert.fail(String.format("Bad exception type.\n\tExpected: %s,\n\tRecieved: %s",
                                          exceptionClass.getName(),
                                          cause.getClass().getName()));
            }
        }
    }
}
