package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.DatePicker
import android.widget.LinearLayout
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import java.util.Date

internal class DatePickerPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Members

    private val datePicker: DatePicker

    //endregion

    //region Public API

    var selected: Selection
        get() = Selection(
            day = datePicker.dayOfMonth,
            month = datePicker.month,
            year = datePicker.year
        )
        set(selected) {
            datePicker.updateDate(selected.year, selected.month, selected.day)
        }

    var minDate: Date
        get() = Date(datePicker.minDate)
        set(date) {
            datePicker.minDate = date.time
        }

    var maxDate: Date
        get() = Date(datePicker.maxDate)
        set(date) {
            datePicker.maxDate = date.time
        }
    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {}

    //endregion

    //region Subtypes

    data class Selection(val day: Int, val month: Int, val year: Int)

    //endregion

    init {
        this.gravity = Gravity.CENTER

        datePicker = DatePicker(context).apply { id = R.id.datePickerPart }
        this.addView(datePicker)
    }
}
