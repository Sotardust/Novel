package com.dai.novel.booklist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dai.novel.BaseActivity
import com.dai.novel.R
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.ChapterListAdapter
import com.dai.novel.database.BookContent
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_list.*
import org.jetbrains.anko.toast

/**
 * Created by dai on 2017/7/26.
 */
class ChapterListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_list)
        val adapter = ChapterListAdapter()

        chapterListRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        chapterListRecycler.addItemDecoration(SpaceItemDecoration(2))
        val list = SQLiteOpenHelper(applicationContext).getAllBookContentData();
        val titleList = ArrayList<String>()
        val dataList = ArrayList<String>()
        list.mapTo(titleList) { it.getString(BookContent.TITLE) }
        for (index in titleList.size - 1..0) {
            dataList.add(titleList[index])
        }
        adapter.data = titleList
        chapterListRecycler.adapter = adapter
//        chapterListRecycler.scrollToPosition(adapter.itemCount)
        adapter.setOnItemClickLister(object : BaseRecyclerAdapter.OnItemClickLister<String> {
            override fun onItemClick(position: Int, data: String) {
                toast(data)
                val intent = Intent().setClass(applicationContext, ChapterActivity::class.java)
                intent.putExtra("title", data)
                startActivity(intent)
            }
        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }
        })
    }
}