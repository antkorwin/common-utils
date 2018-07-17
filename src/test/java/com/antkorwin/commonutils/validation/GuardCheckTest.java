package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.BaseException;
import com.antkorwin.commonutils.exceptions.NotFoundException;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created on 17.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class GuardCheckTest {

    @Test
    public void guardWithoutErrorInfo() {

        GuardCheck.check(() -> {
                             throw new IndexOutOfBoundsException();
                         },
                         IndexOutOfBoundsException.class);
    }


    @Test(expected = AssertionError.class)
    public void guardWithWrongTypeException() {

        GuardCheck.check(() -> {
                             throw new IndexOutOfBoundsException();
                         },
                         NullPointerException.class);
    }


    @Test
    public void testGuardCheck() {

        GuardCheck.check(() -> {
                             throw new NotFoundException(TestErrorInfo.TEST_ERROR);
                         },
                         NotFoundException.class,
                         TestErrorInfo.TEST_ERROR);
    }

    @Test(expected = AssertionError.class)
    public void testGuardCheckWithWrongType() {

        GuardCheck.check(() -> {
                             throw new NotFoundException(TestErrorInfo.TEST_ERROR);
                         },
                         IndexOutOfBoundsException.class,
                         TestErrorInfo.TEST_ERROR);
    }

    @Test(expected = AssertionError.class)
    public void testGuardWithWrongErrorInfo() {

        GuardCheck.check(() -> {
                             throw new NotFoundException(TestErrorInfo.TEST_ERROR);
                         },
                         NotFoundException.class,
                         TestErrorInfo.TEST_ERROR_ARG);
    }

    @Test
    public void testRun() {
        // Arrange
        Runnable runnable = mock(Runnable.class);
        doThrow(IndexOutOfBoundsException.class).when(runnable).run();
        // Act
        GuardCheck.check(runnable, IndexOutOfBoundsException.class);
        // Asserts
        verify(runnable).run();
    }

    @Test
    public void testRunWithErroInfo() {
        // Arrange
        Runnable runnable = mock(Runnable.class);
        doThrow(new NotFoundException(TestErrorInfo.TEST_ERROR)).when(runnable).run();
        // Act
        GuardCheck.check(runnable, NotFoundException.class, TestErrorInfo.TEST_ERROR);
        // Asserts
        verify(runnable).run();
    }

    @Test
    public void testGuardWithParentExceptionType() {
        // Arrange
        Runnable runnable = mock(Runnable.class);
        doThrow(IndexOutOfBoundsException.class).when(runnable).run();
        // Act
        GuardCheck.check(runnable, RuntimeException.class);
        // Asserts
        verify(runnable).run();
    }

    @Test
    public void testGuardWithParentExceptionTypeAndErrorInfo() {
        // Arrange
        Runnable runnable = mock(Runnable.class);
        doThrow(new NotFoundException(TestErrorInfo.TEST_ERROR)).when(runnable).run();
        // Act
        GuardCheck.check(runnable, BaseException.class, TestErrorInfo.TEST_ERROR);
        // Asserts
        verify(runnable).run();
    }
}