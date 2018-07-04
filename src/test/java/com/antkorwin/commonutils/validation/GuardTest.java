package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.ConditionValidationException;
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
    }

    @Test
    public void testGuardWithoutTrowingErrorInfo() {
        Guard.check(true, TestErrorInfo.TEST_ERROR);
    }

    private enum TestErrorInfo implements ErrorInfo {
        TEST_ERROR("error");

        private static final int BASE = 1000;
        private final String msg;

        TestErrorInfo(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMessage() {
            return this.msg;
        }

        @Override
        public Integer getCode() {
            return BASE + ordinal();
        }
    }
}