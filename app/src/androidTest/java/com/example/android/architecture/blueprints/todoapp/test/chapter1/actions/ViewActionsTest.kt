package com.example.android.architecture.blueprints.todoapp.test.chapter1.actions

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.test.BaseTest
import com.example.android.architecture.blueprints.todoapp.test.chapter1.data.TestData
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Test

/**
 * Demonstrates ViewActions usage.
 */
class ViewActionsTest : BaseTest() {
    private var toDoTitle = ""
    private var toDoDescription = ""

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        toDoTitle = TestData.getToDoTitle()
        toDoDescription = TestData.getToDoDescription()
    }

    @Test
    fun addsNewToDo() {
        // Add new TO-DO.
        onView(withId(R.id.fab_add_task)).perform(click())
        onView(withId(R.id.add_task_title))
                .perform(typeText(toDoTitle), closeSoftKeyboard())
        onView(withId(R.id.add_task_description))
                .perform(typeText(toDoDescription), closeSoftKeyboard())
        onView(withId(R.id.fab_edit_task_done)).perform(click())

        // Verify new TO-DO with title is shown in the TO-DO list.
        onView(withText(toDoTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun checksToDoStateChange() {
        // Add new TO-DO.
        onView(withId(R.id.fab_add_task)).perform(click())
        onView(withId(R.id.add_task_title))
                .perform(typeText(toDoTitle), closeSoftKeyboard())
        onView(withId(R.id.add_task_description))
                .perform(typeText(toDoDescription), closeSoftKeyboard())
        onView(withId(R.id.fab_edit_task_done)).perform(click())

        // Mark TO-DO as completed.
        onView(withId(R.id.todo_complete)).perform(click())

        // Filter out completed TO-DO.
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText("Active"))).perform(click())
        onView(withId(R.id.todo_title)).check(matches(not(isDisplayed())))
        onView(withId(R.id.menu_filter)).perform(click())
        onView(allOf(withId(R.id.title), withText("Completed"))).perform(click())
        onView(withId(R.id.todo_title))
                .check(matches(CoreMatchers.allOf(withText(toDoTitle), isDisplayed())))
    }

    @Test
    fun editsToDo() {
        val editedToDoTitle = "Edited $toDoTitle"
        val editedToDoDescription = "Edited $toDoDescription"

        // Add new TO-DO.
        onView(withId(R.id.fab_add_task)).perform(click())
        onView(withId(R.id.add_task_title))
                .perform(typeText(toDoTitle), closeSoftKeyboard())
        onView(withId(R.id.add_task_description))
                .perform(typeText(toDoDescription), closeSoftKeyboard())
        onView(withId(R.id.fab_edit_task_done)).perform(click())

        // Edit TO-DO.
        onView(withText(toDoTitle)).perform(click())
        onView(withId(R.id.fab_edit_task)).perform(click())
        onView(withId(R.id.add_task_title))
                .perform(replaceText(editedToDoTitle), closeSoftKeyboard())
        onView(withId(R.id.add_task_description))
                .perform(replaceText(editedToDoDescription), closeSoftKeyboard())
        onView(withId(R.id.fab_edit_task_done)).perform(click())

        // Verify edited TO-DO is shown.
        onView(withText(editedToDoTitle)).check(matches(isDisplayed()))
    }
}