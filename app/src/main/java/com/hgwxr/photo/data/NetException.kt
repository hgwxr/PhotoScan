package com.hgwxr.photo.data

class NetException(val code: Int, val msg: String) : Exception(msg)
class FormatArrayException(val dataString:String,val code: Int, val msg: String) : Exception(msg)