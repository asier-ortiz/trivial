package app.trivial.asier.controller

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import app.trivial.asier.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogManager {

    private var alertDialog: AlertDialog? = null

    private fun hide() {
        if (alertDialog != null) {
            alertDialog!!.cancel()
        }
    }

    fun showCorrectDialog(context: Context, onCancelListener: DialogInterface.OnCancelListener) {
        hide()
        val builder = MaterialAlertDialogBuilder(context)

        builder.setView(R.layout.dialog_correct)
            .setCancelable(true)
            .setOnCancelListener(onCancelListener)

        alertDialog = builder.create()

        if (alertDialog!!.window != null) {
            alertDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }

        alertDialog!!.show()
    }

    fun showInCorrectDialog(context: Context, onCancelListener: DialogInterface.OnCancelListener) {
        hide()
        val builder = MaterialAlertDialogBuilder(context)

        builder.setView(R.layout.dialog_incorrect)
            .setCancelable(true)
            .setOnCancelListener(onCancelListener)

        alertDialog = builder.create()

        if (alertDialog!!.window != null) {
            alertDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }

        alertDialog!!.show()
    }

    fun showTimeOutDialog(context: Context, onCancelListener: DialogInterface.OnCancelListener) {
        hide()
        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(R.layout.dialog_time_out)
            .setCancelable(true)
            .setOnCancelListener(onCancelListener)
        alertDialog = builder.create()
        if (alertDialog!!.window != null) {
            alertDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
        alertDialog!!.show()
    }
}