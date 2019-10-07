package com.brezze.library_common.utils

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.brezze.library_common.http.BaseResponse
import com.brezze.library_common.http.ExceptionHandle
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

/**
 * 有关Rx的工具类
 */
class RxUtils : Util {

    override fun init(application: Application) {

    }

    companion object {
        val intance by lazy { RxUtils() }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    fun <T> bindToLifecycle(lifecycle: Context): LifecycleTransformer<T> {
        return if (lifecycle is LifecycleProvider<*>) {
            (lifecycle as LifecycleProvider<*>).bindToLifecycle()
        } else {
            throw IllegalArgumentException("context not the LifecycleProvider type")
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    fun bindToLifecycle(@NonNull lifecycle: Fragment): LifecycleTransformer<*> {
        return if (lifecycle is LifecycleProvider<*>) {
            val bindToLifecycle = (lifecycle as LifecycleProvider<*>).bindToLifecycle<Any>()
            bindToLifecycle
        } else {
            throw IllegalArgumentException("fragment not the LifecycleProvider type")
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    fun <T> bindToLifecycle(lifecycle: LifecycleProvider<T>): LifecycleTransformer<T> {
        return lifecycle.bindToLifecycle()
    }

    /**
     * 线程调度器
     */
    fun <T> schedulersTransformer(): ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //异常处理
//    fun <T> exceptionTransformer():ObservableTransformer<T,T> = ObservableTransformer {
//            it.onErrorReturn(object :Function<Throwable,Observable<T>>{
//                override fun apply(t: Throwable): Observable<T> {
//                    return Observable.error(ExceptionHandle.handleException(t))
//                }
//            })
//    }

//    observable.onErrorReturn(
//    object : Function<Throwable, String> {
//        @Throws(Exception::class)
//        override fun apply(throwable: Throwable): String? {
//            return null
//        }
//    });


    class  HttpResponseFunc<T>:Function<Throwable,Observable<T>>{
        override fun apply(t: Throwable): Observable<T> {
            return Observable.error(ExceptionHandle.handleException(t))
        }
    }

    class HandleFuc<T>:Function<BaseResponse<T>,T>{
        override fun apply(t: BaseResponse<T>): T {
            if (!t.isOk()){
                throw RuntimeException("error!")
            }
            return t.data
        }

    }
}

