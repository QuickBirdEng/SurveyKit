package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.BackgroundDrawable
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.BackgroundDrawable.Border.Both
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.BackgroundDrawable.Border.Bottom
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.createSelectableThemedBackground

internal class SingleChoicePart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RadioGroup(context, attrs), StyleablePart {

    //region Public API

    @ColorInt
    var themeColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var radioButtonTextColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var defaultColor: Int = ContextCompat.getColor(context, R.color.survey_text)
    set(color) {
        update(options)
        field = color
    }
    var options: List<TextChoice> = emptyList()
        set(value) {
            update(value)
            field = value
        }

    var selected: TextChoice?
        get() = selectedChoice()
        set(choice) {
            if (choice != null)
                this.findViewWithTag<RadioButton>(options.indexOf(choice))?.isChecked = true
        }

    fun isOneSelected(): Boolean = this.checkedRadioButtonId != -1

    var onCheckedChangeListener: (RadioGroup, Int) -> Unit = { _, _ -> }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        themeColor = surveyTheme.themeColor
        radioButtonTextColor = surveyTheme.textColor
        update(options)
    }

    //endregion

    //region Private API

    private val fields: List<RadioButton>
        get() = (0 until this.childCount).mapNotNull { this.getChildAt(it) as? RadioButton }

    private fun update(list: List<TextChoice>) {
        val selectedChoice = selected
        this.removeAllViews()
        list.forEachIndexed { index, textChoice ->
            val item = createRadioButton(textChoice.text, index, if (index == 0) Both else Bottom)
            if (textChoice == selectedChoice) {
                item.isChecked = true
                item.setTextColor(radioButtonTextColor)
            }
            this.addView(item)
        }
    }

    private fun selectedChoice(): TextChoice? {
        val radioButtonIndex: Int = this
            .findViewById<RadioButton>(this.checkedRadioButtonId)?.tag as? Int
            ?: return null

        return options[radioButtonIndex]
    }

    private fun selectedRadioButton(): RadioButton? = this.findViewById(this.checkedRadioButtonId)

    //endregion

    //region Internal listeners

    private val internalCheckedChangeListener: (RadioGroup, Int) -> Unit = { group, checkedId ->
        fields.forEach { it.setTextColor(defaultColor) }
        selectedRadioButton()?.setTextColor(radioButtonTextColor)
        onCheckedChangeListener(group, checkedId)
    }

    //endregion

    //region RadioButton Creation Helpers

    private fun createRadioButton(
        label: String,
        tag: Int,
        border: BackgroundDrawable.Border
    ): RadioButton {
        val verticalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_vertical_padding)
        ).toInt()
        val horizontalPaddingEditTextLeft = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_left)
        ).toInt()
        val horizontalPaddingEditTextRight = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_right)
        ).toInt()

        return RadioButton(context).apply {
            id = View.generateViewId()
            setText(label)
            this.tag = tag
            isFocusable = true
            isClickable = true
            buttonDrawable = null
            textSize = 20f

            background = createSelectableThemedBackground(context, border, themeColor)

            setPadding(
                horizontalPaddingEditTextLeft,
                verticalPaddingEditText,
                horizontalPaddingEditTextRight,
                verticalPaddingEditText
            )

            val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            this.layoutParams = layoutParams
        }
    }

    //endregion

    init {
        this.id = R.id.singleChoicePart
        this.gravity = Gravity.CENTER

        this.setOnCheckedChangeListener { group, checkedId ->
            internalCheckedChangeListener(group, checkedId)
        }
    }
}
