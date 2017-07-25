package com.dai.novel.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by dai on 2017/7/24.
 */
open class BookList(context: Context) {

    private val keyBookName = "book_name"
    val bookName: SharedPreferences by lazy {
        context.getSharedPreferences(keyBookName, Context.MODE_PRIVATE)
    }

    fun setBookName(key: String, value: String) {
        bookName.edit().putString(key, value).apply();
    }

    fun getBookName(): MutableMap<String, *>? = bookName.all
}