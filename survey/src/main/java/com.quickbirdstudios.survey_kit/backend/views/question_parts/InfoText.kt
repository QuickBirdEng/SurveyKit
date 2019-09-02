package com.quickbirdstudios.survey_kit.backend.views.question_parts

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.quickbirdstudios.survey_kit.backend.views.main_parts.StyleablePart
import com.quickbirdstudios.survey_kit.public_api.SurveyTheme
import com.quickbirdstudios.triangle.survey.R

internal class InfoText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0
) : TextView(context, attrs, defStyleRes), StyleablePart {

    init {
        this.apply {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            textAlignment = TEXT_ALIGNMENT_CENTER
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            this.layoutParams = layoutParams
        }
    }

    override fun style(surveyTheme: SurveyTheme) = Unit

    fun setTextSize(@DimenRes resourceId: Int) {
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(resourceId))
    }

    companion object {
        fun title(context: Context, @StringRes text: Int) = InfoText(context).apply {
            setText(text)
            setTextSize(R.dimen.question_info_title_text_size)
            val horizontalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_horizontal_padding)
            val verticalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_info_title_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }

        fun info(context: Context, @StringRes text: Int) = InfoText(context).apply {
            setText(text)
            setTextSize(R.dimen.question_info_text_text_size)
            val horizontalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_horizontal_padding)
            val verticalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }

        fun question(context: Context, @StringRes text: Int) = InfoText(context).apply {
            setText(text)
            setTextSize(R.dimen.question_text_size)
            val horizontalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_horizontal_padding)
            val verticalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_vertical_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }
    }
}
