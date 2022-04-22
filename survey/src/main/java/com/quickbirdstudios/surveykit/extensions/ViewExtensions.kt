package com.quickbirdstudios.surveykit.extensions

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.getNonNullText() = this.text?.toString() ?: ""
