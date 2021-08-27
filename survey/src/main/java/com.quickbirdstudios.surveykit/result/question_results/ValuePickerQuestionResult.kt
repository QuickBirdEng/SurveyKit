package com.quickbirdstudios.surveykit.result.question_results

import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class ValuePickerQuestionResult(
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    val answer: String?
) : QuestionResult
