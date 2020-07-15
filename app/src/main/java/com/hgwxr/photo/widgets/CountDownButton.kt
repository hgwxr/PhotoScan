package com.hgwxr.photo.widgets

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import java.util.concurrent.TimeUnit


class CountDownButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    var countDownTimer: CountDownTimer? = null

    init {

    }

    fun startCheckCode() {
        startCountDown(60)
    }

    private fun startCountDown(max: Long) {
        if (countDownTimer == null) {
            val oldText = text
            val countDownTimer1 = object : CountDownTimer(max * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    isEnabled = false
                    this@CountDownButton.text = "${(millisUntilFinished / 1000)}S"
                }

                override fun onFinish() {
                    isEnabled = true
                    this@CountDownButton.text = oldText
                }
            }
            this.countDownTimer = countDownTimer1
        } else {
            countDownTimer?.cancel()
        }
        countDownTimer?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countDownTimer?.cancel()
    }
}