package com.quickbirdstudios.survey_kit.result

import android.os.Parcelable

interface QuestionResult : Result, Parcelable {
    val stringIdentifier: String
}

