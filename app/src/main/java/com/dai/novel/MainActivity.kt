package com.dai.novel

import android.os.Bundle
import android.text.Html
import com.dai.novel.database.DataBase
import com.dai.novel.util.OkHttpUtils
import com.dai.novel.util.URLUtil
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataBase().setBookListAttribute("一年永恒")
        DataBase().setBookListAttribute("一念")
        DataBase().saveBookAllListData(applicationContext)
        textView.setOnClickListener { getWebData(URLUtil().getUrl()) }
    }

    fun getWebData(url: String) {
        println("url = [${url}]")
        OkHttpUtils().getSingleGetRequest(url)
                .subscribe(object : SingleObserver<String> {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()

                        println("e = ${e.toString()}")
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onSuccess(value: String?) {
                        println("value = ${value}")

                        val novelContent = value?.split("<script>read2();</script>");
                        if (novelContent != null) {
                            for (va in novelContent) {
                                println("**************")
                                println("va = ${va}")
                            }
                        }
                        println("novelContent = [${novelContent}]")
                        val novelContent1 = novelContent?.get(1)?.split("<script>read3();</script>")?.get(0);
                        println("novelContent1 = [${novelContent1}]")
                        content.text = Html.fromHtml(novelContent1)
                    }

                })
    }
}
