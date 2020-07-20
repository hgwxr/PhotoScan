package com.hgwxr.photo.data.model

class TestModel() {
    var id: String = ""
    var name: String = ""
    var sourceid: String = ""
    var sourcetype: String = ""
}

class TestModelParent(
) {
    var id: String = ""
    var name: String = ""
    var sourceid: String = ""
    var sourcetype: String = ""
    var child: ArrayList<TestModel> = ArrayList()
}

class CheckMobileModel(
) {
    var mobile: String = ""
    var is_used: Int = 0

    fun used(): Boolean {
        return is_used == 1
    }
}


class ConfigModel(
) {
    //    {
//        "app_version": "1.0",
//        "conf_version": "1",
//        "apiurl": {
//        "base_api": "http://winowapi.guijieshuo.com",
//        "pic_winow_api": "http://winowpic1.guijieshuo.com",
//        "pic_external_api": "http://pic.kekelele.net",
//        "vod_winow_api": "http://winowpic1.guijieshuo.com",
//        "vod_external_api": "http://winowpic1.guijieshuo.com"
//    }
    lateinit var app_version: String
    lateinit var conf_version: String
    lateinit var apiurl: ConfigUrls
    override fun toString(): String {
        return "ConfigModel(app_version='$app_version', conf_version='$conf_version', apiurl=$apiurl)"
    }

    fun getHostUrl(): String {
        return apiurl.base_api
    }

    fun getImgHost(): String {
        return apiurl.pic_winow_api+"/"
    }

}

class ConfigUrls {
    //        {
//            "base_api": "http://winowapi.guijieshuo.com",
//            "pic_winow_api": "http://winowpic1.guijieshuo.com",
//            "pic_external_api": "http://pic.kekelele.net",
//            "vod_winow_api": "http://winowpic1.guijieshuo.com",
//            "vod_external_api": "http://winowpic1.guijieshuo.com"
//        }
    var base_api: String = ""
    var pic_winow_api: String = ""
    var pic_external_api: String = ""
    var vod_winow_api: String = ""
    var vod_external_api: String = ""
    override fun toString(): String {
        return "ConfigUrls(base_api='$base_api', pic_winow_api='$pic_winow_api', pic_external_api='$pic_external_api', vod_winow_api='$vod_winow_api', vod_external_api='$vod_external_api')"
    }

}

class UserInfo {
    val id: Long = 0
    val client_id: Int = 0
    val create_id: Int = 0
    val created: String = ""
    val update_id: Int = 0
    val updated: String = ""
    val mobile: String = ""
    val telphone: String = ""
    val username: String = ""
    val email: String = ""
    val truename: String = ""
    val sex: Int = 0
    val avatar: String = ""
    val birthday: String = ""
    val last_time: String = ""
    val last_ip: String = ""
    val role: Int = 0
    val state: Int = 0
    val is_batch: Int = 0
    val access_token: String = ""
    val vip_endtime: String = ""
    val comment: Int = 0
    val app_account: String = ""
    val source: Int = 0
    val vip_use_code: Int = 0  // 是否激活vip 0:否 1:是
}

class LoginInfo {
    val user_info: UserInfo = UserInfo()
    val access_token: String = ""
    val expire: String = ""
}

class BaseResult<T>(
) {
    var code: Int = 0
    var data: T? = null
    var msg: String = ""
    var status = -1
    override fun toString(): String {
        return "BaseResult(code=$code, data=$data, msg='$msg', status=$status)"
    }

    fun success(): Boolean {
        return code == 0
    }

}