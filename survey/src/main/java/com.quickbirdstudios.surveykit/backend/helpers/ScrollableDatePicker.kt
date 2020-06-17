package com.quickbirdstudios.surveykit.backend.helpers

import android.content.Context
import android.view.MotionEvent
import android.widget.DatePicker


internal class ScrollableDatePicker(context: Context) : DatePicker(context) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            parent?.requestDisallowInterceptTouchEvent(true)
        }
        return false
    }

}
