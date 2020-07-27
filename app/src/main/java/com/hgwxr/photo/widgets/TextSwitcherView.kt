package com.hgwxr.photo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.core.content.ContextCompat
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class TextSwitchView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : TextSwitcher(mContext, attrs), ViewSwitcher.ViewFactory {
    private fun init() {
        setFactory(this)
    }

    var mArrays = mutableListOf<String>()
    fun setTextArray(arrays: List<String>) {
        if (mArrays.size > 0) {
            return
        }
        mArrays.addAll(arrays)
    }

    suspend fun start() {
        ToastUtils.showToast(mArrays.toString())
        withContext(Dispatchers.Default) {
            while (true) {
                mArrays.forEach { s ->
                    withContext(Dispatchers.Main) {
                        setText(s)
                    }
                    delay(2000)
                }
            }
        }
    }

    suspend fun startDefault() {
        val mutableListOf = mutableListOf<String>("文本1", "文本1", "文本2", "文本3", "文本4", "文本5")
        setTextArray(mutableListOf)
        start()
    }

    override fun makeView(): View {
        val textView = TextView(mContext)
        textView.textSize = 18f
        textView.gravity = Gravity.CENTER
        textView.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        textView.setTextColor(ContextCompat.getColor(context, R.color.color33))
        return textView
    }

    init {
        init()
//        val translateInAnimation = TranslateAnimation(1.0f,0f,1.0f,0f)
//        val translateOutAnimation = TranslateAnimation(0f,1.0f,0f,1.0f)
         inAnimation = AnimationUtils.loadAnimation(
            context,
            android.R.anim.slide_in_left
        )
         outAnimation = AnimationUtils.loadAnimation(
            context,
            android.R.anim.slide_in_left
        )
    }
}