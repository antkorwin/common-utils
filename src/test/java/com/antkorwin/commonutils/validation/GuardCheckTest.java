package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.NotFoundException;
import org.junit.Test;

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
}