package com.dai.novel.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dai.novel.R

/**
 * Created by dai on 2017/7/24.
 */
class ChapterListAdapter : BaseRecyclerAdapter<String>() {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, data: String) {

        if (viewHolder is ViewHolder) {
            viewHolder.title.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter_list, parent, false)
        return ViewHolder(view)
    }

    internal class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val title = itemView?.findViewById(R.id.item_title) as TextView
    }
}