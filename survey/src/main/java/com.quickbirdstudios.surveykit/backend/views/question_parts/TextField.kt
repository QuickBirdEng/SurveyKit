package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal open class TextField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0
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
        fun withHint(context: Context, @StringRes hint: Int) = TextField(
            context
        ).apply {
            field.hint = context.getString(hint)
        }
    }
}
