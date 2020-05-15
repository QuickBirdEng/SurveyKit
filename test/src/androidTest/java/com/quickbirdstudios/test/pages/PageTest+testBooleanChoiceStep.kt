package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.quickbirdstudios.test.R

internal fun PageTest.testBooleanChoiceStep() {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))
    onView(withId(R.id.singleChoicePart)).check(matches(isDisplayed()))

    onView(withText(R.string.no)).check(matches(isChecked()))

    onView(withText(R.string.yes)).perform(scrollTo(), click())

    onView(withText(R.string.yes)).check(matches(isChecked()))
    onView(withText(R.string.no)).check(matches(isNotChecked()))

    continueToNextStep()
}
