package com.quickbirdstudios.surveykit

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class UiTestExample {

    private val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentationRegistry.targetContext
    private val inputMethodManager = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Before
    fun setup() {

    }

    @Test
    fun runThrough() {
        testIntroStep()
        testTextStep()
        testNumberStep()
        testScaleStep()
        testMultipleChoiceStep()
        testSingleChoiceStep()
        testBooleanChoiceStep()
        testValuePickerStep()
        testDatePickerStep()
        testTimePickerStep()
        testEmailStep()
        testImageSelectorStep()
    }

    //region Step test functions

    private fun testIntroStep() {
        onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.infoTextInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.button_continue)).perform(click())
    }

    private fun testTextStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))
        onView(withId(R.id.textFieldPartField)).perform(click())
        Assert.assertTrue(isKeyboardShown())
        onView(withId(R.id.textFieldPartField)).perform(typeText(TextStepInput))

        continueToNextStep()
    }

    private fun testNumberStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        val defaultValue =
            (activityRule.activity.intStep.answerFormat as AnswerFormat.IntegerAnswerFormat)
                .defaultValue
        if (defaultValue != null) {
            onView(withId(R.id.textFieldPartField)).check(
                matches(isEditTextValueEqualTo(defaultValue.toString()))
            )
            onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        } else {
            onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))
        }

        onView(withId(R.id.textFieldPartField)).perform(click())
        Assert.assertTrue(isKeyboardShown())
        onView(withId(R.id.textFieldPartField)).perform(clearText(), typeText(NumberStepInput))
        onView(withId(R.id.textFieldPartField))
            .check(matches(isEditTextValueEqualTo(NumberStepInput)))

        continueToNextStep()
    }

    private fun testScaleStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        /*
        TODO at some point extract for each step the answerformat (as done for the number)
        and check for all of it
         */
        val progressToSetOn = 0
        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        onView(withId(R.id.seekBarPartField)).check(matches(isDisplayed()))
        onView(withId(R.id.seekBarPartField)).perform(setSeekBarProgress(progressToSetOn))
        Assert.assertTrue(activityRule.activity.retrieveSeekBarProgress() == progressToSetOn)

        continueToNextStep()
    }

    private fun testMultipleChoiceStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))
        onView(withId(R.id.multipleChoicePart)).check(matches(isDisplayed()))

        onView(withText(context.getString(R.string.allergies_back_penicillin))).perform(
            scrollTo(),
            click()
        )
        onView(withText(context.getString(R.string.allergies_pollen))).perform(scrollTo(), click())

        continueToNextStep()
    }

    private fun testSingleChoiceStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))
        onView(withId(R.id.singleChoicePart)).check(matches(isDisplayed()))

        onView(withText(context.getString(R.string.no))).perform(scrollTo(), click())
        onView(withText(R.string.no)).check(matches(isChecked()))
        onView(withText(context.getString(R.string.yes))).perform(scrollTo(), click())
        onView(withText(R.string.yes)).check(matches(isChecked()))
        onView(withText(R.string.no)).check(matches(isNotChecked()))

        continueToNextStep()
    }

    private fun testBooleanChoiceStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        onView(withId(R.id.singleChoicePart)).check(matches(isDisplayed()))

        onView(withText(R.string.no)).check(matches(isChecked()))

        onView(withText(context.getString(R.string.yes))).perform(scrollTo(), click())

        onView(withText(R.string.yes)).check(matches(isChecked()))
        onView(withText(R.string.no)).check(matches(isNotChecked()))

        continueToNextStep()
    }

    private fun testValuePickerStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        onView(withId(R.id.valuePickerPart)).check(matches(isDisplayed()))

        onView(withId(R.id.valuePickerPart)).perform(setValuePickerOption(0))

        continueToNextStep()
    }

    private fun testDatePickerStep() {
        onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))

        onView(withId(R.id.button_continue)).check(matches(isEnabled()))

        var day = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        var month = Calendar.getInstance()[Calendar.MONTH]
        val year = Calendar.getInstance()[Calendar.YEAR]
        onView(withId(R.id.datePickerPart)).check(matches(matchesDate(day, month, year)))

        day = 1
        month = 1
        onView(withId(R.id.datePickerPart)).perform(PickerActions.setDate(year, month, day))
        /*
        For some reason, the date extracted

        val datePicker = activityRule.activity.findViewById<DatePicker>(R.id.datePickerPart)
        println("${datePicker.dayOfMonth}, ${datePicker.month}, ${datePicker.year}")

        for the month is 0, not 1, even though I set it to 1
         */
        onView(withId(R.id.datePickerPart)).check(matches(matchesDate(day, month - 1, year)))

        continueToNextStep()
    }

    private fun testTimePickerStep() {
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

    private fun testEmailStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))
        onView(withId(R.id.textFieldPartField)).perform(click())
        Assert.assertTrue(isKeyboardShown())

        onView(withId(R.id.textFieldPartField)).perform(typeText(EmailStepInputWrong))
        onView(withId(R.id.button_continue)).perform(scrollTo())
        onView(withId(R.id.button_continue)).check(matches(not(isEnabled())))

        onView(withId(R.id.textFieldPartField)).perform(scrollTo())
        onView(withId(R.id.textFieldPartField)).perform(clearText(), typeText(EmailStepInputRight))

        continueToNextStep()
    }

    private fun testImageSelectorStep() {
        checkIfTitleInfoAndContinueAreDisplayed()

        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        onView(withId(R.id.imageSelectorPart)).check(matches(isSelected(0)))
        onView(withId(R.id.imageSelectorPart)).perform(clickRecyclerViewItem(0))

        continueToNextStep()
    }

    //endregion

    //region Helper functions

    private fun testTitleAndText(title: String, info: String) {
        onView(withId(R.id.infoTextTitle)).check(matches(withText(title)))
        onView(withId(R.id.infoTextInfo)).check(matches(withText(info)))
    }

    private fun checkIfTitleInfoAndContinueAreDisplayed() {
        onView(withId(R.id.infoTextTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.infoTextInfo)).check(matches(isDisplayed()))
        onView(withId(R.id.button_continue)).perform(scrollTo())
        onView(withId(R.id.button_continue)).check(matches(isDisplayed()))
    }

    private fun continueToNextStep() {
        onView(withId(R.id.button_continue)).check(matches(isEnabled()))
        onView(withId(R.id.button_continue)).perform(scrollTo(), click())
    }

    private fun isKeyboardShown(): Boolean {
        return inputMethodManager.isAcceptingText
    }

    //endregion

    //region Private Specific View Helper functions

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

    private fun setValuePickerOption(option: Int) = object : ViewAction {
        override fun getDescription() = "Set a number picker option"

        override fun getConstraints(): Matcher<View> = isAssignableFrom(NumberPicker::class.java)

        override fun perform(uiController: UiController?, view: View) {
            (view as NumberPicker).value = option
        }
    }

    private fun matchesDate(day: Int, month: Int, year: Int): Matcher<View> =
        object : BoundedMatcher<View, DatePicker>(DatePicker::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("matches date: $day $month $year")
            }

            override fun matchesSafely(item: DatePicker): Boolean =
                year == item.year && month == item.month && day == item.dayOfMonth
        }

    private fun matchesTime(hour: Int, minute: Int): Matcher<View> =
        object : BoundedMatcher<View, TimePicker>(TimePicker::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("matches time: $hour $minute")
            }

            override fun matchesSafely(item: TimePicker): Boolean =
                hour == item.hour && minute == item.minute
        }

    private fun clickRecyclerViewItem(item: Int) = object : ViewAction {
        override fun getDescription() = "click recyclerview item $item"

        override fun getConstraints(): Matcher<View>? = isAssignableFrom(RecyclerView::class.java)

        override fun perform(uiController: UiController?, parent: View) {
            val childCount = (parent as ViewGroup).childCount

            require(item < childCount) { "Item index $item was not in range (0..$childCount)" }

            (parent.getChildAt(item) as LinearLayout).findViewById<ImageView>(R.id.image)
                .performClick()
        }
    }

    private fun isSelected(item: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description
                    .appendText("Checks if item in recyclerview at position $item is selected")
            }

            override fun matchesSafely(view: View?): Boolean {
                view ?: return false
                return when (view) {
                    is RecyclerView.ViewHolder -> {
                        (
                            view.findViewById<LinearLayout>(R.id.border).background as ColorDrawable
                            ).color == 0
                        true
                    }
                    else -> false
                }
            }
        }

    //endregion

    companion object {
        private const val TextStepInput = "TextStepInput"
        private const val NumberStepInput = "35"
        private const val EmailStepInputWrong = "asdf@test"
        private const val EmailStepInputRight = "email@test.com"
    }
}
