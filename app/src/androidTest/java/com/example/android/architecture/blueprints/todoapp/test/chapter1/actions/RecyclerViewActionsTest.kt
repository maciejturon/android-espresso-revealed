package com.example.android.architecture.blueprints.todoapp.test.chapter1.actions


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.GeneralLocation
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.test.BaseTest
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomClickAction.clickElementWithVisibility
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.AssertNotInTheListTodoWithTitle.assertNotInTheListTodoWithTitle
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.ClickTodoCheckBoxWithTitleViewAction.clickTodoCheckBoxWithTitle
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.ScrollToLastHolder.scrollToLastHolder
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomSwipeActions
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElement
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElementIsGone
import org.hamcrest.core.AllOf.allOf
import org.junit.Test

/**
 * Demonstrates [RecyclerView] actions usage.
 */
class RecyclerViewActionsTest : BaseTest() {
    private val todoSavedSnackbar = onView(withText(R.string.successfully_saved_task_message))
    private val customSwipeActions = CustomSwipeActions()
    private val todoList = allOf(withId(R.id.tasks_list), isDisplayed())

    @Test
    @Throws(Exception::class)
    fun addNewToDos() {
        generateToDos(12)
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, scrollTo()))
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(11))
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
        pressBack()
        onView(withId(R.id.tasks_list))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(2))
    }

    @Test
    @Throws(Exception::class)
    fun addNewToDosChained() {
        generateToDos(12)
        onView(withId(R.id.tasks_list))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, scrollTo()))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(1))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(11))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
        pressBack()
        onView(withId(R.id.tasks_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(2))
    }

    @Test
    @Throws(Exception::class)
    fun completeToDo() {
        generateToDos(10)
        onView(withId(R.id.tasks_list)).perform(clickTodoCheckBoxWithTitle("item 2"))
        onView(withId(R.id.tasks_list))
                .perform(scrollToLastHolder())
    }

    /**
     * Helper function that adds needed TO-DOs amount.
     * @param count - amount of TO-DOs to add.
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun generateToDos(count: Int) {
        for (i in 1..count) {
            waitForElementIsGone(todoSavedSnackbar, 3000)
            // Adding new TO-DO.
            onView(withId(R.id.fab_add_task)).perform(clickElementWithVisibility(20))
            onView(withId(R.id.add_task_title))
                    .perform(typeText("item $i"), closeSoftKeyboard())
            onView(withId(R.id.fab_edit_task_done)).perform(click())
            waitForElement(todoSavedSnackbar, 3000)
        }
        waitForElementIsGone(todoSavedSnackbar, 3000)
    }
    // Writing a Test Case with a Custom Swipe Action (page 53, ex. 6)
    /** Exercise 6.1  */
    @Test
    @Throws(Exception::class)
    fun refreshToDoListByDefaultAction() {
        generateToDos(2)
        onView(todoList).perform(swipeDown())
    }

    /** Exercise 6.2  */
    @Test
    @Throws(Exception::class)
    fun refreshToDoListByCustomAction() {
        generateToDos(2)
        onView(todoList)
                .perform(customSwipeActions.swipeCustom(100, GeneralLocation.CENTER, GeneralLocation.BOTTOM_CENTER))
    }

    /** Exercise 7  */
    @Test
    @Throws(Exception::class)
    fun checkIfItemIsNotInTheList() {
        generateToDos(2)
        onView(todoList).perform(assertNotInTheListTodoWithTitle("item 3"))
    }
}