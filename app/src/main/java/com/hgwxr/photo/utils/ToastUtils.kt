package com.hgwxr.photo.utils

import android.widget.Toast
import com.hgwxr.photo.PApplication

object ToastUtils {
    fun showToast(text: String) {
        Toast.makeText(PApplication.getContext(), text, Toast.LENGTH_SHORT).show()
    }
}