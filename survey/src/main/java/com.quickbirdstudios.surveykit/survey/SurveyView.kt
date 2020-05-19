package com.quickbirdstudios.surveykit.survey

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.Task
import com.quickbirdstudios.surveykit.backend.navigator.TaskNavigator
import com.quickbirdstudios.surveykit.backend.presenter.NextAction
import com.quickbirdstudios.surveykit.backend.presenter.Presenter
import com.quickbirdstudios.surveykit.backend.presenter.PresenterImpl
import com.quickbirdstudios.surveykit.backend.result_gatherer.ResultGatherer
import com.quickbirdstudios.surveykit.backend.result_gatherer.ResultGathererImpl
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.Step
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurveyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleRes), Survey, CoroutineScope {

    //region Members

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private lateinit var taskNavigator: TaskNavigator
    private lateinit var resultGatherer: ResultGatherer
    private lateinit var presenter: Presenter

    //endregion

    //region Public API

    // TODO theme should be not set here but when creating the survey
    override fun start(task: Task, surveyTheme: SurveyTheme) {
        taskNavigator = TaskNavigator(task = task)
        resultGatherer = ResultGathererImpl(task = task)
        presenter = PresenterImpl(
            context = context,
            surveyTheme = surveyTheme,
            viewContainer = this
        )
        startSurvey()
    }

    //endregion

    //region Public API

    private fun startSurvey() = launch {
        var stepData = firstStep()

        while (true) {
            stepData = when (stepData) {
                is StepData.Next -> {
                    onStepResult(stepData.step, stepData.result)
                    stepData.step()
                }
                is StepData.Previous -> {
                    onStepResult(stepData.step, stepData.result)
                    stepData.step()
                }
                is StepData.Skip -> {
                    onStepResult(stepData.step, null)
                    stepData.step()
                }
                is StepData.Finish -> {
                    onStepResult(stepData.step, stepData.stepResult)
                    onSurveyFinish(resultGatherer.taskResult, stepData.finishReason)
                    return@launch
                }
                is StepData.ClosePreemptively -> {
                    onSurveyFinish(resultGatherer.taskResult, stepData.finishReason)
                    return@launch
                }
            }
        }
    }

    override fun backPressed() {
        presenter.triggerBackOnCurrentView()
    }

    //endregion

    //region Overrides

    override var onStepResult: (Step?, StepResult?) -> Unit = { _, _ -> }

    override var onSurveyFinish: (TaskResult, FinishReason) -> Unit = { _, _ -> }

    //endregion

    //region Private API

    private suspend fun firstStep(): StepData {
        val previousStep = taskNavigator.lastStepInHistory()
        val previousStepResult = if (previousStep != null) {
            resultGatherer.retrieve(previousStep.id)
        } else null
        val firstStep =
            taskNavigator.startStep(previousStepResult) ?: return StepData.ClosePreemptively(
                null, FinishReason.Completed
            )

        val stepResult = resultGatherer.retrieve(firstStep.id)
        val result = presenter(Presenter.Transition.None, firstStep, stepResult).storeResult()
        return StepData(
            step = firstStep,
            action = result
        )
    }

    private suspend fun StepData.Next.step(): StepData {
        val newStep = taskNavigator.nextStep(step, result) ?: return StepData(
            FinishReason.Completed
        )
        val newResult = presenter(
            Presenter.Transition.SlideFromRight, newStep, resultGatherer.retrieve(newStep.id)
        ).storeResult()
        return StepData(
            step = newStep,
            action = newResult
        )
    }

    private suspend fun StepData.Previous.step(): StepData {
        val previousStep =
            taskNavigator.previousStep(step) ?: return StepData(
                FinishReason.Failed
            )
        val newResult = presenter(
            Presenter.Transition.SlideFromLeft,
            previousStep,
            resultGatherer.retrieve(previousStep.id)
        ).storeResult()
        return StepData(
            step = previousStep,
            action = newResult
        )
    }

    private suspend fun StepData.Skip.step(): StepData {
        val newStep = taskNavigator.nextStep(step) ?: return StepData(
            FinishReason.Completed
        )
        val newResult = presenter(
            Presenter.Transition.SlideFromRight, newStep, resultGatherer.retrieve(newStep.id)
        ).storeResult()
        return StepData(
            step = newStep,
            action = newResult
        )
    }

    private fun NextAction.storeResult() = this.apply {
        when (this) {
            is NextAction.Next -> resultGatherer.store(this.result)
            is NextAction.Back -> resultGatherer.store(this.result)
            NextAction.Skip -> Unit
            is NextAction.Close -> resultGatherer.store(this.result)
        }
    }

    //endregion

    //region SubTypes

    private sealed class StepData {
        data class Next(val step: Step, val result: StepResult) : StepData()
        data class Previous(val step: Step, val result: StepResult) : StepData()
        data class Skip(val step: Step) : StepData()
        data class Finish(
            val step: Step,
            val stepResult: StepResult,
            val finishReason: FinishReason
        ) : StepData()

        data class ClosePreemptively(
            val stepResult: StepResult?,
            val finishReason: FinishReason
        ) : StepData()

        companion object {
            operator fun invoke(step: Step, action: NextAction): StepData = when (action) {
                is NextAction.Next -> Next(
                    step,
                    action.result
                )
                is NextAction.Back -> Previous(
                    step,
                    action.result
                )
                NextAction.Skip -> Skip(
                    step
                )
                is NextAction.Close -> Finish(
                    step,
                    action.result,
                    action.finishReason
                )
            }

            operator fun invoke(finishReason: FinishReason) =
                ClosePreemptively(
                    null,
                    finishReason
                )
        }
    }

    //endregion
}
