package com.antkorwin.commonutils.validation;

import com.antkorwin.commonutils.exceptions.ConditionValidationException;
import com.antkorwin.commonutils.exceptions.NotFoundException;

/**
 * Created by Korovin Anatolii on 13.11.17.
 *
 * @author Korovin Anatolii
 */
public class Guard {

    public static void checkEntityExist(Object entity, ErrorInfo errorInfo){
        if(entity == null) throw new NotFoundException(errorInfo);
    }

    public static void checkConditionValid(boolean condition, ErrorInfo errorInfo){
        if(!condition) throw new ConditionValidationException(errorInfo);
    }
}
