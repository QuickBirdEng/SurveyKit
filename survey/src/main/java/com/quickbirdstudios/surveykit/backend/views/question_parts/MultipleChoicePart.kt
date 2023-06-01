package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
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

internal class MultipleChoicePart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {

    //region Public API

    @ColorInt
    var themeColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var checkBoxTextColor: Int = ContextCompat.getColor(context, R.color.cyan_text)
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

    var selected: List<TextChoice>
        get() = selectedChoices()
        set(list) {
            list.forEach { selected ->
                this.findViewWithTag<CheckBox>(options.indexOf(selected))?.isChecked = true
            }
        }

    fun isOneSelected(): Boolean = this.selectedChoices().isNotEmpty()

    var onCheckedChangeListener: (View, Boolean) -> Unit = { _, _ -> }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        themeColor = surveyTheme.themeColor
        checkBoxTextColor = surveyTheme.textColor
    }

    //endregion

    //region Private API

    private val fields: List<CheckBox>
        get() = (0 until this.childCount).mapNotNull { this.findViewWithTag(it) as? CheckBox }

    private fun update(list: List<TextChoice>) {
        val selectedChoices = selected
        this.removeAllViews()
        list.forEachIndexed { index, textChoice ->
            val item = createCheckBox(textChoice.text, index, if (index == 0) Both else Bottom)
            if (selectedChoices.contains(textChoice)) {
                item.isChecked = true
                item.setTextColor(checkBoxTextColor)
            }
            this.addView(item)
        }
    }

    private fun selectedChoices(): List<TextChoice> {
        return fields
            .filter { it.isChecked }
            .map { options[it.tag as Int] }
    }

    private fun selectedCheckBoxes(): List<CheckBox> = fields.filter { it.isChecked }

    //endregion

    //region Internal listeners

    private val internalCheckedChangeListener: (View, Boolean) -> Unit = { checkBox, checked ->
        fields.forEach { it.setTextColor(defaultColor) }
        selectedCheckBoxes().forEach { it.setTextColor(checkBoxTextColor) }
        onCheckedChangeListener(checkBox, checked)
    }

    //endregion

    //region Checkbox Creation Helpers

    private fun createCheckBox(
        label: String,
        tag: Int,
        border: BackgroundDrawable.Border
    ): CheckBox {
        val verticalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_vertical_padding)
        ).toInt()
        val horizontalPaddingEditTextLeft = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_left)
        ).toInt()
        val horizontalPaddingEditTextRight = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_right)
        ).toInt()

        val checkBox = CheckBox(context).apply {
            id = View.generateViewId()
            text = label
            this.tag = tag
            isFocusable = true
            isClickable = true
            buttonDrawable = null
            textSize = 20f

            background = createSelectableThemedBackground(context, border, themeColor)

            setOnClickListener { internalCheckedChangeListener(this, this.isChecked) }

            setPadding(
                horizontalPaddingEditTextLeft,
                verticalPaddingEditText,
                horizontalPaddingEditTextRight,
                verticalPaddingEditText
            )

            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.layoutParams = layoutParams
        }

        Handler().post {
            checkBox.background = checkBox.createSelectableThemedBackground(
                context, Both, themeColor
            )
        }

        return checkBox
    }

    //endregion

    init {
        this.let {
            id = R.id.multipleChoicePart
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = VERTICAL
        }
    }
}
