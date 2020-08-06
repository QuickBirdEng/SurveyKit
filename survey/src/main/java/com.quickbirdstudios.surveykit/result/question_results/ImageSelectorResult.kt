package com.quickbirdstudios.surveykit.result.question_results

import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.result.QuestionResult
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageSelectorResult(
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    val answer: List<Int>
) : QuestionResult
