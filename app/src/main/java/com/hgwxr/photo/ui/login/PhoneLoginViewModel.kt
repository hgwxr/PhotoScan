package com.hgwxr.photo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hgwxr.photo.utils.ToastUtils

class PhoneLoginViewModel : ViewModel() {
    val buttonEnable = MutableLiveData<Boolean>()
    val loginForm = MutableLiveData<String>()

    init {
        buttonEnable.value = false
    }

    fun checkButton(phone: String) {
        if (phone.length < 11) {
            loginForm.value = "不是正确的手机号"
        } else {
            loginForm.value = ""
            buttonEnable.value = true
        }
    }

    fun performNextStep(phone: String) {
        ToastUtils.showToast("获取验证码")
    }

}