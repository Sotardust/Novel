package com.dai.novel.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by dai on 2017/5/27.
 */

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<T>? = null
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    private var onItemClickLister: OnItemClickLister<T>? = null
    private var onItemLongClickLister: OnItemLongClickLister<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val value = this.data!![position]
        onBindViewHolder(holder, position, value)
        if (onItemClickLister != null) {
            holder.itemView.setOnClickListener { onItemClickLister!!.onItemClick(position, value) }
            holder.itemView.setOnLongClickListener {
                onItemLongClickLister!!.onItemLongClick(position, value)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return this.data!!.size
    }

    fun setOnItemClickLister(onItemClickLister: OnItemClickLister<T>) {
        this.onItemClickLister = onItemClickLister
    }

    fun setOnItemLongClickLister(onItemLongClickLister: OnItemLongClickLister<T>) {
        this.onItemLongClickLister = onItemLongClickLister
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    abstract fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, data: T)

    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    interface OnItemClickLister<T> {
        fun onItemClick(position: Int, data: T)
    }

    interface OnItemLongClickLister<T> {
        fun onItemLongClick(position: Int, data: T)
    }
}
