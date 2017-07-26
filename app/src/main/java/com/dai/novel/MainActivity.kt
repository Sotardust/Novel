package com.dai.novel

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.BookListAdapter
import com.dai.novel.booklist.ChapterListActivity
import com.dai.novel.database.BookList
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.util.ParseData
import com.dai.novel.util.SpaceItemDecoration
import com.dai.novel.util.URLUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        val adapter = BookListAdapter()
        val nameList = ArrayList<String>()
        val titleList = ArrayList<String>()
        bookListRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        bookListRecycler.addItemDecoration(SpaceItemDecoration(2))
        val list = SQLiteOpenHelper(applicationContext).getAllBookListData();
        for (jsonObject in list) {
            nameList.add(jsonObject.getString(BookList.BOOK_NAME))
            titleList.add(jsonObject.getString(BookList.TITLE))
        }
        adapter.data = nameList
        adapter.title = titleList
        bookListRecycler.adapter = adapter
        adapter.setOnItemClickLister(object : BaseRecyclerAdapter.OnItemClickLister<String> {
            override fun onItemClick(position: Int, data: String) {
                toast(data)
                val intent = Intent().setClass(applicationContext, ChapterListActivity::class.java)
                startActivity(intent)
            }

        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }
        })


        textView.setOnClickListener { ParseData(applicationContext).getNetNovelData(URLUtil().getUrl("/1_1094/5386270.html")) }
        textView1.setOnClickListener { ParseData(applicationContext).getNetNovelData(URLUtil().getUrl("/1_1583/7778655.html")) }
    }

}
