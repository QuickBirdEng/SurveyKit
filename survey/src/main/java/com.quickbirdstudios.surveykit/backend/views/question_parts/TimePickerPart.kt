package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TimePicker
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

@Suppress("DEPRECATION")
internal class TimePickerPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Members

    private val timePicker: TimePicker

    //endregion

    //region Public API

    var selected: Selected
        get() = timePicker.selected
        set(selected) {
            timePicker.selected = selected
        }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        // TODO add styling if there is any
    }

    //endregion

    //region Subtypes

    data class Selected(val hour: Int, val minute: Int)

    //endregion

    //region Private API

    private var TimePicker.selected: Selected
        get() {
            return if (Build.VERSION.SDK_INT < 23) {
                Selected(
                    hour = this.currentHour,
                    minute = this.currentMinute
                )
            } else {
                Selected(
                    hour = this.hour,
                    minute = this.minute
                )
            }
        }
        set(selected) {
            return if (Build.VERSION.SDK_INT < 23) {
                this.currentHour = selected.hour
                this.currentMinute = selected.minute
            } else {
                this.hour = selected.hour
                this.minute = selected.minute
            }
        }

    //endregion

    init {
        this.gravity = Gravity.CENTER

        timePicker = TimePicker(context).apply { id = R.id.timePickerPart }
        this.addView(timePicker)
    }
}
