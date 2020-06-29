package com.quickbirdstudios.surveykit

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import java.util.Calendar
import java.util.Date as JavaDate
import java.util.regex.Pattern
import kotlinx.android.parcel.Parcelize

sealed class AnswerFormat {

    data class IntegerAnswerFormat(
        val defaultValue: Int? = null,
        val hint: String = ""
    ) : AnswerFormat()

    data class SingleChoiceAnswerFormat(
        val textChoices: List<TextChoice>,
        val defaultSelection: TextChoice? = null
    ) : AnswerFormat()

    data class MultipleChoiceAnswerFormat(
        val textChoices: List<TextChoice>,
        val defaultSelections: List<TextChoice> = emptyList()
    ) : AnswerFormat()

    data class ScaleAnswerFormat(
        val maximumValue: Int,
        val minimumValue: Int,
        val defaultValue: Int = minimumValue,
        val step: Float,
        val orientation: Orientation = Orientation.Horizontal,
        val maximumValueDescription: String,
        val minimumValueDescription: String
    ) : AnswerFormat() {
        // TODO when creating the scale part anew, also create Vertical orientation
        enum class Orientation { Horizontal }
    }

    data class TextAnswerFormat(
        val maxLines: Int,
        val hintText: String? = null,
        val isValid: ((String) -> Boolean) = { text -> text.isNotEmpty() }
    ) : AnswerFormat()

    data class BooleanAnswerFormat(
        val positiveAnswerText: String,
        val negativeAnswerText: String,
        val defaultValue: Result = Result.None
    ) : AnswerFormat() {

        enum class Result {
            None, PositiveAnswer, NegativeAnswer;
        }

        val textChoices = listOf(TextChoice(positiveAnswerText), TextChoice(negativeAnswerText))

        fun toResult(id: String?) = when (id) {
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

    data class DateAnswerFormat(
        val defaultValue: Date? = defaultDateValue(),
        val minDate: JavaDate? = null,
        val maxDate: JavaDate? = null
    ) : AnswerFormat() {
        @Parcelize
        data class Date(val day: Int, val month: Int, val year: Int) : Parcelable {
            override fun toString(): String {
                return "${this.day}:${this.month}:${this.year}"
            }
        }

        companion object {
            private fun defaultDateValue() = Date(
                day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                month = Calendar.getInstance().get(Calendar.MONTH),
                year = Calendar.getInstance().get(Calendar.YEAR)
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

    data class DateTimeAnswerFormat(val defaultValue: DateTime?) : AnswerFormat() {
        @Parcelize
        data class DateTime(
            val day: Int,
            val month: Int,
            val year: Int,
            val hour: Int,
            val minute: Int
        ) : Parcelable {
            override fun toString(): String {
                return "${day}/${month + 1}/${year} ${hour}:${minute}"
            }
        }

        companion object {
            operator fun invoke(): DateTimeAnswerFormat = DateTimeAnswerFormat(
                defaultValue = DateTime(
                    day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    month = Calendar.getInstance().get(Calendar.MONTH),
                    year = Calendar.getInstance().get(Calendar.YEAR),
                    hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    minute = Calendar.getInstance().get(Calendar.MINUTE)
                )
            )
        }
    }


    data class EmailAnswerFormat(
        val hintText: String? = null,
        val isValid: (String) -> Boolean = defaultEmailValidation
    ) : AnswerFormat()

    data class ImageSelectorFormat(
        @IntRange(from = 1, to = 5) val numberOfColumns: Int = 4,
        val imageChoiceList: List<ImageChoice>,
        val defaultSelectedImagesIndices: List<Int> = emptyList()
    ) : AnswerFormat() {
        init {
            require(numberOfColumns in 1..5) { "Number of columns supported: 1-5" }
        }
    }
}

@Parcelize // necessary because it is used in QuestionResults (Single and Multiple)
data class TextChoice(
    val text: String,
    val value: String = text
) : Parcelable

@Parcelize
data class ImageChoice(@DrawableRes val resourceId: Int) : Parcelable

private val defaultEmailValidation: (String) -> Boolean = { email ->
    val pattern = Pattern.compile("""^(\w*)@(.+)\..+$""")
    pattern.matcher(email).matches()
}
