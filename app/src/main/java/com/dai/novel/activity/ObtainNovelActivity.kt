package com.dai.novel.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dai.novel.R
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.ObtainNovelAdapter
import com.dai.novel.adapter.SearchAdapter
import com.dai.novel.util.*
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.actvity_obtain_novel.*

/**
 * Created by dai on 2017/7/27.
 */
class ObtainNovelActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_obtain_novel)

        val adapter = ObtainNovelAdapter()
        val dataList = ArrayList<String>()
        val nextChapterList = ArrayList<String>()
        val nameList = ArrayList<String>()
        obtainRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        obtainRecycler.addItemDecoration(SpaceItemDecoration(2))
        searchRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        searchRecycler.addItemDecoration(SpaceItemDecoration(2))
//        nameList.mapTo(dataList) { it -> "获取${it}最新章节数据" }
        nameList.add("一念永恒")
        nameList.add("圣墟")
        dataList.add("获取一念永恒最新章节数据")
        dataList.add("获取圣墟最新章节数据")
        nextChapterList.add("/1_1094/5386270.html")
        nextChapterList.add("/1_1583/7778655.html")


        println("obtainRecycler = ${obtainRecycler}")
        adapter.data = dataList
        obtainRecycler.adapter = adapter
        adapter.setOnItemClickLister(object : BaseRecyclerAdapter.OnItemClickLister<String> {
            override fun onItemClick(position: Int, data: String) {
                ParseData(applicationContext).getNetNovelData(URLUtil().getUrl(nextChapterList[position]), nameList[position], adapter, dataList, position)
            }

        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }

        })

        search.setOnClickListener {
            println("++++++++")
            searchNovel(editText.text.toString())
        }
    }

    fun searchNovel(name: String) {

        OkHttpUtils().getSingleGetRequest(URLUtil().searchUrl(name))
                .subscribe(object : SingleObserver<String> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onSuccess(value: String) {
                        val adapter = SearchAdapter()
                        val nameList = ArrayList<String>()
                        val authorList = ArrayList<String>()
                        val chapterList = ArrayList<String>()
                        val introductionList = ArrayList<String>()
                        val resultList = ParseSearchResult().getParseResult(value)
                        for ((bookName, introduction, author, _, _, chapter, _) in resultList) {
                            nameList.add(bookName)
                            authorList.add(author)
                            introductionList.add(introduction)
                            chapterList.add(chapter)
                        }
                        adapter.data = nameList
                        adapter.author = authorList
                        adapter.chapter = chapterList
                        adapter.introduction = introductionList
                        searchRecycler.adapter = adapter
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })

    }
}