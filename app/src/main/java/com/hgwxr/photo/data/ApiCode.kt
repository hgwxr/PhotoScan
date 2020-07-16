package com.hgwxr.photo.data


object ApiCode {
    /**
     * 配置信息
     */
    const val CONFIG_INFO = "system/apiinfo/baseinfo"

    /**
     * 登陆
     */
    const val LOGIN = "user/account/login"
    /**
     * 登陆V2
     */
    const val LOGIN_V2 = "user/account/loginv2"
    /**
     * 登陆
     */
    const val CHECK_MOBILE = "user/info/checkbindmobile"


    /**
     * mobile int
     */
    const val SEND_CODE = "user/info/code"

    /**
     * mobile
     * verifycode
     */
    const val CHECK_CODE = "user/info/verify"


    /**
     * 推荐
     */
    const val RECOMMEND = "usertask/contentapi/recommend"

    /**
     * 视频
     */
    const val VIDEO = "usertask/contentapi/Vod"

    /**
     * 发布
     */
    const val PUBLIC_INFO = "usertask/release/createpic"


    /**
     * 美图分类信息列表
     */
    const val IMAGE_INFO = "usertask/aisimtapi/club-category"

    /**
     * 美图分类标签
     */
    const val IMAGE_LABEL = "usertask/aisimtapi/tag-category"

    /**
     * 美图相册
     * page
     * category
     */
    const val IMAGE_ALBUM = "usertask/aisimtapi/pic-club-album"
   /**
     * 美图标签下相册列表
     * page
     * category
     */
    const val IMAGE_LABEL_ALBUM = "usertask/aisimtapi/pic-tag-album"

   /**
     * 美图相册详情
     * album_id
     */
    const val IMAGE_ALBUM_DETAIL = "usertask/aisimtapi/pic-album-detail"


}
//   val LOGIN_ :String="user/account/login"
