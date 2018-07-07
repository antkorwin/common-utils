package com.antkorwin.commonutils.gc;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by Korovin Anatolii on 07.07.2018.
 *
 * @author Korovin Anatolii
 * @version 1.0
 */
public class LeakDetectorTest {

    @Test
    public void testWithoutLeaks() {
        // Arrange
        Foo foo = new Foo();
        LeakDetector leakDetector = new LeakDetector(foo);

        // Act
        foo = null;

        // Asserts
        leakDetector.assertMemoryLeaksNotExist();
    }

    @Test
    public void testWithLeak() {
        // Arrange
        Foo foo = new Foo();
        Foo bar = foo;
        LeakDetector leakDetector = new LeakDetector(foo);

        // Act
        foo = null;

        // Asserts
        leakDetector.assertMemoryLeaksExist();
    }


    private class Foo {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("FIN!");
            super.finalize();
        }
    }

}