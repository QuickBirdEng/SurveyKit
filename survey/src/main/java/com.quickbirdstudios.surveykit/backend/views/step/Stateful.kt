package com.quickbirdstudios.surveykit.backend.views.step

import com.quickbirdstudios.surveykit.result.QuestionResult

interface Stateful {
    var state: QuestionResult?
    fun setState()
}
