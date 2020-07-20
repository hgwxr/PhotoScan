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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentModel

        if (chits != other.chits) return false
        if (cid != other.cid) return false
        if (collection != other.collection) return false
        if (create_uid != other.create_uid) return false
        if (ctime != other.ctime) return false
        if (fabulous != other.fabulous) return false
        if (id != other.id) return false
        if (isfollow != other.isfollow) return false
        if (level != other.level) return false
        if (name != other.name) return false
        if (open_window != other.open_window) return false
        if (picArr != other.picArr) return false
        if (picstr != other.picstr) return false
        if (playnumber != other.playnumber) return false
        if (reco != other.reco) return false
        if (rtime != other.rtime) return false
        if (status != other.status) return false
        if (text_info != other.text_info) return false
        if (userInfo != other.userInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chits.hashCode()
        result = 31 * result + cid.hashCode()
        result = 31 * result + collection.hashCode()
        result = 31 * result + create_uid.hashCode()
        result = 31 * result + ctime.hashCode()
        result = 31 * result + fabulous.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + isfollow.hashCode()
        result = 31 * result + level.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + open_window.hashCode()
        result = 31 * result + picArr.hashCode()
        result = 31 * result + picstr.hashCode()
        result = 31 * result + playnumber.hashCode()
        result = 31 * result + reco.hashCode()
        result = 31 * result + rtime.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + text_info.hashCode()
        result = 31 * result + (userInfo?.hashCode() ?: 0)
        return result
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
}