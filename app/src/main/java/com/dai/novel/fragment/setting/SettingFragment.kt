package com.dai.novel.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dai.novel.R
import com.dai.novel.fragment.BaseFragment

/**
 * Created by dai on 2018/1/29.
 */
class SettingFragment : BaseFragment() {

    fun newInstance(): SettingFragment {
        return SettingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_setting, container, false)
        return view
    }
}


