package com.dai.novel.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dai.novel.R

/**
 * Created by dai on 2017/7/28.
 */
class SearchAdapter : BaseRecyclerAdapter<String>() {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, data: String) {
        if (viewHolder is ViewHolder) {
            viewHolder.name.text = data
            viewHolder.author.text = "${author!![position]} 著 |"
            viewHolder.chapter.text = "最新章节:${chapter!![position]}"
            viewHolder.introduction.text = "简介：${introduction!![position]}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    internal class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val name = itemView?.findViewById(R.id.item_name) as TextView
        val author = itemView?.findViewById(R.id.item_author) as TextView
        val chapter = itemView?.findViewById(R.id.item_chapter) as TextView
        val introduction = itemView?.findViewById(R.id.item_introduction) as TextView
    }

    var name: ArrayList<String>? = null
        set(value) {
            field = value
        }
    var author: ArrayList<String>? = null
        set(value) {
            field = value
        }
    var chapter: ArrayList<String>? = null
        set(value) {
            field = value
        }
    var introduction: ArrayList<String>? = null
        set(value) {
            field = value
        }

}
