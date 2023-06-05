package com.quickbirdstudios.surveykit.backend.views.main_parts

import com.quickbirdstudios.surveykit.SurveyTheme

// TODO not needed since styling info should be passed into the views in the constructor
internal interface StyleablePart {
    fun style(surveyTheme: SurveyTheme)
}
