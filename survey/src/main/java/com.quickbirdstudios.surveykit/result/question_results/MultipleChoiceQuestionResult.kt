package com.quickbirdstudios.surveykit.result.question_results

import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.result.QuestionResult
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MultipleChoiceQuestionResult(
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    override val stringIdentifier: String,
    val answer: List<TextChoice>
) : QuestionResult
