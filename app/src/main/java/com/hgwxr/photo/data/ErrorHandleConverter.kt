package com.hgwxr.photo.data

import com.alibaba.fastjson.JSON
import com.hgwxr.photo.data.model.BaseResult
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class ErrorHandleConverter : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return ErrorHandlerResponseConverter<Any>(type)
    }

}

class ErrorHandlerResponseConverter<T>(val type: Type?) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        val string = value.string()
        val result = JSON.parseObject(string, BaseResult::class.java)
        if (result.success()) {
            return JSON.parseObject(JSON.toJSONString(result.data), type)
        } else {
            throw   NetException(result.code, result.msg)
        }
    }

}