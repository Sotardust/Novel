package com.dai.novel.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import com.dai.novel.NovelApplication
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
    val bookList = BookList.NAME//书单
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


    fun insertBookNameTitle(bookName: String, title: String) {
        setBookListAttribute(title, bookName)
//        insertData(bookAllList, bookList)
    }

//    fun updateBookNameTitle(bookName: String, title: String) {
//
//    }

    /***获取用户数据 */
    fun getAllUsers() {
//        if (bookAllList.size < 1) {
        println("getAllUsers")
        val usersObject = getAllData(bookList)
//            if (usersObject != null && usersObject!!.containsField("list")) {
//            if (usersObject != null && usersObject!!.containsField("list")) {
//                val dataList = usersObject.("list") as JSONArray
//                val tempList = JSONArray()
//                for (ob in dataList) {
//                    val item = ob as JSONArray
//                    if (TextUtils.isEmpty(item.getString(Constants.userNameKey)) || TextUtils.isEmpty(Constants.userPassWordKey)) {
//                        tempList.add(item)
//                    }
//                }
//                if (tempList.size() > 0) {
//                    dataList.removeAll(tempList)
//                }
//                bookAllList.p(dataList)
//                if (tempList.size() > 0) {
//                    saveAllUsersData()
//                }
//            }
//        }
//        return bookAllList
    }

    /**
     * 存储用户某个信息
     */
    @Synchronized fun setBookListAttribute(title: String, bookName: String) {
        val item = JSONObject()
        item.put(BookList.TITLE, title)
        item.put(BookList.BOOK_NAME, bookName)
        bookAllList.add(item)
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

    fun getAllData(tableName: String): ArrayList<JSONObject> {
        context.database.use {
            val cursor = query(tableName, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val index = cursor.position
                val f1 = cursor.getInt(0)
                val f2 = cursor.getString(1)
                val f6 = cursor.getString(2)
                val f3 = cursor.getColumnName(0)
                val f4 = cursor.getColumnName(1)
                val f5 = cursor.getColumnName(2)
                val json = JSONObject()
                json.put(BookList.BOOK_NAME, cursor.getString(1))
                json.put(BookList.TITLE, cursor.getString(2))
                bookAllList.add(json)
            }
            cursor.close()
        }
        return bookAllList
    }

    fun insertData(bookName: String, title: String) {
        context.database.use {
            val values = ContentValues()
            values.put(BookList.TITLE, title)
            values.put(BookList.BOOK_NAME, bookName)
            insert(bookList, null, values)
            println("insertData")
            getAllUsers()
        }
    }

    fun updateData(bookName: String, title: String) {
        context.database.use {
            println("updateBookNameTitle = ")
            println("bookName = ${bookName}")
            println("title = ${title}")
            update(bookList, BookList.TITLE to title).`whereSimple`(" ${BookList.BOOK_NAME} =?", bookName).exec()
            getAllUsers()
        }
    }
}



