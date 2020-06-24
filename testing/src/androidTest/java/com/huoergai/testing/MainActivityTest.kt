package com.huoergai.testing

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private lateinit var activityScenario: ActivityScenario<MainActivity>
    private var testStartTime: Long = 0L

    @Before
    fun initFun() {
        println("------------------ MainActivityTest start ------------------")
        testStartTime = System.currentTimeMillis()
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun testEnd() {
        Log.d("MainActivityTest", " testTime:${(System.currentTimeMillis() - testStartTime)}")
        println("------------------ MainActivityTest end ------------------")
    }

    @Test
    fun checkViewVisibility() {
        // Context of the app under test.
        // val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        onView(withId(R.id.btnClick)).check(matches(isDisplayed()))
    }

    @Test
    fun test_View_text() {
        onView(withId(R.id.btnClick)).check(matches(withText(R.string.click)))
    }

    @Test
    fun navToSecondActivity() {
        onView(withId(R.id.btnClick)).perform(click())
        onView(withId(R.id.btn_previous)).check(matches(isDisplayed()))

        // method 1
        // onView(withId(R.id.btn_previous)).perform(click())
        // method 2
        pressBack()

        onView(withId(R.id.main_cl)).check(matches(isDisplayed()))
    }


}