package com.antkorwin.commonutils.gc;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created by Korovin Anatolii on 04.07.2018.
 *
 * @author Korovin Anatolii
 * @version 1.0
 */
public class GcUtilsTest {


    @Test
    public void tryToAllocateAllAvailableMemoryWithSoftReference() {
        // Arrange
        String instance = new String("123323");
        SoftReference<String> softReference = new SoftReference<>(instance);
        instance = null;
        Assertions.assertThat(softReference).isNotNull();
        Assertions.assertThat(softReference.get()).isNotNull();

        // Act
        GcUtils.tryToAllocateAllAvailableMemory();

        // Asserts
        Assertions.assertThat(softReference.get()).isNull();
    }


    @Test
    public void testQueuePollAfterFinalizationGC() throws InterruptedException {
        // Arrange
        Foo foo = new Foo();
        ReferenceQueue<Foo> queue = new ReferenceQueue<>();
        PhantomReference<Foo> ref = new PhantomReference<>(foo, queue);

        // Act
        foo = null;
        int gcPass = GcUtils.fullFinalization();

        // Asserts
        Assertions.assertThat(ref.isEnqueued()).isTrue();
        // expect of release phantom reference until runs 4 iterations(maximum)
        Assertions.assertThat(gcPass).isLessThanOrEqualTo(4);
        Assertions.assertThat(queue.poll()).isEqualTo(ref);
    }

    @Test
    public void testWithoutFinalize() {
        // Arrange
        Object instance = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> ref = new PhantomReference<>(instance, queue);

        // Act
        instance = null;

        GcUtils.fullFinalization();

        // Asserts
        Assertions.assertThat(ref.isEnqueued()).isTrue();
        Assertions.assertThat(queue.poll()).isEqualTo(ref);
    }

    @Test
    public void testWeakAfterGC() {
        // Arrange
        String instance = new String("123");
        WeakReference<String> ref = new WeakReference<>(instance);

        // Act
        instance = null;
        GcUtils.fullFinalization();

        // Asserts
        Assertions.assertThat(ref.get()).isNull();
    }

    @Test
    public void testWeakMap() throws InterruptedException {
        // Arrange
        WeakHashMap<String, Boolean> map = new WeakHashMap<>();
        String instance = new String("123");
        map.put(instance, true);

        // Act
        instance = null;
        GcUtils.fullFinalization();

        // Asserts
        await().atMost(5, TimeUnit.SECONDS)
               .until(map::isEmpty);

        Assertions.assertThat(map).isEmpty();
    }


    private class Foo {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("FIN!");
            super.finalize();
        }
    }
}