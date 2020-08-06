package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal class ValuePickerPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Members

    private val numberPicker: NumberPicker

    //endregion

    //region Public API

    var choices: List<String> = emptyList()
        set(choicesList) {
            numberPicker.minValue = 0
            numberPicker.maxValue = choicesList.size - 1
            numberPicker.displayedValues = choicesList.toTypedArray()
            field = choicesList
        }

    var selected: String
        get() = choices[numberPicker.value]
        set(choice) {
            val index = choices.indexOf(choice)
            numberPicker.value = index
        }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        // TODO coloring if any
    }

    //endregion

    init {
        this.gravity = Gravity.CENTER
        numberPicker = NumberPicker(context).apply {
            id = R.id.valuePickerPart
            wrapSelectorWheel = true
        }

        this.addView(numberPicker)
    }
}
