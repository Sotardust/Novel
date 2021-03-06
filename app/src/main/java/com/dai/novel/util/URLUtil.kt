package com.dai.novel.util

/**
 * Created by dai on 2017/7/24.
 */

open class URLUtil {

    //    val  address = "39.106.220.113:8080";
    val address = "192.168.17.190:8080";

    // url = http://www.biqukan.com/1_1583/7778655.html 圣墟
    // url = http://www.biqukan.com/1_1094/5386270.html 一念永恒
    fun getUrl(nextChapter: String): String = "http://www.biqukan.com" + nextChapter;

    //    fun getUrl(): String = "http://www.biqukan.com/1_1094/15283982.html";
    fun searchUrl(name: String): String = "http://zhannei.baidu.com/cse/search?q=$name&click=1&s=2758772450457967865&nsid="

    // 账号注册地址
    fun registerUrl(): String = "http://$address/mobile/register";

    //账号登录地址
    fun loginUrl(): String = "http://$address/mobile/login";

    fun getAccountUrl(): String = "http://$address/mobile/get_accounts";

    fun testUrl(): String = "http://$address/mobile/list";

}
