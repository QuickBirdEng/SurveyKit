package com.quickbirdstudios.surveykit.backend.views.question_parts.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.Gravity
import android.widget.Checkable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.backend.helpers.extensions.px

@Suppress("unused")
internal fun Checkable.createSelectableThemedBackground(
    context: Context,
    border: BackgroundDrawable.Border,
    themeColor: Int
): Drawable = BackgroundCreationHelper().createSelectableBackground(
    context, border, themeColor
)

private class BackgroundCreationHelper {

    //region Public API

    fun createSelectableBackground(
        context: Context,
        border: BackgroundDrawable.Border,
        color: Int
    ): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            listOf(android.R.attr.state_checked, android.R.attr.state_pressed).toIntArray(),
            createLayerDrawable(
                context,
                BackgroundDrawable.Pressed(border),
                IconDrawable.Check(),
                color
            )
        )
        stateListDrawable.addState(
            listOf(android.R.attr.state_pressed).toIntArray(),
            createLayerDrawable(
                context,
                BackgroundDrawable.Pressed(border),
                IconDrawable.Default(),
                color
            )
        )
        stateListDrawable.addState(
            listOf(android.R.attr.state_checked).toIntArray(),
            createLayerDrawable(
                context,
                BackgroundDrawable.Default(border),
                IconDrawable.Check(),
                color
            )
        )
        stateListDrawable.addState(
            emptyArray<Int>().toIntArray(),
            createLayerDrawable(
                context,
                BackgroundDrawable.Default(border),
                IconDrawable.Default(),
                color
            )
        )

        return stateListDrawable
    }

    //endregion

    //region Private API

    private fun createLayerDrawable(
        context: Context,
        @DrawableRes backgroundDrawableId: Int,
        @DrawableRes iconDrawableId: Int?,
        color: Int
    ): LayerDrawable {
        val layerList = mutableListOf<Drawable>()
        layerList.add(ContextCompat.getDrawable(context, backgroundDrawableId)!!)
        if (iconDrawableId != null) {
            val item = ContextCompat.getDrawable(context, iconDrawableId)!!.apply {
                setTint(color)
            }
            layerList.add(item)
        }
        val layerDrawable = LayerDrawable(layerList.toTypedArray())

        // this positions the icon correctly
        if (iconDrawableId != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layerDrawable.setLayerGravity(1, Gravity.CENTER_VERTICAL or Gravity.END)
                layerDrawable.setLayerInset(
                    1,
                    0,
                    context.px(8).toInt(),
                    context.px(20).toInt(),
                    context.px(8).toInt()
                )
            } else {
                layerDrawable.setLayerInset(
                    1,
                    context.px(320).toInt(),
                    context.px(8).toInt(),
                    context.px(20).toInt(),
                    context.px(8).toInt()
                )
            }
        }
        return layerDrawable
    }

    //endregion

    //region SubClasses#

    private enum class IconDrawable {
        Default, Check;

        @DrawableRes
        operator fun invoke(): Int? = when (this) {
            Default -> null
            Check -> R.drawable.check
        }
    }

    //endregion
}

internal enum class BackgroundDrawable {
    Default, Pressed;

    @DrawableRes
    operator fun invoke(border: Border): Int = when (border) {
        Border.Both -> when (this) {
            Default -> R.drawable.input_border
            Pressed -> R.drawable.input_border_pressed
        }
        Border.Bottom -> when (this) {
            Default -> R.drawable.input_border_bottom_border
            Pressed -> R.drawable.input_border_bottom_border_pressed
        }
    }

    enum class Border { Both, Bottom }
}
