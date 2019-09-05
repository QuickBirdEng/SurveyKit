package com.quickbirdstudios.survey_kit.result.question_results

import android.os.Parcelable
import com.quickbirdstudios.survey_kit.Identifier
import com.quickbirdstudios.survey_kit.result.QuestionResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class IntroQuestionResult(
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    override val stringIdentifier: String = ""
) : QuestionResult, Parcelable
