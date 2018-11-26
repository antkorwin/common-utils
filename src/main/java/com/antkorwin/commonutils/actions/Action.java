package com.antkorwin.commonutils.actions;

/**
 * Created on 23.07.2018.
 *
 * @author Korovin Anatoliy
 */
public interface Action<ResultT> {

    /**
     * Execute an action
     *
     * @param argument for action
     * @return result of execution
     */
    ResultT execute(ActionArgument argument);
}
