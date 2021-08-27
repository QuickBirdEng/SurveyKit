package com.quickbirdstudios.surveykit.result.question_results

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class LocationQuestionResult(
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    val answer: AnswerFormat.LocationAnswerFormat.Location?
) : QuestionResult
