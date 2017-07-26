package com.dai.novel.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by dai on 2017/7/26.
 */
class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        super.getItemOffsets(outRect, view, parent, state)
        println("space = ${space}")
        outRect?.top = space
        outRect?.bottom = space
        outRect?.left = space;
        outRect?.right = space;
    }
}