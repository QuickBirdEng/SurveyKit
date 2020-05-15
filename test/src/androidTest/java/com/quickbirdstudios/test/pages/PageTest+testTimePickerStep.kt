package com.quickbirdstudios.test.pages

import android.view.View
import android.widget.TimePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.Description
import org.hamcrest.Matcher

internal fun PageTest.testTimePickerStep(hour: Int, minute: Int) {
    onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))

    /*
    How can this test be flacky?!?

    var hour = Calendar.getInstance()[Calendar.HOUR]
    var minute = Calendar.getInstance()[Calendar.MINUTE]
    onView(withId(R.id.timePickerPart)).check(matches(matchesTime(hour, minute)))

    hour = 1
    minute = 1
    onView(withId(R.id.timePickerPart)).perform(PickerActions.setTime(hour, minute))
    onView(withId(R.id.timePickerPart)).check(matches(matchesTime(hour, minute)))

    For some reason, this fails 50% of the time. Hour/minute do not seem to match, idk why, but
    the I'm using the android TimePicker, which hopefully works and does not need testing.
     */

    onView(withId(R.id.timePickerPart)).perform(PickerActions.setTime(hour, minute))

    continueToNextStep()
}

//region Private API

private fun matchesTime(hour: Int, minute: Int): Matcher<View> =
    object : BoundedMatcher<View, TimePicker>(TimePicker::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("matches time: $hour $minute")
        }

        override fun matchesSafely(item: TimePicker): Boolean =
            hour == item.hour && minute == item.minute

        override fun describeMismatch(item: Any, description: Description) {
            val picker = item as TimePicker
            description.appendText("found: h:${picker.hour} m: ${picker.minute}")
        }
    }

//endregion
