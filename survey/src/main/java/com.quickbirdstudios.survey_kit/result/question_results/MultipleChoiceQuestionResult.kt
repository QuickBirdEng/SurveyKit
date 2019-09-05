package com.quickbirdstudios.survey_kit.result.question_results

import com.quickbirdstudios.survey_kit.Identifier
import com.quickbirdstudios.survey_kit.TextChoice
import com.quickbirdstudios.survey_kit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MultipleChoiceQuestionResult(
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    override val stringIdentifier: String,
    val answer: List<TextChoice>
) : QuestionResult
