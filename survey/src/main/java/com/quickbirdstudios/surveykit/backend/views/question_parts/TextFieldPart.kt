package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal open class TextFieldPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleRes), StyleablePart {

    override fun style(surveyTheme: SurveyTheme) {}

    val field: TextInputEditText

    val fieldInfo: TextInputLayout

    init {
        val verticalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_vertical_padding)
        ).toInt()
        val horizontalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_left)
        ).toInt()

        fieldInfo = TextInputLayout(context).apply {
            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.layoutParams = layoutParams
        }

        field = TextInputEditText(context).apply {
            id = R.id.textFieldPartField
            inputType = InputType.TYPE_CLASS_TEXT
            isFocusable = true
            isFocusableInTouchMode = true
            isClickable = true
            background = null
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(9))

            setHintTextColor(ContextCompat.getColor(context, R.color.hint_grey))

            setPadding(
                horizontalPaddingEditText,
                verticalPaddingEditText,
                horizontalPaddingEditText,
                verticalPaddingEditText
            )

            setTextColor(ContextCompat.getColor(context, R.color.survey_text))

            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.layoutParams = layoutParams
        }

        this.background = context.resources.getDrawable(R.drawable.input_border, null)

        fieldInfo.addView(field)
        this.addView(fieldInfo)
    }

    companion object {
        fun withHint(context: Context, hint: String) = TextFieldPart(context).apply {
            field.hint = hint
        }
    }
}
