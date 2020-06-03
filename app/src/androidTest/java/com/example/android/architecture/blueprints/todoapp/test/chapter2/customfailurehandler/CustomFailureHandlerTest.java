package com.example.android.architecture.blueprints.todoapp.test.chapter2.customfailurehandler;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.test.BaseTest;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class CustomFailureHandlerTest extends BaseTest {

    private Matcher blankTodoListText = allOf(
            withId(R.id.noTasksMain),
            isDisplayed());

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /** Exercise 8 */
    @Test
    public void refreshToDoListByDefaultAction() {
        onView(blankTodoListText).check(matches(withText("You have no TO-DO!")));
    }
}
