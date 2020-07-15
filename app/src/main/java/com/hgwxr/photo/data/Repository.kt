package com.hgwxr.photo.data

import com.alibaba.fastjson.JSON
import com.hgwxr.photo.widgets.LoadingDialog
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit


const val url = "http://winowapi.guijieshuo.com/"

object Repository {

    private val sServiceMap: MutableMap<String, Service> = HashMap()

    private fun createHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging).build()
    }

    private inline fun <reified T> createService(url: String): T {
        val httpClient = createHttpClient()
        val builder = Retrofit.Builder().baseUrl(url).client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(FastJsonConverterFactory.create())
        val build = builder.build()
        return build.create(T::class.java)
    }


    fun getRetrofit(url: String): Service {
        var retrofit = sServiceMap[url]
        if (retrofit == null) {
            retrofit = createService<Service>(url)
            sServiceMap[url] = retrofit
        }
        return retrofit
    }

    suspend inline fun <reified T> test(): T {
        val retrofit = getRetrofit(url)
        val method = retrofit.getMethod(
            path = "usertask/aisimtapi/club-category",
            param = mutableMapOf()
        )
        try {
            val response = method.await()
            return JSON.parseObject(response, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return JSON.parseObject("{user:'ssd'}", T::class.java)
    }

    suspend inline fun <reified T> testList(): T {
        val retrofit = getRetrofit(url)
        val method = retrofit.getMethod(
            path = "usertask/aisimtapi/club-category",
            param = mutableMapOf()
        )
        try {
            val response = method.await()
            return JSON.parseObject(response, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return JSON.parseObject("{user:'ssd'}", T::class.java)
    }

    suspend inline fun <reified T : Any> testListResult(): Result<T> {
        return getRequest("usertask/aisimtapi/club-category", mutableMapOf())
    }

    suspend inline fun <reified T : Any> postRequest(
        method: String,
        params: MutableMap<String, Any>
    ) {
         withContext(Dispatchers.IO) {
            val retrofit = getRetrofit(url)
            val response = retrofit.postMethod(
                path = method,
                param = params
            )
            try {
                val await = response.await()
                if (await.isSuccessful) {
                    val body = await.body()
                    body?.let {
                        val t = JSON.parseObject(it, T::class.java)
                        t?.let {
                            return@withContext t
                        }
                    }

                }else{
                }
                return@withContext Result.Error(IllegalArgumentException("empty"))
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            } finally {

            }
        }
    }

    suspend inline fun <reified T : Any> getRequest(
        method: String,
        params: MutableMap<String, Any>
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            val retrofit = getRetrofit(url)
            val method = retrofit.getMethod(
                path = method,
                param = params
            )
            try {
                val response = method.await()
                val parseObject = JSON.parseObject(response, T::class.java)
                parseObject?.let {
                    return@withContext Result.Success(it)
                }
                return@withContext Result.Error(IllegalArgumentException("empty"))
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            } finally {
            }
        }
    }
}

interface Service {
    @GET("{path}")
    fun getMethod(
        @Path("path") path: String?,
        @QueryMap param: MutableMap<String, Any>
    ): Deferred<String>

    @FormUrlEncoded
    @POST("{path}")
    fun postMethod(
        @Path("path") path: String?,
        @FieldMap param: MutableMap<String, Any>
    ): Deferred<Response<String>>
}