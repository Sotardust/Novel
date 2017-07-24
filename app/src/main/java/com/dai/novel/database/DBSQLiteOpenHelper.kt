package com.dai.novel.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBSQLiteOpenHelper public constructor(context: Context) : SQLiteOpenHelper(context, DBSQLiteOpenHelper.dataPath, null, DBSQLiteOpenHelper.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

//    init {
//        val con = NovelApplication().applicationContext;
//    }

    fun closeDB() {
        if (instance != null) {
            try {
                val db = instance!!.writableDatabase
                db.close()
            } catch (e: Exception) {
            }

            instance = null
        }
    }


    companion object {
        private val DATABASE_VERSION = 1
        private var instance: DBSQLiteOpenHelper? = null
        private val dataPath = "data.db3"

        fun getInstance(context: Context): DBSQLiteOpenHelper {
            if (instance == null) {
                instance = DBSQLiteOpenHelper(context.applicationContext)
            }
            return instance as DBSQLiteOpenHelper
        }
    }
}
