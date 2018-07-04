package com.antkorwin.commonutils.concurrent;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 04.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class ConcurrentSet {

    /**
     * make new instance of concurrent Set, based on ConcurrentHashMap
     *
     * @param <TypeT> type of elements in Set
     *
     * @return new Set instance
     */
    public static <TypeT> Set<TypeT> getInstance() {
        return Collections.newSetFromMap(new ConcurrentHashMap<TypeT, Boolean>());
    }
}
