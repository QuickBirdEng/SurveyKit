package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.CoreMatchers
import org.junit.Assert

internal fun PageTest.testTextStep(text: String, keyboardVisibilityCheck: () -> Boolean) {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_skip_question)).check(matches(ViewMatchers.isDisplayed()))
    onView(withId(R.id.button_skip_question)).check(matches(
        ViewMatchers.withText(R.string.skip_here))
    )

    onView(withId(R.id.button_continue)).check(matches(CoreMatchers.not(isEnabled())))
    onView(withId(R.id.textFieldPartField)).perform(ViewActions.click())
    Assert.assertTrue(keyboardVisibilityCheck())
    onView(withId(R.id.textFieldPartField)).perform(ViewActions.typeText(text))

    continueToNextStep()
}
