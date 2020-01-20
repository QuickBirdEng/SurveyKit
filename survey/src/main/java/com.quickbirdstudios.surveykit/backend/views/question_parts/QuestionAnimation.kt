package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart
import com.quickbirdstudios.surveykit.steps.CompletionStep.LottieAnimation
import com.quickbirdstudios.surveykit.steps.CompletionStep.LottieAnimation.*

internal class QuestionAnimation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0,
    animation: LottieAnimation?,
    repeatCount: Int?
) : LinearLayout(context, attrs, defStyleRes), StyleablePart {

    override fun style(surveyTheme: SurveyTheme) {}

    private fun LottieAnimationView.setup(repeatCount: Int?, animation: LottieAnimation?) {
        repeatCount?.let { this.repeatCount = it }
        when (animation) {
            is RawResource -> setAnimation(animation.id)
            is Asset -> setAnimation(animation.name)
            is WithJsonReader -> setAnimation(animation.jsonReader, animation.cacheKey)
            is FromJson -> this.setAnimationFromJson(
                animation.jsonString,
                animation.jsonString
            )
            is Animation -> this.animation = animation.animation
            is FromUrl -> setAnimationFromUrl(animation.url)
            null -> Unit
        }
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        val root = layoutInflater.inflate(R.layout.lottie_checkmark_wrapper, this, false)

        val lottieView = root.findViewById<LottieAnimationView>(R.id.animation_view).apply {
            setup(repeatCount, animation)
        }

        val size = context.px(160f).toInt()
        addView(lottieView, LayoutParams(size, size))

        this.gravity = Gravity.CENTER
    }
}
