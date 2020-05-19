package com.quickbirdstudios.test.pages

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.Matcher
import org.junit.Assert

internal fun PageTest.testScaleStep(progressToSetOn: Int, activity: Activity) {
    checkIfTitleInfoAndContinueAreDisplayed()

    /*
    TODO at some point extract for each step the answerformat (as done for the number)
    and check for all of it
     */
    onView(withId(R.id.button_continue))
        .check(ViewAssertions.matches(isEnabled()))
    onView(withId(R.id.seekBarPartField))
        .check(ViewAssertions.matches(isDisplayed()))
    onView(withId(R.id.seekBarPartField)).perform(setSeekBarProgress(progressToSetOn))
    Assert.assertTrue(activity.retrieveSeekBarProgress() == progressToSetOn)

    continueToNextStep()
}

//region Private API

private fun setSeekBarProgress(progress: Int) = object : ViewAction {
    override fun getDescription() = "Set a progress"

    override fun getConstraints(): Matcher<View> =
        isAssignableFrom(AppCompatSeekBar::class.java)

    override fun perform(uiController: UiController?, view: View) {
        (view as AppCompatSeekBar).progress = progress
    }
}

private fun Activity.retrieveSeekBarProgress(resourceId: Int? = null): Int {
    val seekBar = this.findViewById<AppCompatSeekBar>(resourceId ?: R.id.seekBarPartField)
    return seekBar.progress
}

//endregion
