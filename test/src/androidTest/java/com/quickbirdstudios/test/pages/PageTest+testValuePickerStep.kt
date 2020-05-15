package com.quickbirdstudios.test.pages

import android.view.View
import android.widget.NumberPicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.Matcher

internal fun PageTest.testValuePickerStep(indexOfValueToPick: Int) {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))
    onView(withId(R.id.valuePickerPart)).check(matches(isDisplayed()))

    onView(withId(R.id.valuePickerPart)).perform(setValuePickerOption(indexOfValueToPick))

    continueToNextStep()
}

//region Private API

private fun setValuePickerOption(option: Int) = object : ViewAction {
    override fun getDescription() = "Set a number picker option"

    override fun getConstraints(): Matcher<View> = isAssignableFrom(NumberPicker::class.java)

    override fun perform(uiController: UiController?, view: View) {
        (view as NumberPicker).value = option
    }
}

//endregion
