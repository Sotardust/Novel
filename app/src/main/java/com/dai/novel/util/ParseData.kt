package com.dai.novel.util

import android.content.Context
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.preference.BookList
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Created by dai on 2017/7/25.
 */
class ParseData(val context: Context) {


    var string: String? = null;

    fun getWebData(url: String) {
        OkHttpUtils().getSingleGetRequest(url)
                .subscribe(object : SingleObserver<String> {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()

                        println("e = ${e.toString()}")
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onSuccess(value: String?) {
                        string = value
                        val title = parseTitle()
                        val bookName = parseBookName()
                        val bookList = BookList(context)
                        val list: MutableMap<String, String> = bookList.getBookName() as MutableMap<String, String>
                        val flag = list.keys.contains(bookName)
                        bookList.setBookName(bookName, title)

                        when (flag) {
                            true -> SQLiteOpenHelper(context).updateData(bookName, title)
                            else -> SQLiteOpenHelper(context).insertData(bookName, title)
                        }
                        println("parseTitle() = ${parseTitle()}")
                        println("parseBookName() = ${parseBookName()}")
                        println("parseContext() = ${parseContext()}")
                        println("parsePreviewChapter() = ${parsePreviewChapter()}")
                        println("parseIndexNumber() = ${parseIndexNumber()}")
                        println("parseNextChapter() = ${parseNextChapter()}")
                    }

                })
    }

    fun parseTitle(): String = getTitleName(0)

    fun getTitleName(index: Int): String = split("<title>", "</title>").split("_")[index]

    fun parseBookName(): String = getTitleName(1)

    fun parseContext(): String = split("<script>read2();</script>", "<script>read3();</script>")

    fun parsePreviewChapter(): String = split("var preview_page = \"", "\";")

    fun parseIndexNumber(): String = split("index_page = \"", "\";")

    fun parseNextChapter(): String = split("var next_page = \"", "\";")

    fun split(start: String, end: String): String = string?.split(start)!![1].split(end)[0]
}