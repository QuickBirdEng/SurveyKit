package com.quickbirdstudios.surveykit.backend.views.step

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.AbortDialogConfiguration
import com.quickbirdstudios.surveykit.backend.views.main_parts.Content
import com.quickbirdstudios.surveykit.backend.views.main_parts.Dialogs
import com.quickbirdstudios.surveykit.backend.views.main_parts.Footer
import com.quickbirdstudios.surveykit.backend.views.main_parts.Header
import com.quickbirdstudios.surveykit.backend.views.question_parts.InfoTextPart
import com.quickbirdstudios.surveykit.result.QuestionResult
import java.util.Date

abstract class QuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    private val title: String?,
    private val text: String?,
    private val nextButtonText: String
) : StepView(context, id, isOptional), ViewActions {

    //region Members

    private val root: View = View.inflate(context, R.layout.view_question, this)
    var header: Header = root.findViewById(R.id.questionHeader)
    var content: Content = root.findViewById(R.id.questionContent)
    var footer: Footer = content.findViewById(R.id.questionFooter)
    private var abortDialogConfiguration: AbortDialogConfiguration? = null

    val startDate: Date = Date()

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        header.style(surveyTheme)
        content.style(surveyTheme)
        abortDialogConfiguration = surveyTheme.abortDialogConfiguration
    }

    //endregion

    //region Abstracts

    abstract override fun createResults(): QuestionResult

    abstract override fun isValidInput(): Boolean

    //endregion

    //region Open Helpers

    @CallSuper
    override fun setupViews() {
        title?.let { InfoTextPart.title(context, it) }?.let(content::add)
        text?.let { InfoTextPart.info(context, it) }?.let(content::add)

        header.onBack = { onBackListener(createResults()) }
        // TODO add translations and move out of this class
        header.onCancel = {
            Dialogs.cancel(
                context,
                AbortDialogConfiguration(
                    abortDialogConfiguration?.title ?: R.string.abort_dialog_title,
                    abortDialogConfiguration?.message ?: R.string.abort_dialog_message,
                    abortDialogConfiguration?.neutralMessage
                        ?: R.string.abort_dialog_neutral_message,
                    abortDialogConfiguration?.negativeMessage ?: R.string.abort_dialog_neutral_message
                )
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
    }

//endregion
}
