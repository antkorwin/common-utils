package com.antkorwin.commonutils.validation;

import org.junit.Test;

/**
 * Created on 18.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class ErrorInfoCollisionDetectorTest {

    @Test
    public void assertInPackage() {

        // Arrange
        String packageNameForScan = ErrorInfoCollisionDetector.class.getPackage().getName();

        // Act & assert
        GuardCheck.check(() -> ErrorInfoCollisionDetector.assertInPackage(packageNameForScan),
                         AssertionError.class);
    }


    @ErrorInfoUnique
    public enum TestInfo implements ErrorInfo {

        FIRST_1("1111"),
        SECOND_2("2222");

        private static final int BASE = 5000;
        private final String message;

        TestInfo(String msg) {
            this.message = msg;
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public Integer getCode() {
            return BASE + ordinal();
        }
    }

    @ErrorInfoUnique
    public enum AnotherTestInfo implements ErrorInfo {

        FIRST_A("AAA"),
        SECOND_B("BBB");

        private static final int BASE = 5000;
        private final String message;

        AnotherTestInfo(String msg) {
            this.message = msg;
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public Integer getCode() {
            return BASE + ordinal();
        }
    }

}