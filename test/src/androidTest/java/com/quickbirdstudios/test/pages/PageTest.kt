package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.quickbirdstudios.test.R

internal interface PageTest

internal fun PageTest.testTitleAndText(title: String, info: String) {
    onView(withId(R.id.infoTextTitle)).check(matches(withText(title)))
    onView(withId(R.id.infoTextInfo)).check(matches(withText(info)))
}

internal fun PageTest.checkIfTitleInfoAndContinueAreDisplayed() {
    onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))
    onView(withId(R.id.infoTextInfo)).check(matches(isDisplayed()))
    onView(withId(R.id.button_continue)).perform(ViewActions.scrollTo())
    onView(withId(R.id.button_continue)).check(matches(isDisplayed()))
}

internal fun PageTest.continueToNextStep() {
    onView(withId(R.id.button_continue)).check(matches(isEnabled()))
    onView(withId(R.id.button_continue)).perform(ViewActions.scrollTo(), ViewActions.click())
}
