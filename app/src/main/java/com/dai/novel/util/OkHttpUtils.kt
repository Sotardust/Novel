package com.dai.novel.util

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by dai on 2017/7/24.
 */

open class OkHttpUtils {

    fun getSingleGetRequest(url: String): Single<String> {


        return Single.create(SingleOnSubscribe<String> { emitter ->
            val okHttpClient = OkHttpClient();
            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = okHttpClient.newCall(request).execute();
            val content = response.body().string();
            println("content = ${content}")
            emitter.onSuccess(content)

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
