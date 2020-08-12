package com.hgwxr.photo.ui.home.content

import androidx.lifecycle.ViewModel
import com.hgwxr.photo.data.ApiCode
import com.hgwxr.photo.data.Repository
import com.hgwxr.photo.data.model.ContentModel

class RecommedViewModel : ViewModel() {
    private var mPage = 1
    suspend fun loadRecommed(): MutableList<ContentModel> {
        mPage = 1
        val params = mutableMapOf<String, Any>()
        params["page"] = mPage
        val method = Repository.getMethod<MutableList<ContentModel>>(ApiCode.RECOMMEND, params)
        if (method.size > 0) {
            mPage++
        }
        return method
    }

}