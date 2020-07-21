package com.hgwxr.photo.data

import android.util.Log
import com.alibaba.fastjson.JSON
import com.hgwxr.photo.data.model.ConfigModel
import com.hgwxr.photo.utils.TypeLiteral
import com.hgwxr.photo.utils.typeLiteral
import com.hgwxr.photo.widgets.LoadingDialog
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.lang.Exception
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


const val url = "http://winowapi.guijieshuo.com/"

object Repository {

    private val sServiceMap: MutableMap<String, Service> = HashMap()

    private fun createHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            ("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private inline fun <reified T> createService(url: String): T {
        val httpClient = createHttpClient()
        val builder = Retrofit.Builder().baseUrl(url).client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ErrorHandleConverter())
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


    private suspend fun getRemoteConfigModel(): ConfigModel? {
        try {
            val remoteConfigModel =
                getRequest<ConfigModel>(method = ApiCode.CONFIG_INFO, params = mutableMapOf())
            Log.e("getConfigModel", "request：${remoteConfigModel.toString()}")
            return remoteConfigModel
        } catch (e: Exception) {
            Log.e("E:", "request：${e.message}")
            e.printStackTrace()
        }
        return null
    }

    suspend fun getConfigModel(): ConfigModel? {
        Log.e("getConfigModel", "before")
        if (LocalRepository.configModel == null) {
            LocalRepository.configModel =
                getRemoteConfigModel() ?: return LocalRepository.getLocalConfigModel()
        }
        return LocalRepository.configModel
    }

    lateinit var configInfo: ConfigModel

    suspend fun getConfigInfoModel(): ConfigModel? {
        val configModel = getConfigModel()
        if (configModel != null) {
            configInfo = configModel
        }
        return configModel
    }

    suspend inline fun <reified T> getMethod(
        method: String,
        params: MutableMap<String, Any>
    ): T? {
        val configInfoModel = getConfigInfoModel()
        return configInfoModel?.let {
            return@let getRequest<T>(baseUrl = it.getHostUrl(), method = method, params = params)
        }
    }

    suspend inline fun <reified T> postMethodLoading(
        method: String,
        params: MutableMap<String, Any>,
        loading: Boolean = true
    ): T? {
        try {
            if (loading) {
                Log.e("postMethodLoading", "show" + Thread.currentThread().name)
                LoadingDialog.startLoading()
            }
            return postMethod(method, params)
        } finally {
            if (loading) {
                Log.e("postMethodLoading", "hide" + Thread.currentThread().name)
                LoadingDialog.hideLoadingDialog()
            }
        }
    }

    suspend inline fun <reified T> postMethod(
        method: String,
        params: MutableMap<String, Any>
    ): T? {
        val configInfoModel = getConfigInfoModel()
        return configInfoModel?.let {
            return@let postRequest<T>(baseUrl = it.getHostUrl(), method = method, params = params)
        }
    }

    suspend inline fun <reified T> getRequest(
        baseUrl: String = url,
        method: String,
        params: MutableMap<String, Any>
    ): T {
        val retrofit = getRetrofit(baseUrl)
        val response = retrofit.getMethod(
            path = method,
            param = params
        )
        val await = response.await()
        val type = typeLiteral<T>().type
//        val type = TypeLiteral<T>().type
        return JSON.parseObject(await, type);
    }

    suspend inline fun <reified T> postRequest(
        baseUrl: String = url,
        method: String,
        params: MutableMap<String, Any>
    ): T {
        val retrofit = getRetrofit(baseUrl)
        val response = retrofit.postMethod(
            path = method,
            param = params
        )
        val await = response.await()
        val type = typeLiteral<T>().type
//        val type = TypeLiteral<T>().type
        return JSON.parseObject(await, type);
    }
}

interface Service {
    @GET("{path}")
    fun getMethod(
        @Path("path") path: String?,
        @QueryMap param: MutableMap<String, Any>
    ): Deferred<String>

    @GET("{path}")
    fun <T> getMethodV1(
        @Path("path") path: String?,
        @QueryMap param: MutableMap<String, Any>
    ): Deferred<T>

    @FormUrlEncoded
    @POST("{path}")
    fun postMethod(
        @Path("path") path: String?,
        @FieldMap param: MutableMap<String, Any>
    ): Deferred<String>
}