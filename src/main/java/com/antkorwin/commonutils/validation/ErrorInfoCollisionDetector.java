package com.antkorwin.commonutils.validation;

import org.assertj.core.api.Assertions;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * Created on 18.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class ErrorInfoCollisionDetector {

    public static void assertInPackage(final String packageNameForScan) {

        Reflections reflections = new Reflections(packageNameForScan);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ErrorInfoUnique.class);
        HashMap<Integer, ErrorInfo> mapOfCodes = new HashMap<>();

        annotated.forEach(clazz -> {
            for (ErrorInfo e : (ErrorInfo[]) clazz.getEnumConstants()) {
                if (mapOfCodes.containsKey(e.getCode())) {
                    ErrorInfo info = mapOfCodes.get(e.getCode());
                    Assertions.fail("Detected collisions in the next error info: " +
                                    "\n" + e.toString() + "[" + e.getCode() + "]  / "
                                    + info.toString() + "[" + info.getCode() + "]" +
                                    "\n - from " + e.getClass() +
                                    "\n - and " + info.getClass());
                }
                mapOfCodes.put(e.getCode(), e);
            }
        });
    }
}
