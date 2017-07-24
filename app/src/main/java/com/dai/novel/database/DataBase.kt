package com.dai.novel.database

import android.content.Context
import android.os.Environment
import org.json.JSONObject
import java.io.File


/**
 * Created by dai on 2017/7/24.
 */
open class DataBase() {
    /**
     * `
     * table
     */
    val bookList = SqliteHelper.BookList.NAME//书单
    var filePath = "${Environment.getExternalStorageDirectory()}/novel/"

    /**
     * 用户数据缓存
     **/
    val bookAllList = ArrayList<JSONObject>();
    val bookNameKey = "bookName"
    val bookTimeKey = "bookTime"

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


    fun saveBookAllListData(context: Context) {
//        SqliteHelper().setSqliteHelper(context)
        SqliteHelper().saveSqlData(bookAllList, bookList)
    }

    /***获取用户数据 */
    fun getAllUsers(): ArrayList<JSONObject> {
        if (bookAllList.size < 1) {
            println("getAllUsers")
            val usersObject = SqlBaseHelper().getSqlData(bookList)
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
        }
        return bookAllList
    }

    /**
     * 存储用户某个信息
     */
    @Synchronized fun setBookListAttribute(book: String) {
        var saved = false
//        for (ob in bookAllList) {
//            item = ob as JSONObject
//            if (item.getString(bookNameKey) == userKeys) {
//                item.put(vkeys, value)
//                saved = true
//                break
//            }
//        }
//        if (!saved) {
//            item = JSONObject()
//            item.put(bookNameKey, userKeys)
//            item.put(vkeys, value)
//            bookAllList.add(item)
//        }
        val item = JSONObject()
        item.put(SqliteHelper.BookList.TITLE, book)
//        item.put(vkeys, value)
        bookAllList.add(item)
        println("bookAllList = ${bookAllList}")

    }
}