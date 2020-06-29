package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import kotlinx.android.parcel.Parcelize
import java.util.*

internal class DateTimePickerPart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart, TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private val timePicker: TimePickerDialog
    private val datePicker: DatePickerDialog
    private val showTimePickerButton: Button
    private val showDatePickerButton: Button
    private val selectedDateTimeLabel: TextView

    var selectedTime = SelectedTime(
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE)
    )
        set(value) {
            field = value
            selectedDateTimeLabel.text = "$selectedDate $selectedTime"
        }
    var selectedDate = SelectedDate(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )
        set(value) {
            field = value
            selectedDateTimeLabel.text = "$selectedDate $selectedTime"
        }

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL

        timePicker = TimePickerDialog(context, this, selectedTime.hour, selectedTime.minute, true)
        datePicker =
            DatePickerDialog(
                context,
                this,
                selectedDate.year,
                selectedDate.month,
                selectedDate.dayOfMonth
            )
        showTimePickerButton = Button(context).apply {
            text = context.getString(R.string.date_time_picker_show_time_button_text)
            setOnClickListener { timePicker.show() }
            val layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            layoutParams.bottomMargin =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            this.layoutParams = layoutParams
            background = ContextCompat.getDrawable(context, R.drawable.main_button_background)
        }
        showDatePickerButton = Button(context).apply {
            text = context.getString(R.string.date_time_picker_show_date_button_text)
            setOnClickListener { datePicker.show() }
            val layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            layoutParams.bottomMargin =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            this.layoutParams = layoutParams

            background = ContextCompat.getDrawable(context, R.drawable.main_button_background)
        }
        selectedDateTimeLabel = TextView(context).apply {
            text = "$selectedDate $selectedTime"
            textSize = context.resources.getDimension(R.dimen.date_time_label_text_size)
            setTextColor(ContextCompat.getColor(context, R.color.black))
            textAlignment = AppCompatTextView.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            this.layoutParams = layoutParams
        }

        addView(selectedDateTimeLabel)
        addView(showDatePickerButton)
        addView(showTimePickerButton)
    }

    @Parcelize
    data class SelectedTime(val hour: Int, val minute: Int) : Parcelable {
        override fun toString(): String {
            return "$hour:$minute"
        }
    }

    @Parcelize
    data class SelectedDate(val year: Int, val month: Int, val dayOfMonth: Int) : Parcelable {
        override fun toString(): String {
            return "$year/${month + 1}/$dayOfMonth"
        }
    }

    override fun style(surveyTheme: SurveyTheme) {
        showDatePickerButton.setTextColor(surveyTheme.themeColor)
        showTimePickerButton.setTextColor(surveyTheme.themeColor)

        showDatePickerButton.background.setTint(surveyTheme.themeColor)
        showTimePickerButton.background.setTint(surveyTheme.themeColor)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        selectedTime = SelectedTime(hourOfDay, minute)
        selectedDateTimeLabel.text = "$selectedDate $selectedTime"
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        selectedDate = SelectedDate(year, month, dayOfMonth)
        selectedDateTimeLabel.text = "$selectedDate $selectedTime"
    }
}
