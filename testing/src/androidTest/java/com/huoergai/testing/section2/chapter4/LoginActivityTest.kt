package com.huoergai.testing.section2.chapter4

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.huoergai.testing.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * D&T: 2020-06-27 08:53
 * Des:
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    private lateinit var activityScenario: ActivityScenario<LoginActivity>

    @Before
    fun initTest() {
        activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun shouldWelcomeUser_whenLogin() {
        onView(withId(R.id.et_user_name)).perform(typeText("mia"))
        onView(withId(R.id.et_password)).perform(typeText("holiday"))
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("hello mia")).check(matches(isDisplayed()))

    }

}