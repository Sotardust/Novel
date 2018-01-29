package com.dai.novel.fragment.bookcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dai.novel.R
import com.dai.novel.fragment.BaseFragment

/**
 * Created by dai on 2018/1/29.
 */
class BookcaseFragment:BaseFragment() {
    fun newInstance(): BookcaseFragment {
        return BookcaseFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_bookcase, container, false)
        return view
    }
}