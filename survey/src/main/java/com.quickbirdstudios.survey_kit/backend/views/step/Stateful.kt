package com.quickbirdstudios.survey_kit.backend.views.step

import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult

interface Stateful {
    var state: QuestionResult?
    fun setState()
}
