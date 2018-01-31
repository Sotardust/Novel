package com.dai.novel.util

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by dai on 2017/7/24.
 */

open class OkHttpUtils {

    companion object {
        var okHttpClient: OkHttpClient? = null
    }

    fun setHttpClient() {
        okHttpClient = obtainOkHttpClient()
    }

    fun getHttpClient(): OkHttpClient? {
        return okHttpClient
    }

    fun obtainOkHttpClient(): OkHttpClient {
        val mBuilder = OkHttpClient.Builder()
                .cookieJar(object : CookieJar {
                    private val cookieStore = HashMap<String, List<Cookie>>()

                    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        println("url = [${url}], cookies = [${cookies}]")
                        cookieStore.put(url.host(), cookies)
                    }

                    override fun loadForRequest(url: HttpUrl): List<Cookie> {
                        val cookies = cookieStore[url.host()]
                        println("url = [${url}]")
                        println("cookies != null = ${cookies != null}")
                        println("cookies != null = ${cookies?.size}")
                        if (cookies != null) {
                            for (cookie in cookies) {
                                println("cookie = ${cookie.domain()}")
                                println("cookie = ${cookie.name()}")
                                println("cookie = ${cookie.path()}")
                                println("cookie = ${cookie.value()}")
                            }
                        }
                        return cookies ?: ArrayList<Cookie>()
                    }
                })

        println("obtainOkHttpClient ")
        return mBuilder.hostnameVerifier { hostname, _ ->
            println("hostname = " + hostname)
            true
        }.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    open fun getSingleGetRequest(url: String): Single<String> {

        println("getSingleGetRequest getOkHttpClient = ${getHttpClient()}")
        return Single.create(SingleOnSubscribe<String> { emitter ->
            //            val okHttpClient = OkHttpClient();
            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = getHttpClient()?.newCall(request)?.execute()
            println("getSingleGetRequest response?.headers() = ${response?.headers().toString()}")
            println("getSingleGetRequest response?.headers() = ${request.headers().toMultimap()}")
            val content = response?.body()?.string()
//            println("content = ${content}")
            if (content != null) {
                emitter.onSuccess(content)
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    open fun getSinglePostRequest(url: String, jsonObject: JSONObject): Single<String> {
        println("getSinglePostRequest getOkHttpClient = ${getHttpClient()}")
        return Single.create(SingleOnSubscribe<String> { e ->
            val formBody = FormBody.Builder()
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                formBody.add(key, jsonObject.getString(key))
            }
            val requestBody = formBody.build()
            val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
            val response = getHttpClient()?.newCall(request)?.execute()
            if (url == URLUtil().loginUrl()) {
                println("request = ${request.headers()}")
            }

            e.onSuccess(response?.body()!!.string())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestBuilder(): Request.Builder {
        return Request.Builder()
    }
}
