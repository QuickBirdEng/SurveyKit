package com.quickbirdstudios.surveykit

import androidx.annotation.ColorInt
import com.quickbirdstudios.surveykit.backend.views.main_parts.AbortDialogConfiguration

// Todo rename to configuration for next major version
data class SurveyTheme(
    @ColorInt val themeColorDark: Int,
    @ColorInt val themeColor: Int,
    @ColorInt val textColor: Int,
    val abortDialogConfiguration: AbortDialogConfiguration? = null
)
