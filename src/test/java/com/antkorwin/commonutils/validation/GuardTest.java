package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.BaseException;
import com.antkorwin.commonutils.exceptions.ConditionValidationException;
import com.antkorwin.commonutils.exceptions.WrongArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Korovin Anatolii on 04.07.2018.
 *
 * @author Korovin Anatolii
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
        Guard.check( 02 + 010 == 12, IndexOutOfBoundsException.class, "ooops");
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
}