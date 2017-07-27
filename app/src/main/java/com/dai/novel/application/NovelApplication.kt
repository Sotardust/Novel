package com.dai.novel.application

import android.app.Application
import com.dai.novel.database.SQLiteOpenHelper

/**
 * Created by dai on 2017/7/24.
 */
class NovelApplication : Application() {

    private var instance: NovelApplication? = null

    @Synchronized
    fun getInstance(): NovelApplication {
        if (instance == null) {
            instance = NovelApplication()
        }
        return instance!!
    }

    override fun onCreate() {
        super.onCreate()
        SQLiteOpenHelper(applicationContext).isCreateMainFileSuccess()
//        DatabaseOpenHelper(applicationContext)
    }

}