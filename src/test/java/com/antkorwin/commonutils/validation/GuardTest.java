package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.MissingResourceException;

/**
 * Created by Korovin Anatoliy on 04.07.2018.
 *
 * @author Korovin Anatoliy
 * @version 1.0
 */
public class GuardTest {

    @Test
    public void testGuard() {
        // Arrange
        Exception actual = null;

        // Act
        try {
            Guard.check(false, NullPointerException.class);
        } catch (Exception e) {
            actual = e;
        }

        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(NullPointerException.class);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void name() throws Exception {
        Guard.check(02 + 010 == 12, IndexOutOfBoundsException.class, "ooops");
    }

    @Test
    public void testGuardWithoutThrowing() {
        Guard.check(true, NullPointerException.class);
    }

    @Test
    public void testGuardWithMessage() {
        // Arrange
        Exception actual = null;

        // Act
        try {
            Guard.check(false, NullPointerException.class, "message");
        } catch (Exception e) {
            actual = e;
        }

        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(NullPointerException.class);
        Assertions.assertThat(actual.getMessage()).isEqualTo("message");
    }

    @Test
    public void testGuardWithMessageWithoutThrowing() {
        Guard.check(true, NullPointerException.class, "message");
    }

    @Test
    public void testGuardWithCause() {
        // Arrange
        Exception cause = new IOException("101010");
        Exception actual = null;

        // Act
        try {
            Guard.check(false, IndexOutOfBoundsException.class, "message", cause);
        } catch (Exception e) {
            actual = e;
        }

        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(IndexOutOfBoundsException.class);
        Assertions.assertThat(actual.getMessage()).isEqualTo("message");
        Assertions.assertThat(actual.getCause()).isInstanceOf(IOException.class);
        Assertions.assertThat(actual.getCause().getMessage()).isEqualTo("101010");
    }

    @Test
    public void testGuardWithCauseWithoutThrowing() {
        // Arrange
        Exception cause = new IOException();
        // Act
        Guard.check(true, NullPointerException.class, "message", cause);
    }


    @Test
    public void testThrowsErrorInfo() {
        // Arrange
        Exception actual = null;

        // Act
        try {
            Guard.check(false, TestErrorInfo.TEST_ERROR);
        } catch (Exception e) {
            actual = e;
        }

        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(ConditionValidationException.class);
        Assertions.assertThat(actual.getMessage()).isEqualTo("error");
        Assertions.assertThat(((BaseException) actual).getCode()).isEqualTo(1000);
    }

    @Test
    public void testGuardWithoutTrowingErrorInfo() {
        Guard.check(true, TestErrorInfo.TEST_ERROR);
    }

    @Test
    public void testCheckExist() {
        Guard.checkArgumentExist(new Object(), TestErrorInfo.TEST_ERROR);
    }

    @Test
    public void testCheckExistWithThrowsErrorInfo() {
        // Arrange
        Exception actual = null;

        // Act
        try {
            Guard.checkArgumentExist(null, TestErrorInfo.TEST_ERROR_ARG);
        } catch (Exception e) {
            actual = e;
        }

        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(WrongArgumentException.class);
        Assertions.assertThat(actual.getMessage()).isEqualTo("wrong arg");
        Assertions.assertThat(((BaseException) actual).getCode()).isEqualTo(1001);
    }

    @Test
    public void testThrowsSelectedExceptionWithErrorInfo() {
        // Arrange
        Exception actual = null;
        // Act
        try {
            Guard.check(false, NotFoundException.class, TestErrorInfo.TEST_ERROR);
        } catch (Exception e) {
            actual = e;
        }
        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(actual.getMessage())
                  .isEqualTo(TestErrorInfo.TEST_ERROR.getMessage());
        Assertions.assertThat(((BaseException) actual).getCode())
                  .isEqualTo(TestErrorInfo.TEST_ERROR.getCode());
    }

    @Test
    public void testCheckWithExceptionNotInheritedFromBaseException() {
        // Arrange
        Exception actual = null;
        // Act
        try {
            Guard.check(false,
                        RuntimeException.class,  // not inherit from the BaseException
                        TestErrorInfo.TEST_ERROR);
        } catch (Exception e) {
            actual = e;
        }
        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(RuntimeException.class);
        Assertions.assertThat(actual.getMessage())
                  .isEqualTo(TestErrorInfo.TEST_ERROR.getMessage());
    }

    @Test
    public void testCheckWithWrongTypeException() {
        // Arrange
        Exception actual = null;
        // Act
        try {
            Guard.check(false,
                        MissingResourceException.class,  //exception without String argument constructor
                        TestErrorInfo.TEST_ERROR);
        } catch (Exception e) {
            actual = e;
        }
        // Asserts
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(InternalException.class);
        Assertions.assertThat(actual.getMessage())
                  .isEqualTo(InternalErrorInfo.EXCEPTION_CONSTRUCTOR_NOT_FOUND.getMessage());
    }
}