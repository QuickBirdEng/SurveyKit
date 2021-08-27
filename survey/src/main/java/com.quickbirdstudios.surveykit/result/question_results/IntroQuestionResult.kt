package com.quickbirdstudios.surveykit.result.question_results

import android.os.Parcelable
import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class IntroQuestionResult(
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    override val stringIdentifier: String = ""
) : QuestionResult, Parcelable
