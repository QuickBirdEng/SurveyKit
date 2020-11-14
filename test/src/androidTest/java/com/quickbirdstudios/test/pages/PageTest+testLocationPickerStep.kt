package com.quickbirdstudios.test.pages

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider
import com.quickbirdstudios.test.R
import com.quickbirdstudios.test.TestActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.not

internal fun PageTest.testLocationPickerTestStep(activityRule: ActivityTestRule<TestActivity>) {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_continue)).check(matches(isEnabled()))

    onView(withHint(android.R.string.search_go)).perform(typeText("test1"))

    onData(
        allOf(
            `is`(instanceOf(AddressSuggestionProvider.AddressSuggestion::class.java)),
            `is`(
                AddressSuggestionProvider.AddressSuggestion(
                    "test1",
                    AnswerFormat.LocationAnswerFormat.Location(1.0, 1.0)
                )
            )
        )
    ).inRoot(
        RootMatchers.withDecorView(
            not(
                `is`(
                    activityRule
                        .activity.window.decorView
                )
            )
        )
    )
        .perform(click())

    continueToNextStep()
}
