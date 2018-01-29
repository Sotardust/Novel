package com.dai.novel.fragment.bookcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dai.novel.R
import com.dai.novel.adapter.inter.XSingleObserverAdapter
import com.dai.novel.fragment.BaseFragment
import com.dai.novel.util.OkHttpUtils
import com.dai.novel.util.URLUtil
import kotlinx.android.synthetic.main.fragment_bookcase.*

/**
 * Created by dai on 2018/1/29.
 */
class BookcaseFragment : BaseFragment() {

    fun newInstance(): BookcaseFragment {
        return BookcaseFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_bookcase, container, false)
//        btn.setOnClickListener {
        getResult()
//        }
        return view
    }

    fun getResult() {
        OkHttpUtils().getSingleGetRequest(URLUtil().getAccountUrl())
                .subscribe(object : XSingleObserverAdapter<String>() {
                    override fun onSuccess(t: String) {
                        super.onSuccess(t)
                        btn1.text = t
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        e.printStackTrace()

                    }
                })
    }
}