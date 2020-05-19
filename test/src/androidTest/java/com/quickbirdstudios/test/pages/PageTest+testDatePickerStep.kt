package com.quickbirdstudios.test.pages

import android.view.View
import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import java.util.Calendar
import org.hamcrest.Description
import org.hamcrest.Matcher

internal fun PageTest.testDatePickerStep(year: Int, month: Int, day: Int) {
    onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))

    val currentDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
    val currentMonth = Calendar.getInstance()[Calendar.MONTH]
    val currentYear = Calendar.getInstance()[Calendar.YEAR]
    onView(withId(R.id.datePickerPart))
        .check(matches(matchesDate(currentDay, currentMonth, currentYear)))

    onView(withId(R.id.datePickerPart)).perform(PickerActions.setDate(year, month + 1, day))
    /*
    For some reason, the date extracted

    val datePicker = activityRule.activity.findViewById<DatePicker>(R.id.datePickerPart)
    println("${datePicker.dayOfMonth}, ${datePicker.month}, ${datePicker.year}")

    for the month is 0, not 1, even though I set it to 1
     */
    onView(withId(R.id.datePickerPart)).check(matches(matchesDate(day, month, year)))

    continueToNextStep()
}

//region Private API

private fun matchesDate(day: Int, month: Int, year: Int): Matcher<View> =
    object : BoundedMatcher<View, DatePicker>(DatePicker::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("matches date: $day $month $year")
        }

        override fun matchesSafely(item: DatePicker): Boolean =
            year == item.year && month == item.month && day == item.dayOfMonth
    }

//endregion
