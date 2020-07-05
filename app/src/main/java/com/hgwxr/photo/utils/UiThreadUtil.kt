package com.hgwxr.wechartfiletools.utils

import android.os.Handler
import android.os.Looper


/**
 * Utility for interacting with the UI thread.
 */
object UiThreadUtil {
    private var sMainHandler: Handler? = null
    /**
     * @return `true` if the current thread is the UI thread.
     */
    val isOnUiThread: Boolean
        get() = Looper.getMainLooper().thread === Thread.currentThread()



    /**
     * Runs the given `Runnable` on the UI thread.
     */
    fun runOnUiThread(runnable: Runnable?) {
        synchronized(UiThreadUtil::class.java) {
            if (sMainHandler == null) {
                sMainHandler = Handler(Looper.getMainLooper())
            }
        }
        sMainHandler!!.post(runnable)
    }
}