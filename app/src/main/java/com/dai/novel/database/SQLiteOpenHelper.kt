package com.dai.novel.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import com.dai.novel.NovelApplication
import org.jetbrains.anko.db.RowParser
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.json.JSONObject
import java.io.File

/**
 * Created by dai on 2017/7/24.
 */
open class SQLiteOpenHelper(ctx: Context) {
    /**
     * `
     * table
     */
    val bookList = BookList.NAME//书单表
    val bookContent = BookContent.NAME//存储章节内容表
    var filePath = "${Environment.getExternalStorageDirectory()}/novel/"

    /**
     * 用户数据缓存
     **/
    val bookAllList = ArrayList<JSONObject>();

    val context = ctx

    fun isCreateMainFileSuccess(): Boolean {
        var isSuccess = false
        val file = File(filePath)
        if (!file.exists()) {
            isSuccess = file.mkdirs()
        } else {
            isSuccess = true
        }
        return isSuccess
    }

    fun isTableExist(tableName: String): Boolean {
        var isExist = false
        var cursor: Cursor? = null
        try {
            val db: SQLiteDatabase = DatabaseOpenHelper(NovelApplication().getInstance()).writableDatabase
//            db.execSQL(createSql)
            if (db.isOpen) {
                cursor = db.query(tableName, null, null, null, null, null, null)
                while (cursor!!.moveToNext()) {
                    isExist = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return isExist
    }

    fun queryChapterContent(title: String): String {
        print("queryChapterContent")
        var content: String? = null
        context.database.use {
            val row = arrayOf(BookContent.ID, BookContent.TITLE, BookContent.content)
            println("title = ${title}")
            val list = select(bookContent, BookContent.content).whereSimple("${BookContent.TITLE} =?", title).parseList(MyRowParser())
            for (li in list) {
                content = li
            }
//           val content = select(bookContent, BookContent.content).whereArgs("${BookContent.TITLE}=$title").parseList { Company(HashMap(it)) }
            println("content = ${content}")
        }
        return content!!
    }
//
//    interface RowParser<T> {
//        fun parseRow(columns: Array<Any>): T
//    }
//
//    class RowP : RowParser<String> {
//        override fun parseRow(columns: Array<Any>): String {
//            return columns[2] as String
//        }
//
//    }

    class Chapter(val id: Int, val title: String, val content: String)

    var rowParser = classParser<Chapter>()

    class MyRowParser : RowParser<String> {
        override fun parseRow(columns: Array<Any?>): String {
            for (item in columns) {
                println("item = $item")
            }
            return columns[0] as String
        }

    }

//    val parser = rowParser { id: Int, name: String, email: String ->
//        Triple(id, name, email)
//    }

    fun getAllBookListData(): ArrayList<JSONObject> {
        context.database.use {
            val cursor = query(BookList.NAME, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val json = JSONObject()
                json.put(BookList.BOOK_NAME, cursor.getString(1))
                json.put(BookList.TITLE, cursor.getString(2))
                bookAllList.add(json)
            }
            cursor.close()
        }
        return bookAllList
    }

    fun getAllBookContentData(): ArrayList<JSONObject> {
        context.database.use {
            val cursor = query(BookContent.NAME, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val json = JSONObject()
                json.put(BookContent.TITLE, cursor.getString(1))
                json.put(BookContent.content, cursor.getString(2))
                bookAllList.add(json)
            }
            cursor.close()
        }
        return bookAllList
    }

    fun insertBookListData(bookName: String, title: String) {
        context.database.use {
            val values = ContentValues()
            values.put(BookList.TITLE, title)
            values.put(BookList.BOOK_NAME, bookName)
            insert(bookList, null, values)
            println("insertBookListData")
        }
    }

    fun insertBookContentData(title: String, content: String) {
        context.database.use {
            val values = ContentValues()
            values.put(BookContent.TITLE, title)
            values.put(BookContent.content, content)
            insert(bookContent, null, values)
            println("insertBookContentData")
        }
    }

    fun updateBookListData(bookName: String, title: String) {
        context.database.use {
            println("updateBookNameTitle = ")
            println("bookName = ${bookName}")
            println("title = ${title}")
            update(bookList, BookList.TITLE to title).`whereSimple`(" ${BookList.BOOK_NAME} =?", bookName).exec()
        }
    }
}



