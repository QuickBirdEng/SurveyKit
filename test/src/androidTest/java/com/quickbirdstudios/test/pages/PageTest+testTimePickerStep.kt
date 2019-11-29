package com.quickbirdstudios.test.pages

import android.view.View
import android.widget.TimePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.quickbirdstudios.test.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.util.*

internal fun PageTest.testTimePickerStep() {
    onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))

    var hour = Calendar.getInstance()[Calendar.HOUR]
    var minute = Calendar.getInstance()[Calendar.MINUTE]
    onView(withId(R.id.timePickerPart)).check(matches(matchesTime(hour, minute)))

    hour = 1
    minute = 1
    onView(withId(R.id.timePickerPart)).perform(PickerActions.setTime(hour, minute))
    onView(withId(R.id.timePickerPart)).check(matches(matchesTime(hour, minute)))

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
