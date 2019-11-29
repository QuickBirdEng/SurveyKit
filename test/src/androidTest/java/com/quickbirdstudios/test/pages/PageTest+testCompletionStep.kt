package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R

internal fun PageTest.testCompletionStep() {
    onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))
    onView(withId(R.id.infoTextInfo)).check(matches(isDisplayed()))
    onView(withId(R.id.button_continue)).perform(click())
}
