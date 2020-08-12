package com.hgwxr.photo.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgwxr.photo.data.*
import com.hgwxr.photo.data.model.LoginInfo
import kotlinx.coroutines.launch

data class ProcessState(val message: String, val success: Boolean, val init: Boolean)
data class LoginState(val message: String, val success: Boolean, val init: Boolean)

class PhoneLoginViewModel : ViewModel() {
    val buttonEnable = MutableLiveData<Boolean>()
    val loginForm = MutableLiveData<String>()
    val clickNextStep = MutableLiveData<Boolean>()
    val processBar = MutableLiveData<ProcessState>()
    val loginState = MutableLiveData<LoginState>()

    init {
        buttonEnable.value = false
        clickNextStep.value = false
        processBar.value = ProcessState("", false, false)
        loginState.value = LoginState("", false, false)
    }

    fun checkButton(phone: String) {
        if (phone.length < 11) {
            loginForm.value = "不是正确的手机号"
            buttonEnable.value = false
        } else {
            loginForm.value = ""
            buttonEnable.value = true
        }
    }

    fun clickNextStep() {
        clickNextStep.value = true
    }

    fun clickBack(): Boolean {
        clickNextStep.value = false.takeIf {
            clickNextStep.value!!
        }
        return clickNextStep.value!!
    }

    fun performNextStep(phone: String, checkCode: String) {
        if (clickNextStep.value!!) {
            performLogin(phone, checkCode)
        } else {
            clickNextStep.value = true
        }
    }

    fun performLogin(phone: String, checkCode: String) {
        viewModelScope.launch {
            val params = mutableMapOf<String, Any>()
            params["username"] = phone
            params["mobile"] = phone
            params["password"] = "666666"
            params["verifycode"] = checkCode
            params["rememberMe"] = 1
            try {
                val response = Repository.postMethodLoading<LoginInfo>(ApiCode.LOGIN_V2, params)
                Log.e("performNextStep:", response.toString())
                response.let {
                    LocalRepository.saveUserInfo(it)
                }
                loginState.value = LoginState("登录成功", true, init = true)
            } catch (e: Throwable) {
                if (e is NetException) {
                    loginState.value = LoginState(e.msg, false, init = true)
                } else {
                    loginState.value = e.message?.let { LoginState(it, false, init = true) }
                }
            }
        }
    }

    fun performSendCheckCode(phone: String) {
        viewModelScope.launch {
            val params = mutableMapOf<String, Any>()
            params["mobile"] = phone
            try {
                val result = Repository.postMethodLoading<String>(ApiCode.SEND_CODE, params)
                Log.e("performNextStep:", result.toString())
                processBar.value = result?.let { ProcessState(it, true, init = true) }
            } catch (e: Throwable) {
                e.printStackTrace()
                if (e is NetException) {
                    processBar.value = ProcessState(e.msg, false, init = true)
                } else {
                    processBar.value = e.message?.let { ProcessState(it, false, init = true) }
                }
            }
        }

    }

}