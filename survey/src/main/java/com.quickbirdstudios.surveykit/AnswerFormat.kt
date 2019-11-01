package com.quickbirdstudios.surveykit

import android.os.Parcelable
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import kotlinx.android.parcel.Parcelize

sealed class AnswerFormat {
    data class IntegerAnswerFormat(
        val defaultValue: Int? = null,
        @StringRes val hint: Int = R.string.empty
    ) : AnswerFormat()


    data class SingleChoiceAnswerFormat(
        val textChoices: List<TextChoice>
    ) : AnswerFormat()

    data class MultipleChoiceAnswerFormat(
        val textChoices: List<TextChoice>
    ) : AnswerFormat()

    data class ScaleAnswerFormat(
        val maximumValue: Int,
        val minimumValue: Int,
        val defaultValue: Int = minimumValue,
        val step: Float,
        val orientation: Orientation = Orientation.Horizontal,
        @StringRes val maximumValueDescription: Int,
        @StringRes val minimumValueDescription: Int
    ) : AnswerFormat() {
        enum class Orientation { Horizontal, Vertical; }
    }


    data class TextAnswerFormat(
        val maximumLength: Int,
        val multipleLines: Boolean
    ) : AnswerFormat()

    data class BooleanAnswerFormat(
        @StringRes val positiveAnswerText: Int,
        @StringRes val negativeAnswerText: Int,
        val defaultValue: Result = Result.None
    ) : AnswerFormat() {

        enum class Result {
            None, PositiveAnswer, NegativeAnswer;
        }

        val textChoices = listOf(TextChoice(positiveAnswerText), TextChoice(negativeAnswerText))

        fun toResult(id: Int?) = when (id) {
            positiveAnswerText -> Result.PositiveAnswer
            negativeAnswerText -> Result.NegativeAnswer
            else -> null
        }
    }

    data class ValuePickerAnswerFormat(
        val choices: List<String>,
        val defaultValue: String? = null
    ) : AnswerFormat() {
        init {
            check(defaultValue == null || choices.contains(defaultValue)) {
                throw IllegalStateException(
                    "${ValuePickerAnswerFormat::class.simpleName}:" +
                        "${ValuePickerAnswerFormat::defaultValue.name}($defaultValue) " +
                        "has to be part of " + ValuePickerAnswerFormat::choices.name + "($choices)"
                )
            }
        }
    }
}


@Parcelize // TODO check if this is necessary
data class TextChoice(
    @StringRes val text: Int,
    @StringRes val value: Int = text
) : Parcelable

