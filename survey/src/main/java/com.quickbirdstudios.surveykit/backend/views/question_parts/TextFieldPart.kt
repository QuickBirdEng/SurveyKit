package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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

    val field: EditText

    init {
        val verticalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_vertical_padding)
        ).toInt()
        val horizontalPaddingEditText = context.px(
            context.resources.getDimension(R.dimen.text_field_horizontal_padding_left)
        ).toInt()

        field = EditText(context).apply {
            id = R.id.textFieldPartField
            inputType = InputType.TYPE_CLASS_TEXT
            isFocusable = true
            isFocusableInTouchMode = true
            isClickable = true
            background = null

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

        this.addView(field)
    }

    companion object {
        fun withHint(context: Context, hint: String) = TextFieldPart(context).apply {
            field.hint = hint
        }
    }
}
