package com.quickbirdstudios.survey_kit.backend.helpers

val Any.logTag: String get() = this::class.java.simpleName.orEmpty().take(23)
