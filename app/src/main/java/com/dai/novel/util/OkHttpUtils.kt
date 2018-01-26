package com.dai.novel.util

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit


/**
 * Created by dai on 2017/7/24.
 */

open class OkHttpUtils {


    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient? {
        return okHttpClient
    }

    fun setOkHttpClient() {
        okHttpClient = obtainOkHttpClient()
    }


    private fun obtainOkHttpClient(): OkHttpClient {
        val mBuilder = OkHttpClient.Builder().cookieJar(object : CookieJar {
            private val cookieStore = HashMap<String, List<Cookie>>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore.put(url.host(), cookies)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host()]
                return cookies ?: ArrayList<Cookie>()
            }
        })
//        if (CSMApplication.getSslSocketFactory() != null) {
//            mBuilder.sslSocketFactory(CSMApplication.getSslSocketFactory(), CSMApplication.getTrustManager())
//        }

        return mBuilder.hostnameVerifier { hostname, _ ->
            println("hostname = " + hostname)
            true
        }.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    open fun getSingleGetRequest(url: String): Single<String> {

        return Single.create(SingleOnSubscribe<String> { emitter ->
            val okHttpClient = OkHttpClient();
            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = okHttpClient.newCall(request).execute();
            val content = response.body()?.string();
//            println("content = ${content}")
            emitter.onSuccess(content)

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    open fun getSinglePostRequest(url: String, jsonObject: JSONObject): Single<String> {
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
            val response = OkHttpClient().newCall(request).execute()
            e.onSuccess(response.body()!!.string())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
