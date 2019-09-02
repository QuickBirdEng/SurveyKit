package com.quickbirdstudios.survey_kit.public_api

import androidx.annotation.ColorInt

//TODO remove default values and call it Theme or SurveyTheme
data class SurveyTheme(
    @ColorInt val themeColorDark: Int,
    @ColorInt val themeColor: Int,
    @ColorInt val textColor: Int
)
