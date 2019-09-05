package com.quickbirdstudios.survey_kit

sealed class NavigationRule {
    data class DirectStepNavigationRule(
        val destinationStepStepIdentifier: StepIdentifier
    ) : NavigationRule()

    data class ConditionalDirectionStepNavigationRule(
        val resultToStepIdentifierMapper: (String) -> StepIdentifier?
    ) : NavigationRule()
}
