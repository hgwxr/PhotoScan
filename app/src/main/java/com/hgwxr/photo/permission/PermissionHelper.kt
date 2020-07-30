package com.hgwxr.photo.permission

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.hgwxr.photo.R

object PermissionHelper {

    const val keyPermission="permissionString"
    const val tagPermission="tagPermission"
    const val permissionRequestCode=1000
    lateinit var fragmentActivity: FragmentActivity
    fun init(activity: FragmentActivity) {
        fragmentActivity = activity
    }

    lateinit var navController: NavController
    fun initNavController(controller: NavController) {
        navController = controller
    }

    fun applyPermission(permission: String) {
        val bundle = Bundle()
        bundle.putString(keyPermission,permission)
        val beginTransaction = fragmentActivity.supportFragmentManager.beginTransaction()
        beginTransaction.add(PermissionFragment::class.java,bundle,tagPermission).commitAllowingStateLoss()
    }
}