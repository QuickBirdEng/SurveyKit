package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R

internal fun PageTest.testCustomStep() {
    onView(withId(R.id.back_button)).check(matches(isDisplayed()))
    onView(withId(R.id.skip_button)).check(matches(isDisplayed()))
    onView(withId(R.id.close_button)).check(matches(isDisplayed()))
    onView(withId(R.id.continue_button)).perform(ViewActions.click())
}
