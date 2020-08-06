package com.quickbirdstudios.test.pages

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.quickbirdstudios.test.R

internal fun PageTest.testMultipleChoiceStep(context: Context) {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))
    onView(withId(R.id.multipleChoicePart)).check(matches(isDisplayed()))

    // remove selection
    onView(withText(context.getString(R.string.allergies_pet))).perform(scrollTo(), click())
    onView(withText(context.getString(R.string.allergies_pollen))).perform(scrollTo(), click())

    // reselect
    onView(withText(context.getString(R.string.allergies_back_penicillin)))
        .perform(scrollTo(), click())
    onView(withText(context.getString(R.string.allergies_pollen))).perform(scrollTo(), click())

    continueToNextStep()
}
