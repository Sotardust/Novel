package com.dai.novel.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dai.novel.R
import com.dai.novel.activity.booklist.ChapterListActivity
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.BookListAdapter
import com.dai.novel.database.BookList
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                println("data = ${data}")
                toast(data)
                val intent = Intent().setClass(applicationContext, ChapterListActivity::class.java)
                intent.putExtra("tableName", data)
                startActivity(intent)
            }

        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }
        })

        addOrUpdate.setOnClickListener {
            val intent = Intent().setClass(this, ObtainNovelActivity::class.java)
            intent.putStringArrayListExtra("name",nameList)
            intent.putStringArrayListExtra("title",titleList)
            startActivity(intent)
        }
    }

}
