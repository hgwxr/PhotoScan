package com.hgwxr.photo.data

import com.tencent.mmkv.MMKV


object LocalRepository {
    private val kv = MMKV.defaultMMKV()


    fun saveString(k: String, value: String) {
        kv.decodeString(k, value)
    }
}