package com.antkorwin.commonutils.actions;

import com.antkorwin.commonutils.exceptions.ConditionValidationException;
import lombok.AllArgsConstructor;
import org.junit.Test;

/**
 * Created on 23.07.2018.
 *
 * @author Korovin Anatoliy
 */
public class BaseActionTest {

    @Test(expected = ConditionValidationException.class)
    public void execute() {
        new TestAction().execute(new TestArgument(false));
    }

    @Test
    public void executeFail() {
        new TestAction().execute(new TestArgument(true));
    }


    class TestAction extends BaseAction<String, TestArgument> {
        @Override
        protected String executeImpl(TestArgument argument) {
            return "Hello World from Action!";
        }
    }

    @AllArgsConstructor
    class TestArgument implements ActionArgument {

        private boolean condition;

        @Override
        public boolean validate() {
            return condition;
        }
    }
}