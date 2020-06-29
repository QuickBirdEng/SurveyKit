package com.quickbirdstudios.surveykit.result.question_results

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DateTimeQuestionResult(
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    val answer: AnswerFormat.DateTimeAnswerFormat.DateTime?
) : QuestionResult
