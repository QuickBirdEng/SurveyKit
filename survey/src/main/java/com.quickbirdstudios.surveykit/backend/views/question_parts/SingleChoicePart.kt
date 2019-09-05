package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.BackgroundDrawable
import com.quickbirdstudios.surveykit.backend.views.question_parts.helper.createSelectableThemedBackground

internal class SingleChoicePart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RadioGroup(context, attrs),
    StyleablePart {

    //region Public API

    @ColorInt
    var themeColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var textColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var defaultColor: Int = Color.BLACK

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
        textColor = surveyTheme.textColor
        update(options)
    }

    //endregion


    //region Private API

    private val fields: List<View>
        get() = (0 until this.childCount).map { this.getChildAt(it) }

    private fun update(list: List<TextChoice>) {
        val selectedChoice = selected
        this.removeAllViews()
        list.forEachIndexed { index, textChoice ->
            val item = if (index == list.size - 1) createLastRadioButton(textChoice.text, index)
            else createRadioButton(textChoice.text, index)
            if (textChoice == selectedChoice) {
                item.isChecked = true
                item.setTextColor(textColor)
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
        fields.forEach { (it as RadioButton).setTextColor(defaultColor) }
        selectedRadioButton()?.setTextColor(textColor)
        onCheckedChangeListener(group, checkedId)
    }

    //endregion


    //region RadioButton Creation Helpers

    private fun createRadioButton(@StringRes label: Int, tag: Int): RadioButton {
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
            background = createSelectableThemedBackground(
                context, BackgroundDrawable.Border.Top, themeColor
            )

            setPadding(
                horizontalPaddingEditTextLeft,
                verticalPaddingEditText,
                horizontalPaddingEditTextRight,
                verticalPaddingEditText
            )

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.layoutParams = layoutParams
        }
    }

    private fun createLastRadioButton(@StringRes label: Int, tag: Int) =
        createRadioButton(label, tag).apply {
            background = createSelectableThemedBackground(
                context, BackgroundDrawable.Border.Both, themeColor
            )
        }

    //endregion


    init {
        this.gravity = Gravity.CENTER

        this.setOnCheckedChangeListener { group, checkedId ->
            internalCheckedChangeListener(group, checkedId)
        }
    }
}
