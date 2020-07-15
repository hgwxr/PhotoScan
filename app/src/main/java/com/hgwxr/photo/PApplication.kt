package com.hgwxr.photo

import android.app.Application
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.tencent.mmkv.MMKV


class PApplication : MultiDexApplication() {
    companion object {
        var _context: Application? = null
        fun getContext(): Application {
            return _context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
        val initialize = MMKV.initialize(this)
        Log.d("PApplication", "mmkv-----path:$initialize")

    }
}