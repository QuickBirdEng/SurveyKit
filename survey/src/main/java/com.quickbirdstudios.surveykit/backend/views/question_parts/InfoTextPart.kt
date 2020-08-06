package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal class InfoTextPart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : AppCompatTextView(context, attrs, defStyleRes), StyleablePart {

    init {
        this.apply {
            setTextColor(ContextCompat.getColor(context, R.color.survey_text))
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
        fun title(context: Context, text: String) = InfoTextPart(
            context
        ).apply {
            id = R.id.infoTextTitle
            setText(text)
            setTextSize(R.dimen.question_info_title_text_size)
            val horizontalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_horizontal_padding)
            val verticalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_info_title_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }

        fun info(context: Context, text: String) = InfoTextPart(
            context
        ).apply {
            id = R.id.infoTextInfo
            setText(text)
            setTextSize(R.dimen.question_info_text_text_size)
            val horizontalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_horizontal_padding)
            val verticalPadding =
                context.resources.getDimensionPixelSize(R.dimen.question_text_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }

        fun question(context: Context, @StringRes text: Int) = InfoTextPart(
            context
        ).apply {
            id = R.id.infoTextQuestion
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
