package com.quickbirdstudios.surveykit.backend.helpers.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout

fun View.verticalMargin(margin: Int) {
    (this.layoutParams as LinearLayout.LayoutParams).setMargins(0, margin, 0, margin)
}

fun View.horizontalMargin(margin: Int) {
    (this.layoutParams as LinearLayout.LayoutParams).setMargins(margin, 0, margin, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
