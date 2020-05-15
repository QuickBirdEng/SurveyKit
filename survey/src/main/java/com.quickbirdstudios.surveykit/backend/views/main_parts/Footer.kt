package com.quickbirdstudios.surveykit.backend.views.main_parts

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.helpers.extensions.colorStroke
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px

class Footer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : Toolbar(context, attrs, defStyleRes), StyleablePart {

    //region Public API

    var themeColor = Color.RED
        set(newColor) {
            buttonContinue.colorMainButtonEnabledState(canContinue, newColor)
            buttonSkip.setTextColor(newColor)
            field = newColor
        }

    var questionCanBeSkipped: Boolean = false
        get() = buttonSkip.visibility != View.VISIBLE
        set(canBeSkipped) {
            buttonSkip.visibility = if (canBeSkipped) View.VISIBLE else View.GONE
            field = canBeSkipped
        }

    var canContinue: Boolean
        get() = buttonContinue.isEnabled
        set(state) {
            buttonContinue.isEnabled = state
            buttonContinue.colorMainButtonEnabledState(state, themeColor)
        }

    fun setContinueButtonText(text: String) {
        buttonContinue.text = text
    }

    var onContinue: () -> Unit = {}
    var onSkip: () -> Unit = {}

    //endregion

    //region Members

    private val root: View = View.inflate(context, R.layout.layout_footer, this)
    private val buttonContinue = root.findViewById<Button>(R.id.button_continue).apply {
        setOnClickListener {
            hideKeyboard()
            onContinue()
        }
        colorMainButtonEnabledState(true, themeColor)
    }
    private val buttonSkip = root.findViewById<Button>(R.id.button_skip_question).apply {
        setOnClickListener {
            hideKeyboard()
            onSkip()
        }
        colorSkipButton(themeColor)
    }

    //endregion

    //region Private API

    private fun Button.colorMainButtonEnabledState(enabled: Boolean, color: Int) {
        val drawable = context.resources.getDrawable(R.drawable.main_button_background, null)
        if (enabled) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            (drawable as GradientDrawable).colorStroke(context.px(1f).toInt(), color)
            setTextColor(color)
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.disabled_grey))
        }
        background = drawable
    }

    private fun Button.colorSkipButton(color: Int) {
        setTextColor(color)
    }

    // TODO this should probably not be done here
    private fun hideKeyboard() {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        themeColor = surveyTheme.themeColor
    }

    //endregion
}
