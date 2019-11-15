package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.imageSelector.ImageSelectorPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ImageSelectorResult

internal class ImageSelectorQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val answerFormat: AnswerFormat.ImageSelectorFormat,
    private var preselected: List<Int>?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var imageSelectorPart: ImageSelectorPart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult = ImageSelectorResult(
        id = id,
        startDate = startDate,
        answer = imageSelectorPart.selected,
        stringIdentifier = imageSelectorPart.selected.joinToString(",")
    )

    override fun isValidInput(): Boolean = imageSelectorPart.isOneSelected()

    override fun setState() {}

    override fun setupViews() {
        super.setupViews()

        imageSelectorPart = content.add(ImageSelectorPart(context))
        imageSelectorPart.update(answerFormat.imageChoiceList)
        imageSelectorPart.numberOfColumns = answerFormat.numberOfColumns
        imageSelectorPart.selected = preselected ?: answerFormat.defaultSelectedImagesIndices
    }

    //endregion

}
