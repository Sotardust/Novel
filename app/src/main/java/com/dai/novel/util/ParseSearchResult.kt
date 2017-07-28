package com.dai.novel.util

import com.dai.novel.dao.ResultList
import org.jsoup.Jsoup

/**
 * Created by dai on 2017/7/28.
 */
class ParseSearchResult {

    fun getParseResult(value: String): ArrayList<ResultList> {
        val resultList = ArrayList<ResultList>()
        val document = Jsoup.parse(value)
        val trElements = document.select("div")
        for (element in trElements) {
            if (element.className() == "result-list") {
                for (element1 in element.children()) {
                    var bookName: String? = null
                    var introduction: String? = null
                    var author: String? = null
                    var type: String? = null
                    var updateTime: String? = null
                    var chapter: String? = null
                    var number: String? = null
                    for (element2 in element1.children()) {
                        when (element2.className()) {
                            "result-game-item-pic" -> {
                                number = split(element2.html(), "cpos=\"img\" href=\"http://www.biqukan.com/", "/\"")
                                //TODO image = split(element2.html(),"<img src=\"","\" alt=")
                            }
                            else -> {
                                for (element3 in element2.children()) {
                                    when (element3.className()) {
                                        "result-game-item-desc" -> {
                                            introduction = element3.text()
                                        }
                                        "result-item-title result-game-item-title" -> {
                                            bookName = split(element3.html(), "title=\"", "\" class=\"result-game")
                                        }
                                        "result-game-item-info" -> {
                                            var count = 0;
                                            for (element4 in element3.children()) {
                                                count++;
                                                when (count) {
                                                    1 -> {
                                                        author = element4.text().split("：")[1]
                                                        println("author = ${author}")
                                                    }
                                                    2 -> type = element4.text().split("：")[1]
                                                    3 -> updateTime = element4.text().split("：")[1]
                                                    4 -> chapter = element4.text().split("：")[1]
                                                }
                                            }

                                            println("bookName = ${bookName}")
                                            println("introduction = ${introduction}")
                                            println("type = ${type}")
                                            println("updateTime = ${updateTime}")
                                            println("chapter = ${chapter}")
                                            println("number = ${number}")
                                            resultList.add(ResultList(bookName!!, introduction!!, author!!, type!!, updateTime!!, chapter!!, number!!))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultList
    }

    fun split(value: String, start: String, end: String): String = value.split(start)[1].split(end)[0]
}