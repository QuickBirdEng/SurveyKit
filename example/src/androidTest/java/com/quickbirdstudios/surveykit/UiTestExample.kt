package com.quickbirdstudios.surveykit

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.quickbirdstudios.example.R
import com.quickbirdstudios.example.ui.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UiTestExample {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = SurveyKitTestApp()

    @Before
    fun setup() {

    }

    @Test
    fun appStart() {
        onView(withId(R.id.survey_view)).check(matches(isDisplayed()))
    }

    @Test
    fun runThroughApp() {
        onView(withId(R.id.button_continue)).perform(click())
    }

}
