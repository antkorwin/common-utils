package com.antkorwin.commonutils.concurrent;

/**
 * Created on 05.07.2018.
 *
 * Thread.sleep wrapper
 *
 * @author Korovin Anatoliy
 */
public class ThreadSleep {

    /**
     * Wrapped call of the Thread.sleep, without declaration of checked exceptions.
     *
     * @param seconds the length of time to sleep, in seconds
     */
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
