package com.hgwxr.photo.ui.home.content

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgwxr.photo.data.ApiCode
import com.hgwxr.photo.data.Repository
import com.hgwxr.photo.data.model.ContentModel
import com.hgwxr.photo.utils.ToastUtils
import kotlinx.coroutines.launch
import java.lang.Exception

data class ListFormState<T>(val success: Boolean, val contentList: MutableList<T>) {
}

data class LoadNextForm(val success: Int, val message: String, val type:Int=0) {
    fun success(): Boolean {
        return success == 0
    }

    fun over(): Boolean {
        return success == 1
    }

    fun error(): Boolean {
        return success == -1
    }
    fun  isInit(): Boolean {
        return type==1
    }
}

class ContentsViewModel : ViewModel() {
    val list = MutableLiveData<List<ContentModel>>()
    val mainListForm = MutableLiveData<ListFormState<ContentModel>>()
    val loadNext = MutableLiveData<LoadNextForm>()

    init {
        list.value = mutableListOf()
        loadNext.value = LoadNextForm(0, "")
        mainListForm.value =
            ListFormState(true, contentList = mutableListOf())
    }

    private suspend fun loadRecommend(): MutableList<ContentModel>? {
        mPage=1
        val params = mutableMapOf<String, Any>()
        params["page"] = mPage
        return Repository.getMethod<MutableList<ContentModel>>(ApiCode.RECOMMEND, params)
    }

    private var mPage = 1
    fun loadNextRecommend() {
        viewModelScope.launch {
            try {
                val method = loadRecommend()
                method?.let {
                    Log.e("performLoadData=", it.toString())
                    val size = it.size
                    val empty = size > 0
                    mPage++.takeIf {
                        empty
                    }
                    val value = mainListForm.value
                    value?.let { _value ->
                        _value.contentList.addAll(method)
                        loadNext.value = LoadNextForm(success = if (empty) 0 else 1,message =  "加载完成...")
                        mainListForm.value = ListFormState<ContentModel>(
                            success = true,
                            contentList = _value.contentList
                        )
                        ToastUtils.showToast("" + _value.contentList.size)
                    }
                }
            } catch (e: Exception) {
                loadNext.value = LoadNextForm(success = -1, message = "加载失败...")
            }


        }
    }

    fun performLoadData() {
        mPage = 1
        if (isMainPage()) {
            viewModelScope.launch {
                try {
                    val method = loadRecommend()
                    method?.let {
                        Log.e("performLoadData=", it.toString())
                        val size = it.size
                        mPage++.takeIf { size > 0 }
                        mainListForm.value = ListFormState(
                            success = true,
                            contentList = it
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    mainListForm.value = ListFormState(
                        success = false,
                        contentList = mutableListOf()
                    )
                }
            }
        } else if (isVideoPage()) {
            viewModelScope.launch {
                try {
                    val params = mutableMapOf<String, Any>()
                    params["page"] = 1
                    val method = Repository.getMethod<String>(ApiCode.VIDEO, params)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else if (isPicPage()) {

        }
    }

    fun isMainPage(): Boolean {
        return mType == ContentsFragment.TYPE_INTRODUCE
    }

    fun isVideoPage(): Boolean {
        return mType == ContentsFragment.TYPE_VIDEO
    }

    fun isPicPage(): Boolean {
        return mType == ContentsFragment.TYPE_PICTURE
    }

    var mType: Int = 0
    fun setType(type: Int) {
        mType = type
    }

    // TODO: Implement the ViewModel
    init {


    }
}