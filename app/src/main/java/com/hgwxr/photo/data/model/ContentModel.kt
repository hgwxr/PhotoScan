package com.hgwxr.photo.data.model

class ContentModel {
    var chits: String = ""
    var cid: String = ""
    var collection: String = ""
    var create_uid: String = ""
    var ctime: String = ""
    var fabulous: String = ""
    var id: String = ""
    var isfollow: String=""
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
    var userInfo: UserInfoNew? = null


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
}