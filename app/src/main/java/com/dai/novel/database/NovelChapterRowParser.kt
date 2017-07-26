package com.dai.novel.database

import org.jetbrains.anko.db.RowParser

/**
 * Created by dai on 2017/7/26.
 */

class NovelChapterRowParser : RowParser<String> {
    override fun parseRow(columns: Array<Any?>): String {
        for (item in columns) {
        }
        return columns[0] as String
    }
}