package com.quickbirdstudios.test.pages

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert

internal fun PageTest.testNumberStep(
    numberText: String,
    defaultValue: Int?,
    keyboardVisibilityCheck: () -> Boolean
) {
    checkIfTitleInfoAndContinueAreDisplayed()

    if (defaultValue != null) {
        onView(withId(R.id.textFieldPartField))
            .check(matches(isEditTextValueEqualTo(defaultValue.toString())))
        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
    } else {
        onView(withId(R.id.button_continue)).check(matches(CoreMatchers.not(isEnabled())))
    }

    onView(withId(R.id.textFieldPartField)).perform(click())
    Assert.assertTrue(keyboardVisibilityCheck())
    onView(withId(R.id.textFieldPartField)).perform(clearText(), typeText(numberText))
    onView(withId(R.id.textFieldPartField)).check(matches(isEditTextValueEqualTo(numberText)))

    continueToNextStep()
}

//region Private API

private fun isEditTextValueEqualTo(content: String): Matcher<View> =
    object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Match Edit Text Value with View ID Value : $content")
        }

        override fun matchesSafely(view: View?): Boolean {
            view ?: return false
            return when (view) {
                is TextView -> view.text.toString() == content
                is EditText -> view.text.toString() == content
                else -> false
            }
        }
    }

//endregion
