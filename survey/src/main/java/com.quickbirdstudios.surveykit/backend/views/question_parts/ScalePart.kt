package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.BOTTOM
import androidx.constraintlayout.widget.ConstraintSet.CHAIN_SPREAD
import androidx.constraintlayout.widget.ConstraintSet.LEFT
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.constraintlayout.widget.ConstraintSet.RIGHT
import androidx.constraintlayout.widget.ConstraintSet.TOP
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

internal class ScalePart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs),
    StyleablePart {

    constructor(
        context: Context,
        minimumValue: Int,
        minimumValueDescription: String,
        maximumValue: Int,
        maximumValueDescription: String,
        step: Float,
        defaultValue: Float
    ) : this(context) {
        this.minimumValue = minimumValue
        this.maximumValue = maximumValue
        this.step = step
        maxValueDescription = maximumValueDescription
        minValueDescription = minimumValueDescription
        selected = defaultValue.invert().toFloat()
        seekBar.max = calculateMaxValue()
    }

    //region Members

    private var minimumValue: Int = 0
    private var maximumValue: Int = 10
    private var step = 1f
    private var defaultValue = 0f

    private var minimumValueDescriptionField: TextView
    private var maximumValueDescriptionField: TextView
    private var currentValueDescriptionField: TextView
    private var seekBar: AppCompatSeekBar


    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            seekBar.tickMark = ContextCompat.getDrawable(context, R.drawable.tickmark)!!.apply {
                setTint(surveyTheme.themeColorDark)
            }
        }
    }

    //endregion

    //region Public API

    var selected: Float
        get() = seekBar.progress.convert()
        set(value) {
            seekBar.progress = value.toInt()
        }

    var minValueDescription: String
        get() = minimumValueDescriptionField.text.toString()
        set(value) {
            minimumValueDescriptionField.text = value
        }

    var maxValueDescription: String
        get() = maximumValueDescriptionField.text.toString()
        set(value) {
            maximumValueDescriptionField.text = value
        }

    //endregion

    //region Private API: Calculations

    // TODO add checks to prevent faulty calculations
    private fun calculateMaxValue(): Int =
        ((this.maximumValue - this.minimumValue) / this.step).toInt()

    private fun Int.convert(): Float =
        (this + this@ScalePart.minimumValue) * this@ScalePart.step

    private fun Float.invert(): Int =
        ((this - this@ScalePart.minimumValue) / this@ScalePart.step).toInt()

    //endregion

    //region Private API: Views

    private var currentValueDescription: String
        get() = currentValueDescriptionField.text.toString()
        set(value) {
            currentValueDescriptionField.text = value
        }

    private fun <T : View> T.setRandomViewId(): T = this.apply { id = View.generateViewId() }

    private fun horizontalLayoutConstraintSet(): ConstraintSet = ConstraintSet().apply {
        constrainHeight(minimumValueDescriptionField.id, WRAP_CONTENT)
        connect(minimumValueDescriptionField.id, BOTTOM, PARENT_ID, BOTTOM)

        constrainHeight(maximumValueDescriptionField.id, WRAP_CONTENT)
        connect(maximumValueDescriptionField.id, BOTTOM, PARENT_ID, BOTTOM)

        constrainHeight(seekBar.id, WRAP_CONTENT)
        createHorizontalChain(
            PARENT_ID,
            LEFT,
            PARENT_ID,
            RIGHT,
            listOf(
                minimumValueDescriptionField.id,
                seekBar.id,
                maximumValueDescriptionField.id
            ).toIntArray(),
            listOf(1f, 8f, 1f).toFloatArray(),
            CHAIN_SPREAD
        )

        constrainHeight(currentValueDescriptionField.id, WRAP_CONTENT)
        createVerticalChain(
            PARENT_ID,
            TOP,
            seekBar.id,
            TOP,
            listOf(currentValueDescriptionField.id, seekBar.id).toIntArray(),
            listOf(1f, 1f).toFloatArray(),
            CHAIN_SPREAD
        )
        centerHorizontally(currentValueDescriptionField.id, PARENT_ID)
    }

    //endregion

    init {
        val descriptionTextSize =
            context.resources.getDimension(R.dimen.scale_part_description_text_size)
        val selectedTextSize = context.resources.getDimension(R.dimen.scale_part_selected_text_size)
        val textPadding = context.resources.getDimension(R.dimen.scale_part_text_padding).toInt()

        this.let {
            minimumValueDescriptionField = TextView(context).setRandomViewId().apply {
                this.textAlignment = View.TEXT_ALIGNMENT_CENTER
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionTextSize)
                this.setTextColor(ContextCompat.getColor(context, R.color.survey_text));
                this.setPadding(textPadding, textPadding, textPadding, 0)
            }
            maximumValueDescriptionField = TextView(context).setRandomViewId().apply {
                this.textAlignment = View.TEXT_ALIGNMENT_CENTER
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, descriptionTextSize)
                this.setTextColor(ContextCompat.getColor(context, R.color.survey_text));
                this.setPadding(textPadding, textPadding, textPadding, 0)
            }
            currentValueDescriptionField = TextView(context).setRandomViewId().apply {
                this.textAlignment = View.TEXT_ALIGNMENT_CENTER
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTextSize)
                this.setTextColor(ContextCompat.getColor(context, R.color.survey_text));
                this.setPadding(textPadding, textPadding, textPadding, 0)
            }
            seekBar = AppCompatSeekBar(context).apply {
                id = R.id.seekBarPartField
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                seekBar.tickMark = ContextCompat.getDrawable(context, R.drawable.tickmark)
            }

            addView(minimumValueDescriptionField)
            addView(seekBar)
            addView(maximumValueDescriptionField)
            addView(currentValueDescriptionField)

            horizontalLayoutConstraintSet().applyTo(this)
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentValueDescription = progress.convert().toInt().toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
