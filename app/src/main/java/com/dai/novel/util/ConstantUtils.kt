package com.dai.novel.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by dai on 2018/1/24.
 */
class ConstantUtils {
    //判断网络是否连接
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        if (info != null && info.isConnected) {
            // 当前网络是连接的
            if (info.state === NetworkInfo.State.CONNECTED) {
                // 当前所连接的网络可用
                return true
            }
        }
        return false
    }
}