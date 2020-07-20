package com.hgwxr.photo.ui.home.content

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.hgwxr.photo.data.ApiCode
import com.hgwxr.photo.data.Repository
import com.hgwxr.photo.data.model.ContentModel
import kotlinx.coroutines.launch
import java.lang.Exception

class ContentsViewModel : ViewModel() {
    val list = MutableLiveData<List<ContentModel>>()

    init {
        list.value = mutableListOf()
    }

    fun performLoadData() {
        if (isMainPage()) {
            viewModelScope.launch {
                try {
                    val params = mutableMapOf<String, Any>()
                    params["page"] = 1
                    val method = Repository.getMethod<String>(ApiCode.RECOMMEND, params)
                    method?.let {
                        Log.e("performLoadData=", it.toString())
                        val parseArray = JSON.parseArray(method, ContentModel::class.java)
                        list.value = parseArray
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun isMainPage(): Boolean {
        return mType == ContentsFragment.TYPE_INTRODUCE
    }

    var mType: Int = 0
    fun setType(type: Int) {
        mType = type
    }

    // TODO: Implement the ViewModel
    init {


    }
}