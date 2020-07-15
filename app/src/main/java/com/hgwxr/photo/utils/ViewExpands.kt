package com.hgwxr.photo.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hgwxr.photo.R

fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_LONG): Snackbar {
    return Snackbar.make(this, message, duration)
        .setAction(R.string.str_dialog_btn_cancel) {
        }
}