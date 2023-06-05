package com.quickbirdstudios.surveykit.backend.helpers.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

fun GradientDrawable.colorStroke(width: Int, color: Int): Drawable {
    val drawable = this.mutate() as GradientDrawable
    drawable.setStroke(width, color)
    return drawable
}
