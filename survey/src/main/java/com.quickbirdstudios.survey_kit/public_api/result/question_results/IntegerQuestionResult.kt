package com.quickbirdstudios.survey_kit.public_api.result.question_results

import com.quickbirdstudios.survey_kit.public_api.Identifier
import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class IntegerQuestionResult(
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    override val stringIdentifier: String,
    val answer: Int?
) : QuestionResult
