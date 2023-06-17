package com.example.contacts.presentation.common

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setAction("Settings") {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", this.context.packageName, null)
            intent.data = uri
            this.context.startActivity(intent)
        }.show()
}
