package com.hgwxr.photo

import android.app.Dialog
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialDialogs
import com.hgwxr.photo.utils.StatusBarHelper
import com.hgwxr.photo.widgets.LoadingDialog
import name.gudong.loading.LoadingView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoadingDialog.initDialog(this)
        setContentView(R.layout.activity_main)
        val statusBarHelper = StatusBarHelper.create(this)
        statusBarHelper.setTranslucent(true)
        statusBarHelper.setBarDarkStyle()
        statusBarHelper.setDefaultColor()
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            statusBarHelper.setTranslucent(false)
            Toast.makeText(
                this@MainActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }

    }




}
