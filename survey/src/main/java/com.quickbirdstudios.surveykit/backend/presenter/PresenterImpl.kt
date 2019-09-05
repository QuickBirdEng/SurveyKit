package com.quickbirdstudios.surveykit.backend.presenter

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.presenter.animations.ViewAnimator
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.steps.Step
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


internal class PresenterImpl(
    override val context: Context,
    override val viewContainer: FrameLayout,
    override val surveyTheme: SurveyTheme
) : Presenter {


    //region Members

    private val viewAnimator: ViewAnimator =
        ViewAnimator(context)
    private var currentQuestionView: StepView? = null

    //endregion


    //region Public API

    override suspend fun present(
        transition: Presenter.Transition, step: Step, stepResult: StepResult?
    ): NextAction {
        val viewToPresent = step.createView(context, stepResult).apply(surveyTheme)
        return showAndWaitForResult(step.id, viewToPresent, transition)
    }


    override fun triggerBackOnCurrentView() {
        val results = currentQuestionView?.createResults() ?: return
        currentQuestionView?.back()
    }

    //endregion


    //region Private API

    private suspend fun showAndWaitForResult(
        id: StepIdentifier, questionView: StepView, transition: Presenter.Transition
    ): NextAction {
        val stepResult =
            StepResult(id = id, startDate = Date())
        showView(questionView, transition)
        return suspendCoroutine { routine ->
            questionView.onNext { result ->
                stepResult.results.add(result)
                routine.resume(
                    NextAction.Next(
                        stepResult
                    )
                )
            }
            questionView.onBack { result ->
                stepResult.results.add(result)
                routine.resume(
                    NextAction.Back(
                        stepResult
                    )
                )
            }
            questionView.onSkip {
                routine.resume(NextAction.Skip)
            }
            questionView.onClose { result, reason ->
                stepResult.results.add(result)
                routine.resume(
                    NextAction.Close(
                        stepResult,
                        reason
                    )
                )
            }
        }
    }

    private fun showView(questionView: StepView, transition: Presenter.Transition) {
        val previousQuestionView = currentQuestionView
        currentQuestionView = questionView

        viewContainer.addView(questionView)
        questionView.layoutParams.apply {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = LinearLayout.LayoutParams.MATCH_PARENT
        }
        questionView.setupViews()
        questionView.onViewCreated()

        when (transition) {
            Presenter.Transition.SlideFromRight -> viewAnimator.rightToLeft(
                viewContainer, ViewAnimator.PageSwipe(previousQuestionView, questionView)
            )
            Presenter.Transition.SlideFromLeft -> viewAnimator.leftToRight(
                viewContainer, ViewAnimator.PageSwipe(previousQuestionView, questionView)
            )
            Presenter.Transition.None -> Unit
        }
    }

    private fun StepView.apply(surveyTheme: SurveyTheme) = this.apply { this.style(surveyTheme) }

    //endregion


}
