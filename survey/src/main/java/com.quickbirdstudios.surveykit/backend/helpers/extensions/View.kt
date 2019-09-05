package com.quickbirdstudios.surveykit.backend.helpers.extensions

import android.view.View
import android.widget.LinearLayout

fun View.verticalMargin(margin: Int) {
    (this.layoutParams as LinearLayout.LayoutParams).setMargins(0, margin, 0, margin)
}

fun View.horizontalMargin(margin: Int) {
    (this.layoutParams as LinearLayout.LayoutParams).setMargins(margin, 0, margin, 0)
}
