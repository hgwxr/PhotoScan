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