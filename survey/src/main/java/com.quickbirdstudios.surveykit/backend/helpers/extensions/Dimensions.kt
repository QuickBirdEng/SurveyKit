package com.quickbirdstudios.surveykit.backend.helpers.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Context.dp(px: Number): Float {
    val resources = this.resources
    val metrics = resources.displayMetrics
    return (px.toFloat() /
        (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()))
}

fun Context.px(dp: Number): Float {
    val resources = this.resources
    val metrics = resources.displayMetrics
    return (dp.toFloat() *
        (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()))
}
