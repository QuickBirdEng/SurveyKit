package com.quickbirdstudios.surveykit.backend.views.main_parts

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color

internal class Dialogs {

    companion object {
        fun cancel(
            context: Context,
            title: String,
            message: String,
            neutralMessage: String,
            cancelMessage: String,
            cancelAction: () -> Unit
        ): AlertDialog? {
            val dialog = AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(cancelMessage) { _, which ->
                    if (which == DialogInterface.BUTTON_NEGATIVE) cancelAction()
                }
                .setNeutralButton(neutralMessage) { _, _ -> }
                .create()


            dialog.setTitle(title)
            dialog.show()
            dialog.setButtonColors()
            return dialog
        }
    }
}

private fun AlertDialog.setButtonColors() {
    val negativeButton = this.getButton(DialogInterface.BUTTON_NEGATIVE) ?: return
    negativeButton.setTextColor(Color.RED)
}
