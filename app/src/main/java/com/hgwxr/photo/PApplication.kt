package com.hgwxr.photo

import android.app.Application

class PApplication : Application() {
    companion object {
        var _context: Application? = null
        fun getContext(): Application {
            return _context!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }
}