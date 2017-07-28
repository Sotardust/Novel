package com.dai.novel.activity.booklist

import android.os.Bundle
import android.text.Html
import com.dai.novel.activity.BaseActivity
import com.dai.novel.R
import com.dai.novel.database.SQLiteOpenHelper
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chapter.*
import org.jetbrains.anko.toast

/**
 * Created by dai on 2017/7/26.
 */

class ChapterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)
        val title = intent.getStringExtra("title")
        chapterTitle.text = title
        queryChapterContent(title)
    }

    fun queryChapterContent(title: String) {
        Single.create(SingleOnSubscribe<String> { emitter ->
            val value = SQLiteOpenHelper(applicationContext).queryChapterContent(title)
            emitter.onSuccess(value)

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<String> {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        toast(e.toString())

                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onSuccess(value: String?) {
                        println("value = " + value)
                        chapterContent.text = Html.fromHtml(value)
                    }
                })
    }

}