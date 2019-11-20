package com.quickbirdstudios.surveykit.backend.views.step

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.Content
import com.quickbirdstudios.surveykit.backend.views.main_parts.Dialogs
import com.quickbirdstudios.surveykit.backend.views.main_parts.Footer
import com.quickbirdstudios.surveykit.backend.views.main_parts.Header
import com.quickbirdstudios.surveykit.backend.views.question_parts.InfoText
import com.quickbirdstudios.surveykit.result.QuestionResult
import java.util.*

abstract class QuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes private val title: Int?,
    @StringRes private val text: Int?,
    @StringRes private val nextButtonText: Int
) : StepView(context, id, isOptional), ViewActions {

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
    }

    //endregion

}
