package com.hgwxr.photo.widgets

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.hgwxr.photo.R

object LoadingDialog {
    var activity: Activity? = null
    var alertDialog: AlertDialog? = null
    fun initDialog(activity: Activity) {
        this.activity = activity
    }

    fun startLoading() {
        startLoadingDialog(false)
    }

    fun startLoadingDialog(cancelable: Boolean) {
        activity?.let {
            if (alertDialog != null) {
                alertDialog!!.show()
            } else {
                val builder = AlertDialog.Builder(it, R.style.LoadingDialog)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setView(R.layout.dialog_loading)
                } else {
                    val inflate = it.layoutInflater.inflate(R.layout.dialog_loading, null)
                    builder.setView(inflate)
                }
                builder.setCancelable(cancelable)
                val create = builder.create()
                alertDialog = create
                alertDialog!!.show()
            }

        }
    }

    fun hideLoadingDialog() {
        alertDialog?.dismiss()
    }
}