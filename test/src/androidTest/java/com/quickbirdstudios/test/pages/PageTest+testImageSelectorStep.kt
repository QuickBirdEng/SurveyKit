package com.quickbirdstudios.test.pages

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.quickbirdstudios.test.R
import org.hamcrest.Matcher

internal fun PageTest.testImageSelectorStep(itemsToClick: List<Int>) {
    checkIfTitleInfoAndContinueAreDisplayed()

    onView(withId(R.id.button_continue)).check(ViewAssertions.matches(isEnabled()))

    itemsToClick.forEach { index ->
        onView(withId(R.id.imageSelectorPart)).perform(clickRecyclerViewItem(index))
    }

    continueToNextStep()
}

//region Private Helper

private fun clickRecyclerViewItem(item: Int) = object : ViewAction {
    override fun getDescription() = "click recyclerview item $item"

    override fun getConstraints(): Matcher<View>? =
        isAssignableFrom(RecyclerView::class.java)

    override fun perform(uiController: UiController?, parent: View) {
        val childCount = (parent as ViewGroup).childCount

        require(item < childCount) { "Item index $item was not in range (0..$childCount)" }

        (parent.getChildAt(item) as LinearLayout).findViewById<ImageView>(R.id.image)
            .performClick()
    }
}

// this is not working, fix it before touching it
// private fun isSelected(item: Int): Matcher<View> =
//    object : TypeSafeMatcher<View>() {
//        override fun describeTo(description: Description) {
//            description.appendText("Checks if item in recyclerview at position $item is selected")
//        }
//
//        override fun matchesSafely(view: View?): Boolean {
//            view ?: return false
//            return when (view) {
//                is RecyclerView.ViewHolder -> {
//                    (
//                        view.findViewById<LinearLayout>(R.id.border).background as ColorDrawable
//                        ).color == 0
//                    true
//                }
//                else -> false
//            }
//        }
//    }

//endregion
