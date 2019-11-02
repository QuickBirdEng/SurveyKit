package com.quickbirdstudios.surveykit

import android.os.Parcelable
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.regex.Pattern

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
        // TODO when creating the scale part anew, also create Vertical orientation
        enum class Orientation { Horizontal }
    }


    data class TextAnswerFormat(
        val maxLines: Int,
        @StringRes val hintText: Int? = null,
        val isValid: ((String) -> Boolean)? = null
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


    data class DateAnswerFormat(val defaultValue: Date?) : AnswerFormat() {
        @Parcelize
        data class Date(val day: Int, val month: Int, val year: Int) : Parcelable {
            override fun toString(): String {
                return "${this.day}:${this.month}:${this.year}"
            }
        }

        companion object {
            operator fun invoke(): DateAnswerFormat = DateAnswerFormat(
                defaultValue = Date(
                    day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    month = Calendar.getInstance().get(Calendar.MONTH),
                    year = Calendar.getInstance().get(Calendar.YEAR)
                )
            )
        }
    }


    data class TimeAnswerFormat(val defaultValue: Time?) : AnswerFormat() {
        @Parcelize
        data class Time(val hour: Int, val minute: Int) : Parcelable {
            override fun toString(): String {
                return "${this.hour}:${this.minute}"
            }
        }

        companion object {
            operator fun invoke(): TimeAnswerFormat = TimeAnswerFormat(
                defaultValue = Time(
                    hour = Calendar.getInstance().get(Calendar.HOUR),
                    minute = Calendar.getInstance().get(Calendar.MINUTE)
                )
            )
        }
    }


    data class EmailAnswerFormat(@StringRes val hintText: Int? = null) : AnswerFormat() {
        val isValid: (String) -> Boolean = { email ->
            val pattern = Pattern.compile("""^(.+)@(.+)\..+$""")
            pattern.matcher(email).matches()
        }
    }

}


@Parcelize // necessary because it is used in QuestionResults (Single and Multiple)
data class TextChoice(
    @StringRes val text: Int,
    @StringRes val value: Int = text
) : Parcelable

