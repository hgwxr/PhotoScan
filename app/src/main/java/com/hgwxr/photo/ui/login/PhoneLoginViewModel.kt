package com.hgwxr.photo.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.hgwxr.photo.data.ApiCode
import com.hgwxr.photo.data.Repository
import com.hgwxr.photo.data.Result
import com.hgwxr.photo.data.model.BaseResult
import com.hgwxr.photo.data.model.CheckMobileModel
import com.hgwxr.photo.data.model.TestModelParent
import com.hgwxr.photo.utils.ToastUtils
import com.hgwxr.photo.widgets.LoadingDialog
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

    fun clickBack() {
        clickNextStep.value = false
    }

    fun performNextStep(phone: String,checkCode:String) {
        if (clickNextStep.value!!){
            performLogin(phone,checkCode)
        }else{
            clickNextStep.value = true
        }
//        ToastUtils.showToast("获取验证码")
//        viewModelScope.launch{
//            try {
//                val test = Repository.testList<BaseResult<TestModelParent>>()
//                val test = Repository.testListResult<BaseResult<TestModelParent>>()
//            ToastUtils.showToast(test)
//                Log.e("performNextStep:",test.toString())
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//    }
    }

    fun performLogin(phone: String, checkCode: String) {
        viewModelScope.launch {
            LoadingDialog.startLoading()

            val checkMobile = mutableMapOf<String, Any>()
            val checkMobileResult = Repository.getRequest<BaseResult<CheckMobileModel>>(ApiCode.CHECK_MOBILE, checkMobile)
            if (checkMobileResult is Result.Success) {
                val data = checkMobileResult.data
                if (data.success()) {
                    if (data.success()) {
                        data.data?.let {
                            if (it.used()) {

                            }
                        }
                    } else {

                    }
                } else {

                }
            }


            val params = mutableMapOf<String, Any>()
            params["username"] = phone
            params["password"] = phone
            params["verifycode"] = checkCode
            params["rememberMe"] = 1
//            val result = Repository.postRequest<BaseResult<String>>(ApiCode.LOGIN, params)
//            LoadingDialog.hideLoadingDialog()
//            Log.e("performLogin:", result.toString())
//            if (result is Result.Success) {
//                val data = result.data
//                if (data.success()) {
//                    if (data.success()) {
//                        loginState.value = LoginState(data.msg, true, init = true)
//                    } else {
//                        loginState.value = LoginState(data.msg, false, init = true)
//                    }
//                } else {
//                    loginState.value = LoginState(result.toString(), false, init = true)
//                }
//            }

        }
    }

    fun performSendCheckCode(phone: String) {
        viewModelScope.launch {
            val params = mutableMapOf<String, Any>()
            params["mobile"] = phone
            LoadingDialog.startLoading()
//            val result = Repository.postRequest<BaseResult<String>>(ApiCode.SEND_CODE, params)
//            LoadingDialog.hideLoadingDialog()
//            Log.e("performNextStep:", result.toString())
//            if (result is Result.Success) {
//                val data = result.data
//                if (data.success()) {
//                    processBar.value = data.data?.let { ProcessState(it, true, init = true) }
//                } else {
//                    processBar.value = ProcessState(data.msg, false, init = true)
//                }
//            } else {
//                processBar.value = ProcessState(result.toString(), false, init = true)
////                Snackbar.make()
//            }
        }

    }

}