package com.huoergai.testing

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * D&T: 2020-06-24 20:28
 * Des:
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SecondaryActivityTest {
    lateinit var activityScenario: ActivityScenario<SecondaryActivity>

    @Before
    fun initFun() {
        println("------------------ SecondaryActivityTest start ------------------")
        activityScenario = ActivityScenario.launch(SecondaryActivity::class.java)
    }

    @After
    fun testEnd() {
        println("------------------ SecondaryActivityTest end ------------------")
    }

    @Test
    fun testViewVisibility() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun backToPreviousActivity() {
        onView(withId(R.id.btn_previous)).perform(click())
    }

}