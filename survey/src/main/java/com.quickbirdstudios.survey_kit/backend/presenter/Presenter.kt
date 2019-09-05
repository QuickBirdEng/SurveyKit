package com.quickbirdstudios.survey_kit.backend.presenter

import android.content.Context
import android.widget.FrameLayout
import com.quickbirdstudios.survey_kit.SurveyTheme
import com.quickbirdstudios.survey_kit.result.StepResult
import com.quickbirdstudios.survey_kit.steps.Step


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

