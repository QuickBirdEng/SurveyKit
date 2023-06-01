package com.quickbirdstudios.surveykit.backend.views.main_parts

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color

internal class Dialogs {

    companion object {
        fun cancel(
            context: Context,
            abortDialogConfiguration: AbortDialogConfiguration,
            cancelAction: () -> Unit
        ): AlertDialog? {
            val dialog = AlertDialog.Builder(context)
                .setMessage(abortDialogConfiguration.message)
                .setCancelable(true)
                .setNegativeButton(abortDialogConfiguration.negativeMessage) { _, which ->
                    if (which == DialogInterface.BUTTON_NEGATIVE)
                        cancelAction()
                }
                .setNeutralButton(abortDialogConfiguration.neutralMessage) { _, _ -> }
                .create()

            dialog.setTitle(abortDialogConfiguration.title)
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
