package com.quickbirdstudios.survey_kit.public_api.result

import android.os.Parcelable

interface QuestionResult : Result, Parcelable {
    val stringIdentifier: String
}

