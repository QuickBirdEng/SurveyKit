package com.quickbirdstudios.survey_kit.backend.views.step

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.views.main_parts.*
import com.quickbirdstudios.survey_kit.backend.views.question_parts.InfoText
import com.quickbirdstudios.survey_kit.public_api.FinishReason
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.SurveyTheme
import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult
import java.util.*

// TODO rename and move to own file
abstract class StepView(
    context: Context,
    override val id: StepIdentifier,
    override val isOptional: Boolean
) : FrameLayout(context), ViewActions, StyleablePart {

    protected var onNextListener: (QuestionResult) -> Unit = {}
    override fun onNext(block: (QuestionResult) -> Unit) {
        onNextListener = block
    }

    protected var onBackListener: (QuestionResult) -> Unit = {}
    override fun onBack(block: (QuestionResult) -> Unit) {
        onBackListener = block
    }

    protected var onCloseListener: (QuestionResult, FinishReason) -> Unit = { _, _ -> }
    override fun onClose(block: (QuestionResult, FinishReason) -> Unit) {
        onCloseListener = block
    }

    protected var onSkipListener: () -> Unit = {}
    override fun onSkip(block: () -> Unit) {
        onSkipListener = block
    }

    override fun back() = onBackListener(createResults())

    abstract fun setupViews()
    open fun onViewCreated() = Unit
}

abstract class QuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes private val title: Int?,
    @StringRes private val text: Int?,
    @StringRes private val nextButtonText: Int
) : StepView(context, id, isOptional), ViewActions, Stateful {


    //region Members

    private val root: View = View.inflate(context, R.layout.view_question, this)
    var header: Header = root.findViewById(R.id.questionHeader)
    var content: Content = root.findViewById(R.id.questionContent)
    var footer: Footer = content.findViewById(R.id.questionFooter)

    val startDate: Date = Date()

    //endregion


    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        header.style(surveyTheme)
        content.style(surveyTheme)
    }

    //endregion


    //region Abstracts

    abstract override fun createResults(): QuestionResult

    override var state: QuestionResult? = null

    abstract override fun isValidInput(): Boolean

    //endregion


    //region Open Helpers

    @CallSuper
    override fun setupViews() {
        title?.let { InfoText.title(context, it) }?.let(content::add)
        text?.let { InfoText.info(context, it) }?.let(content::add)

        header.onBack = { onBackListener(createResults()) }
        //TODO add translations and move out of this class
        header.onCancel = {
            Dialogs.cancel(
                context,
                "Leave?",
                "If you leave now, your current answers are lost.",
                "Back to the survey",
                "Cancel Survey"
            ) {
                onCloseListener(createResults(), FinishReason.Discarded)
            }
        }
    }

    override fun onViewCreated() {
        super.onViewCreated()

        footer.canContinue = isValidInput()
        footer.onContinue = { onNextListener(createResults()) }
        footer.onSkip = { onSkipListener() }
        footer.questionCanBeSkipped = isOptional
        footer.setContinueButtonText(nextButtonText)

        setState()
    }

    //endregion

}
