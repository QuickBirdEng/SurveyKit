package com.quickbirdstudios.surveykit.result

import android.os.Parcelable

interface QuestionResult : Result, Parcelable {
    val stringIdentifier: String
}
