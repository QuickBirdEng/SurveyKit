package com.quickbirdstudios.survey_kit.backend.views.question_parts

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.helpers.extensions.px
import com.quickbirdstudios.survey_kit.backend.views.main_parts.StyleablePart
import com.quickbirdstudios.survey_kit.backend.views.question_parts.helper.BackgroundDrawable
import com.quickbirdstudios.survey_kit.backend.views.question_parts.helper.createSelectableThemedBackground
import com.quickbirdstudios.survey_kit.public_api.SurveyTheme
import com.quickbirdstudios.survey_kit.public_api.TextChoice

internal class MultipleChoicePart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart {


    //region Public API

    @ColorInt
    var themeColor: Int = ContextCompat.getColor(context, R.color.cyan_normal)
        set(color) {
            update(options)
            field = color
        }

    @ColorInt
    var textColor: Int = ContextCompat.getColor(context, R.color.cyan_text)
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
        textColor = surveyTheme.textColor
        update(options)
    }

    //endregion


    //region Private API

    private val fields: List<CheckBox>
        get() = (0 until this.childCount).mapNotNull { this.findViewWithTag(it) as? CheckBox }

    private fun update(list: List<TextChoice>) {
        val selectedChoices = selected
        this.removeAllViews()
        list.forEachIndexed { index, textChoice ->
            val item = if (index == list.size - 1) createLastCheckBox(textChoice.text, index)
            else createCheckBox(textChoice.text, index)
            if (selectedChoices.contains(textChoice)) {
                item.isChecked = true
                item.setTextColor(textColor)
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
        selectedCheckBoxes().forEach { it.setTextColor(textColor) }
        onCheckedChangeListener(checkBox, checked)
    }

    //endregion


    //region Checkbox Creation Helpers

    private fun createCheckBox(@StringRes label: Int, tag: Int): CheckBox {
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
            setText(label)
            this.tag = tag
            isFocusable = true
            isClickable = true
            buttonDrawable = null
            textSize = 20f
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
                context, BackgroundDrawable.Border.Top, themeColor
            )
        }

        return checkBox
    }

    private fun createLastCheckBox(@StringRes label: Int, tag: Int): CheckBox {
        val createCheckBox = createCheckBox(label, tag)
        Handler().post {
            createCheckBox.background = createCheckBox.createSelectableThemedBackground(
                context, BackgroundDrawable.Border.Both, themeColor
            )
        }
        return createCheckBox
    }

    //endregion


    init {
        this.let {
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = VERTICAL
        }
    }
}
