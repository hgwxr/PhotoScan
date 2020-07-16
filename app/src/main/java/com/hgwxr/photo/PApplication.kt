package com.hgwxr.photo

import android.app.Application
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.hgwxr.photo.data.Repository
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext


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