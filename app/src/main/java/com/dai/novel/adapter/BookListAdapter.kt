package com.dai.novel.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dai.novel.R

/**
 * Created by dai on 2017/7/24.
 */
class BookListAdapter : BaseRecyclerAdapter<String>() {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, data: String) {
        if (viewHolder is ViewHolder) {
            viewHolder.tv.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent)
    }

    internal class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView?.findViewById(R.id.item_name) as TextView
    }
}