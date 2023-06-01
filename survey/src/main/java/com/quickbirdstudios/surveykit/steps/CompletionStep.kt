package com.quickbirdstudios.surveykit.steps

import android.content.Context
import androidx.annotation.RawRes
import com.airbnb.lottie.parser.moshi.JsonReader
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.FinishQuestionView
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.StepResult

class CompletionStep(
    private val title: String? = null,
    private val text: String? = null,
    private val buttonText: String = "Finish",
    private val lottieAnimation: LottieAnimation? = null,
    private val repeatCount: Int = 0,
    override val isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier()
) : Step {
    override fun createView(context: Context, stepResult: StepResult?): StepView =
        FinishQuestionView(
            context = context,
            title = title,
            text = text,
            finishButtonText = buttonText,
            lottieAnimation = lottieAnimation,
            repeatCount = repeatCount
        )

    sealed class LottieAnimation {
        data class RawResource(@RawRes val id: Int) : LottieAnimation()
        data class Asset(val name: String) : LottieAnimation()
        data class WithJsonReader(val jsonReader: JsonReader, val cacheKey: String) :
            LottieAnimation()

        data class FromJson(val jsonString: String, val cacheKey: String) : LottieAnimation()
        data class Animation(val animation: android.view.animation.Animation) : LottieAnimation()
        data class FromUrl(val url: String) : LottieAnimation()
    }
}
