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
import java.util.Date
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

    override suspend fun invoke(
        transition: Presenter.Transition,
        step: Step,
        stepResult: StepResult?
    ): NextAction {
        val viewToPresent = step.createView(context, stepResult)
        return showAndWaitForResult(step.id, viewToPresent, transition)
    }

    override fun triggerBackOnCurrentView() {
        currentQuestionView?.back()
    }

    //endregion

    //region Private API

    private suspend fun showAndWaitForResult(
        id: StepIdentifier,
        questionView: StepView,
        transition: Presenter.Transition
    ): NextAction {
        val stepResult = StepResult(id = id, startDate = Date())

        showView(questionView, transition)

        return suspendCoroutine { routine ->
            questionView.onNext { result ->
                questionView.invalidateCallBacks()
                stepResult.results.add(result)
                routine.resume(NextAction.Next(stepResult))
            }
            questionView.onBack { result ->
                questionView.invalidateCallBacks()
                stepResult.results.add(result)
                routine.resume(NextAction.Back(stepResult))
            }
            questionView.onSkip {
                questionView.invalidateCallBacks()
                routine.resume(NextAction.Skip)
            }
            questionView.onClose { result, reason ->
                questionView.invalidateCallBacks()
                stepResult.results.add(result)
                routine.resume(NextAction.Close(stepResult, reason))
            }
        }
    }

    /*
    call this after the first callback of a StepView is called (onNext, onBack, onSkip, onClose)
    Reason: if any callback is called twice (or 2 different callbacks are called) the coroutine
    would be resumed twice -> crash
    For now this works perfect: the callback is executed and no further callback gets through (new
    views have new callbacks instantiated, so no issue there)

    TODO: evaluate on how to do this properly
     */
    private fun StepView.invalidateCallBacks() {
        this.onNext { }
        this.onBack { }
        this.onSkip { }
        this.onClose { _, _ -> }
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
        questionView.style(surveyTheme)

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

    //endregion
}
