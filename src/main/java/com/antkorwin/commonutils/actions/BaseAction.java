package com.antkorwin.commonutils.actions;

import com.antkorwin.commonutils.validation.Guard;

/**
 * Created on 23.07.2018.
 *
 * @author Korovin Anatoliy
 */
public abstract class BaseAction<ResultT, ArgumentT extends ActionArgument> implements Action<ResultT> {

    @Override
    public ResultT execute(ActionArgument argument) {
        Guard.checkArgumentExist(argument, ActionsErrorInfo.ACTION_ARGUMENT_IS_MANDATORY);
        Guard.check(argument.validate(), ActionsErrorInfo.ILLEGAL_ARGUMENTS_VALUE);
        return executeImpl((ArgumentT) argument);
    }

    protected abstract ResultT executeImpl(ArgumentT argument);
}