package com.quickbirdstudios.survey_kit.survey

import android.os.Bundle
import com.quickbirdstudios.survey_kit.result.QuestionResult
import com.quickbirdstudios.survey_kit.result.TaskResult
import com.quickbirdstudios.survey_kit.steps.Step
import java.util.*

// NOT USED
private class SurveyState(
    val history: Stack<Step>, val taskResult: TaskResult, val questionResult: QuestionResult?
) {

    fun serialize(): Bundle =
        Bundle().apply {
            putSerializable(HistoryKey, history)
            putParcelable(TaskResultKey, taskResult)
            putParcelable(CurrentQuestionResult, questionResult)
        }

    companion object {
        fun deserialize(bundle: Bundle): SurveyState =
            SurveyState(
                history = bundle.getSerializable(HistoryKey) as Stack<Step>,
                taskResult = bundle.getParcelable(TaskResultKey),
                questionResult = bundle.getParcelable(CurrentQuestionResult)
            )

        private const val HistoryKey = "history"
        private const val TaskResultKey = "taskResult"
        private const val CurrentQuestionResult = "currentQuestionResult"
    }
}
