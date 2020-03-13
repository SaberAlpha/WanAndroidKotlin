package com.brezze.library_common.http

import ResponseThrowable
import android.util.Log
import com.brezze.library_common.utils.ToastUtils
import io.reactivex.observers.DisposableObserver
import java.lang.reflect.ParameterizedType

/**
 * File: BaseApiObserver
 * author: zhangjiabiao Create on 2020/3/12 17:45
 * Change (from 2020/3/12)
 *--------------------------------
 *decription:
 *----------------------------
 *
 */
open class BaseApiObserver<T>(var onResult:(T?)->Unit,
                         var onServerError:(ResponseThrowable)->Unit = {})
    : DisposableObserver<Result<T>>() {

    override fun onComplete() {

    }

    override fun onNext(t: Result<T>) = when(t.errorCode){
        0->{
            onResult(t.data)
        }
        else->{
            toast(t.errorMsg)
            onServerError(ResponseThrowable(t.errorCode,t.errorMsg))
        }
    }


    override fun onError(e: Throwable) {
        val handleException = ExceptionHandle.handleException(e)
        toast(handleException.errMsg)
        onServerError(handleException)
    }

    /**
     * toast
     *
     * @param msg
     */
    private fun toast(msg: String) {
        ToastUtils.showShortSafe(msg)
    }

    private fun toast(msg: Int) {
        ToastUtils.showShortSafe(msg)
    }


}