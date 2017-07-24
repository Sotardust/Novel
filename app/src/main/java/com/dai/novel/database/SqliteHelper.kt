package com.dai.novel.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dai.novel.NovelApplication
import org.jetbrains.anko.db.*
import org.json.JSONObject

/**
 * Created by dai on 2017/7/24.
 */
open class SqliteHelper() : MyDatabaseOpenHelper(NovelApplication().applicationContext) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db?.dropTable(BookList.NAME, true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(BookList.NAME, true,
                BookList.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                BookList.TITLE to TEXT)
    }

//    var context: Context? = null

    companion object : SqliteHelper() {
        val DB_NAME = "novel.db"
        val DB_VERSION = 1
        //        val CONTEXT = context;
        val instance: SqliteHelper by lazy { SqliteHelper() }
    }

    // 书单表
    object BookList {
        val NAME = "bookList"
        val ID = "_id"
        val TITLE = "title"
    }

    //书内容表
    object BookContent {

    }

    fun isTableExist(tableName: String): Boolean {
        var isExist = false
        var cursor: Cursor? = null
        try {
            val db: SQLiteDatabase = writableDatabase
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

    fun getSqlData(tableName: String) {
        var tempObject: JSONObject? = null
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        try {
            db = writableDatabase
            if (db.isOpen) {
                cursor = db.query(tableName, null, null, null, null, null, null)
                while (cursor!!.moveToNext()) {
                    val fdsa = cursor.getColumnIndex(BookList.TITLE)
                    println("fa = ${fdsa}")
//                    val sqlData = cursor.getBlob(cursor.getColumnIndex(columnName))
//                    for (data in sqlData) {
//                        println("********")
//                        println("data = [${data}]")
//                    }
//                    tempObject = BSON.decode(sqlData) as JSONObject
//                    tempObject = JSONObject().
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null)
                cursor.close()

        }
//        return tempObject
    }

    fun saveSqlData(tempObject: ArrayList<JSONObject>, tableName: String) {
        try {
            val db = writableDatabase
            if (db.isOpen) {
                val values = ContentValues()
                for (jsonObject in tempObject) {
                    values.put(SqliteHelper.BookList.TITLE, jsonObject.getString(SqliteHelper.BookList.TITLE))
                }
                if (isTableExist(tableName)) {
//                    db.update(tableName, values, null, null)
//                } else {
                    db.insert(tableName, null, values)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

        }
    }
}



