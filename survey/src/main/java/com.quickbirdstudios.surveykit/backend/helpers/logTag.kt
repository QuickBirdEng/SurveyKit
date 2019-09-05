package com.quickbirdstudios.surveykit.backend.helpers

val Any.logTag: String get() = this::class.java.simpleName.orEmpty().take(23)
