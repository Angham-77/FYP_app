package com.example.fyp_habitiny

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityTest{
    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java).moveToState(Lifecycle.State.RESUMED)
    }
    @Test
    fun testLogin_Successful() {
        val username = "test4"
        val password = "test4"
        Espresso.onView(withId(R.id.editTextUserName)).perform(ViewActions.typeText(username))
        Espresso.onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.buttonSignin)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textViewMessageMainActivity))
            .check(ViewAssertions.matches(ViewMatchers.withText("User Not Found, Please try again")))
    }
}