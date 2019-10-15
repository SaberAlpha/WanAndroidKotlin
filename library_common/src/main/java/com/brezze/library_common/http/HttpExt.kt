package com.brezze.library_common.http

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

inline fun <reified T> Single<Result<T>>.handleResult() = compose { upsteam ->
    upsteam.flatMap {
        when (it.errorCode) {
            0 ->
                Single.just(it.data ?: T::class.java.newInstance())
            else ->
                Single.error(HttpException(it.errorMsg))
        }
    }
}!!

inline fun <reified T> Observable<Result<T>>.handleResult2() = compose { upstream ->
    upstream.flatMap {
        when (it.errorCode) {
            0 ->
                Observable.just(it.data ?: T::class.java.newInstance())
            else ->
                Observable.error(HttpException(it.errorMsg))
        }
    }
}

fun <T> Single<T>.io2Main(): Single<T> {
    val transformer: SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    return compose(transformer)
}

fun <T> Observable<T>.io2Main2(): Observable<T> {
    val transformer: ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    return compose(transformer)
}