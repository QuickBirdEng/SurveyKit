package com.quickbirdstudios.survey_kit.backend.presenter

import android.content.Context
import android.widget.FrameLayout
import com.quickbirdstudios.survey_kit.public_api.SurveyTheme
import com.quickbirdstudios.survey_kit.public_api.result.StepResult
import com.quickbirdstudios.survey_kit.public_api.steps.Step


interface Presenter {
    val context: Context
    val viewContainer: FrameLayout
    val surveyTheme: SurveyTheme

    suspend fun present(transition: Transition, step: Step, stepResult: StepResult?): NextAction
    fun triggerBackOnCurrentView()

    enum class Transition {
        None, SlideFromRight, SlideFromLeft;
    }
}

