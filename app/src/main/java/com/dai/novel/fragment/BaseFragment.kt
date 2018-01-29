package com.dai.novel.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by dai on 2018/1/29.
 */
open class BaseFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private var containerViewId: Int = 0//记录替换ID

//    // 切換Fragment
//    fun changeFragment(f: Fragment) {
//        changeFragment(f, containerViewId, false, null)
//    }
//
//    //带参数的。
//    fun changeFragment(f: Fragment, mBundle: Bundle) {
//        changeFragment(f, containerViewId, false, mBundle)
//    }
//
//    // 初始化Fragment
//    fun initFragment(f: Fragment, @IdRes containerViewId1: Int) {
//        containerViewId = containerViewId1
//        changeFragment(f, containerViewId, true, null)
//    }

//    private fun changeFragment(f: Fragment, @IdRes containerViewId: Int, init: Boolean, mBundle: Bundle?) {
//        var fragmentManager: FragmentManager? = null
//        if (fragmentManager == null) {
//            fragmentManager = getSupportFragmentManager()
//        }
//        val ft = fragmentManager?.beginTransaction()
//        ft?.replace(containerViewId, f)
//        if (mBundle != null) {
//            f.arguments = mBundle
//        }
//        ft?.commit()
//    }
}