package com.hgwxr.photo.data.model

import com.hgwxr.photo.data.LocalRepository

class ContentModel {
    var chits: String = ""
    var cid: String = ""
    var collection: String = ""
    var create_uid: String = ""
    var ctime: String = ""
    var fabulous: String = ""
    var id: String = ""
    var isfollow: String = ""
    var level: String = ""
    var name: String = ""
    var open_window: String = ""
    var picArr: List<String> = mutableListOf()
    var picstr: String = ""
    var playnumber: String = ""
    var reco: String = ""
    var rtime: String = ""
    var status: String = ""
    var text_info: String = ""
    var d_type: String = ""//,1九图(微头条) 2图文(文章)，3视频(视频)，4问题，5纯文本 （当前版本只有1和3状态）
    var userInfo: UserInfoNew? = null
    var vod_url: String="" //视频地址
    var pic_url: String=""//封面
    fun getFormatPic(): List<String> {
        val localConfigModel = LocalRepository.getLocalConfigModel()
        return localConfigModel?.let {
            val hostUrl = it.getImgHost()
            return@let picArr.map { t -> hostUrl + t }
        } ?: picArr

    }
}


class UserInfoNew {
    var baseInfo: BaseInfo? = null
}

class BaseInfo {
    var avatar: String = ""
    var birthday: String = ""
    var comment: Int = 0
    var id: String = ""
    var mobile: String = ""
    var sex: Int = 0
    var truename: String = ""
    var user_mobile: String = ""
    var username: String = ""
    var vip_day: Int = 0
    var vip_endtime: String = ""
    fun getFormatAvatar(): String {
        val localConfigModel = LocalRepository.getLocalConfigModel()
        return localConfigModel?.let {
           return@let it.getImgHost()+avatar
        } ?: avatar
    }
}