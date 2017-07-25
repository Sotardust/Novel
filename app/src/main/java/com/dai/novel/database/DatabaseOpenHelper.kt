package com.dai.novel.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by dai on 2017/7/24.
 */
open class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "novel", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(BookList.NAME, true,
                BookList.ID to INTEGER + PRIMARY_KEY,
                BookList.BOOK_NAME to TEXT,
                BookList.TITLE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("onUpgrade")
        db.dropTable(BookList.NAME, true)
    }
}


// Access property for Context
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)

