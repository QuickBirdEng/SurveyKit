package com.quickbirdstudios.survey_kit.public_api

import android.graphics.Color

//TODO remove default values and call it Theme or SurveyTheme
data class SurveyTheme(
    val themeColorDark: Int = Color.GREEN,
    val themeColor: Int = Color.GREEN,
    val textColor: Int = Color.GREEN
)
