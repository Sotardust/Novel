package com.dai.novel.util

import android.content.Context
import com.dai.novel.adapter.ObtainNovelAdapter
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.preference.BookList
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Created by dai on 2017/7/25.
 */
class ParseData(val context: Context) {


    var string: String? = null;

    fun getNetNovelData(url: String, key: String, adapter: ObtainNovelAdapter, dataList: ArrayList<String>, position: Int) {

        val bookList = BookList(context)
        val nextChapter: MutableMap<String, String> = bookList.getNextChapter() as MutableMap<String, String>
        val mURL: String = if (nextChapter.isNotEmpty() && nextChapter.containsKey(key)) nextChapter[key] as String else url
        if (!mURL.contains("html")) {
            dataList[position] = "${key}已更新到最新章节"
            adapter.data = dataList
            return
        }
        var bookName: String? = null
        OkHttpUtils().getSingleGetRequest(mURL)
                .subscribe(object : SingleObserver<String> {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        if (parseNextChapter().contains("html")) {
                            getNetNovelData(url = URLUtil().getUrl(nextChapter = parseNextChapter()), key = bookName!!, adapter = adapter, dataList = dataList, position = position)
                        }
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onSuccess(value: String?) {
                        string = value
                        val title = parseTitle()
                        val content = parseContext()
                        bookName = parseBookName()
                        val list: MutableMap<String, String> = bookList.getBookName() as MutableMap<String, String>
                        val flag = list.keys.contains(bookName!!)
                        bookList.setBookName(bookName!!, title)
                        bookList.setNextChapter(bookName!!, URLUtil().getUrl(parseNextChapter()))


                        dataList[position] = title
                        adapter.data = dataList
                        when (flag) {
                            true -> SQLiteOpenHelper(context).updateBookListData(bookName!!, title)
                            else -> SQLiteOpenHelper(context).insertBookListData(bookName!!, title)
                        }
                        SQLiteOpenHelper(context).insertBookContentData(bookName!!, title, content)

                        if (parseNextChapter().contains("html")) {
                            getNetNovelData(url = URLUtil().getUrl(nextChapter = parseNextChapter()), key = bookName!!, adapter = adapter, dataList = dataList, position = position)
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