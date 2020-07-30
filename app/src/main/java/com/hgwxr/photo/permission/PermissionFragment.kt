package com.hgwxr.photo.permission

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.ToastUtils

class PermissionFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFullScreen)
        arguments?.getString(PermissionHelper.keyPermission)?.let {
            context?.let { _context ->
                val checkSelfPermission = ContextCompat.checkSelfPermission(_context, it)
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    val notNotify = shouldShowRequestPermissionRationale(it)
                    if (notNotify) {
                        ToastUtils.showToast("not any notify apply permission")
                    } else {
                        requestPermissions(arrayOf(it), PermissionHelper.permissionRequestCode)
                        ToastUtils.showToast("apply permission")
                    }
                } else {
                    ToastUtils.showToast("has $it permission")
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==PermissionHelper.permissionRequestCode) {
            findNavController().popBackStack()
            val iterator = grantResults.iterator()
            while (iterator.hasNext()) {
                val nextInt = iterator.nextInt()
                if (nextInt==PackageManager.PERMISSION_DENIED) {
                    ToastUtils.showToast("没有获取到权限 ")
                }else{
                    ToastUtils.showToast("onRequestPermissionsResult  $requestCode ${permissions.toString()}  ")
                }
            }
        }else{
            ToastUtils.showToast("else :")
        }
    }
}