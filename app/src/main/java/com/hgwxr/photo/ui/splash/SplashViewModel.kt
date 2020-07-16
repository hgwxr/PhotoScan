package com.hgwxr.photo.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgwxr.photo.data.LocalRepository
import com.hgwxr.photo.data.Repository
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {


    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION, // Authentication failed
        ELSE  // Authentication failed
    }

    val authenticationState = MutableLiveData<AuthenticationState>()

    init {
        // In this example, the user is always unauthenticated when MainActivity is launched
        authenticationState.value = AuthenticationState.ELSE
    }

    fun checkLogin() {
        viewModelScope.launch {
            val configModel = Repository.getConfigModel()
            Log.e("SplashViewModel", configModel.toString())
            Log.e("SplashViewModel", "over")
            val loginInfo = LocalRepository.getLoginInfo()
            if (loginInfo == null) {
                authenticationState.value = AuthenticationState.UNAUTHENTICATED
            } else {
                authenticationState.value = AuthenticationState.AUTHENTICATED
            }
        }
    }
}