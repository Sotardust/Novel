package com.dai.novel.adapter.inter

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Created by dai on 2018/1/29.
 */
open class XSingleObserverAdapter<Any> : SingleObserver<Any> {
    override fun onError(e: Throwable) {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(t: Any) {

    }
}