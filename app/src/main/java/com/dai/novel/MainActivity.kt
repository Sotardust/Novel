package com.dai.novel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.BookListAdapter
import com.dai.novel.database.BookList
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.util.ParseData
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
        bookListRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        val list = SQLiteOpenHelper(applicationContext).getAllData(BookList.NAME);
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
            }

        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }
        })


        textView.setOnClickListener { ParseData(applicationContext).getWebData(URLUtil().getUrl()) }
    }

}
