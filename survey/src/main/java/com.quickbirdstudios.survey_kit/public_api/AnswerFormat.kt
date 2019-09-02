package com.quickbirdstudios.survey_kit.public_api

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
}


@Parcelize
data class TextChoice(
    @StringRes val text: Int,
    @StringRes val value: Int = text
) : Parcelable

/*

data class TimeAnswerFormat(val defaultHour: Int, val defaultMinute: Int) :
    AnswerFormat()


data class DateAnswerFormat(
    val defaultYear: Int,
    val defaultMonth: Int,
    val defaultDay: Int
) : AnswerFormat()


class DateTimeAnswerFormat(
    val defaultHour: Int,
    val defaultMinute: Int,
    val defaultYear: Int,
    val defaultMonth: Int,
    val defaultDay: Int
) : AnswerFormat()

data class BooleanAnswerFormat(val yesString: String, val noString: String) : AnswerFormat()



*/
