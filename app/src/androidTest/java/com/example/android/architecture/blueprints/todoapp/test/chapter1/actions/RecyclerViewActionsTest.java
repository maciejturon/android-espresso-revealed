package com.example.android.architecture.blueprints.todoapp.test.chapter1.actions;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.v7.widget.RecyclerView;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.test.BaseTest;
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions;
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomSwipeActions;

import org.hamcrest.Matcher;
import org.junit.Test;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomClickAction.clickElementWithVisibility;
import static com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.AssertNotInTheListTodoWithTitle.assertNotInTheListTodoWithTitle;
import static com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.ClickTodoCheckBoxWithTitleViewAction.clickTodoCheckBoxWithTitle;
import static com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElement;
import static com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElementIsGone;
import static org.hamcrest.Matchers.allOf;

/**
 * Demonstrates {@link RecyclerView} actions usage.
 */
public class RecyclerViewActionsTest extends BaseTest {

    private ViewInteraction todoSavedSnackbar = onView(withText(R.string.successfully_saved_task_message));
    private CustomSwipeActions customSwipeActions = new CustomSwipeActions();
    private Matcher todoList = allOf(
            withId(R.id.tasks_list),
            isDisplayed());

    @Test
    public void addNewToDos() throws Exception {
        generateToDos(12);
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition(10, scrollTo()));
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition(1));
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition(11));
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition(11, click()));
        Espresso.pressBack();
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition(2));
    }

    @Test
    public void addNewToDosChained() throws Exception {
        generateToDos(12);
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition(10, scrollTo()))
                .perform(scrollToPosition(1))
                .perform(scrollToPosition(11))
                .perform(actionOnItemAtPosition(11, click()));
        Espresso.pressBack();
        onView(withId(R.id.tasks_list)).perform(scrollToPosition(2));
    }

    @Test
    public void completeToDo() throws Exception {
        generateToDos(10);
        onView(withId(R.id.tasks_list)).perform(clickTodoCheckBoxWithTitle("item 2"));
        onView(withId(R.id.tasks_list))
                .perform(CustomRecyclerViewActions.ScrollToLastHolder.scrollToLastHolder());
    }

    /**
     * Helper function that adds needed TO-DOs amount.
     * @param count - amount of TO-DOs to add.
     * @throws Exception
     */
    private void generateToDos(int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            waitForElementIsGone(todoSavedSnackbar, 3000);
            // Adding new TO-DO.
            onView(withId(R.id.fab_add_task)).perform(clickElementWithVisibility(20));
            onView(withId(R.id.add_task_title))
                    .perform(typeText("item " + i), closeSoftKeyboard());
            onView(withId(R.id.fab_edit_task_done)).perform(click());
            waitForElement(todoSavedSnackbar, 3000);
        }
        waitForElementIsGone(todoSavedSnackbar, 3000);
    }

    // Writing a Test Case with a Custom Swipe Action (page 53, ex. 6)

    /** Exercise 6.1 */
    @Test
    public void refreshToDoListByDefaultAction() throws Exception {
        generateToDos(2);
        onView(todoList).perform(swipeDown());
    }

    /** Exercise 6.2 */
    @Test
    public void refreshToDoListByCustomAction() throws Exception {
        generateToDos(2);
        onView(todoList)
                .perform(customSwipeActions.swipeCustom(100, GeneralLocation.CENTER, GeneralLocation.BOTTOM_CENTER));
    }

    /** Exercise 7 */
    @Test
    public void checkIfItemIsNotInTheList() throws Exception {
        generateToDos(2);
        onView(todoList).perform(assertNotInTheListTodoWithTitle("item 3"));
    }

}
