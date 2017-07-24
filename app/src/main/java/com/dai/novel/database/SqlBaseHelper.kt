package com.dai.novel.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dai.novel.NovelApplication
import org.json.JSONObject


/**
 * Created by dai on 2017/7/24.
 */
class SqlBaseHelper {
    private val columnName = "data"
    private fun getWritableDatabase(): SQLiteDatabase {
        val db = DBSQLiteOpenHelper.getInstance(NovelApplication().applicationContext).writableDatabase
        return db
    }

    fun isTableExist(tableName: String): Boolean {
        var isExist = false
        val createSql = getCreateSQL(tableName)
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        try {
            db = getWritableDatabase()
            db.execSQL(createSql)
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

    //    fun getSqlData(tableName: String): JSONObject {
    fun getSqlData(tableName: String) {
        var tempObject: JSONObject? = null
        val createSql = getCreateSQL(tableName)
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        try {
            db = getWritableDatabase()
            db.execSQL(createSql)
            if (db.isOpen) {
                cursor = db.query(tableName, null, null, null, null, null, null)
                while (cursor!!.moveToNext()) {
                    val sqlData = cursor.getBlob(cursor.getColumnIndex(columnName))
                    for (data in sqlData) {
                        println("********")
                        println("data = [${data}]")
                    }
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

        var db: SQLiteDatabase? = null
        try {
            db = getWritableDatabase()
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

    //根据标签获得数据库创建SQL
    private fun getCreateSQL(tableName: String): String = "create table IF NOT EXISTS  $tableName($columnName BLOB)"

}