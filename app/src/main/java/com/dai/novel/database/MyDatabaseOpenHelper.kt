package com.dai.novel.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.dropTable

/**
 * Created by dai on 2017/7/24.
 */
open class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "novel.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
//        db.createTable("bookList", true,
//                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                "name" to TEXT
//        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("User", true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)

