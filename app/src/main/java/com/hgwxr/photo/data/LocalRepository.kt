package com.hgwxr.photo.data

import com.alibaba.fastjson.JSON
import com.hgwxr.photo.data.model.ConfigModel
import com.hgwxr.photo.data.model.LoginInfo
import com.tencent.mmkv.MMKV


const val keyLocalConfigModel = "keyLocalConfigModel"
const val keyCurrentUser = "keyCurrentUser"

object LocalRepository {
    private val kv = MMKV.defaultMMKV()


    fun saveString(k: String, value: String) {
        kv.encode(k, value)
    }

    fun getString(key: String): String? {
        return kv.getString(key, "")
    }

    var configModel:ConfigModel?=null

    fun getLocalConfigModel(): ConfigModel? {
        if (configModel==null) {
            val configInfo = getString(keyLocalConfigModel)
            configModel=configInfo?.let {
                return@let JSON.parseObject(configInfo, ConfigModel::class.java)
            }
        }
        return configModel
    }

    var mLoginInfo: LoginInfo? = null
    fun getLoginInfo(): LoginInfo? {
        if (mLoginInfo==null) {
            val string = getString(keyCurrentUser)
            mLoginInfo= string?.let {
               return@let JSON.parseObject(it,LoginInfo::class.java)
            }
        }
        return mLoginInfo
    }


    fun saveUserInfo( loginInfo: LoginInfo) {
        mLoginInfo=loginInfo
        saveString(keyCurrentUser, JSON.toJSONString(loginInfo))
    }
}