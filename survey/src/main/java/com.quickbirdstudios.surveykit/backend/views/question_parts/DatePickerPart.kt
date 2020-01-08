package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import java.text.SimpleDateFormat
import java.util.*

internal class DatePickerPart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Members

    private val dateView: TextView
    private val datePickerDialog: DatePickerDialog
    private val calendar = Calendar.getInstance()

    //endregion

    //region Public API

    var dateFormatter: SimpleDateFormat = defaultDateFormatter()

    var selected: Selection = Selection(
        day = calendar[Calendar.DAY_OF_MONTH],
        month = calendar[Calendar.MONTH],
        year = calendar[Calendar.YEAR]
    )
        set(selection) {
            field = selection
            dateView.text = dateFormatter.format(selection.toDate())
        }

    var minDate: Date? = null

    var maxDate: Date? = null

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        dateView.setTextColor(surveyTheme.textColor)
    }

    //endregion

    //region Private Helper

    private fun defaultDateFormatter(): SimpleDateFormat =
        SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

    private fun datePickerListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            selected = Selection(day = dayOfMonth, month = month, year = year)
            dateView.text = dateFormatter.format(selected.toDate())
        }

    private fun Selection.toDate(): Date {
        calendar[Calendar.YEAR] = this.year
        calendar[Calendar.MONTH] = this.month
        calendar[Calendar.DAY_OF_MONTH] = this.day

        return calendar.time
    }

    //endregion

    //region Subtypes

    data class Selection(val day: Int, val month: Int, val year: Int)

    //endregion

    init {
        this.gravity = Gravity.CENTER

        datePickerDialog = DatePickerDialog(
            context,
            datePickerListener(),
            selected.year,
            selected.month,
            selected.day
        )
        dateView = TextView(context).apply {
            text = dateFormatter.format(selected.toDate())
            textSize = 40f
            background = ContextCompat.getDrawable(context, R.drawable.grey_background_rounded)
            setPadding(20, 20, 20, 20)
        }
        dateView.setOnClickListener {
            minDate?.let { datePickerDialog.datePicker.minDate = it.time }
            maxDate?.let { datePickerDialog.datePicker.maxDate = it.time }
            datePickerDialog.show()
        }
        this.addView(dateView)
    }
}
