package com.brezze.kotlin92.ui.main

import android.app.Application
import android.util.Log
import com.brezze.library_common.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    fun onClickView() {
//        showDialog()
        Observable.timer(3, TimeUnit.SECONDS)
            .compose(getLifecycleProvider().bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showDialog() }
            .doAfterTerminate { dimissDialog() }
            .subscribe {
                Log.e("1223", "收到！")
                toast("订阅收到！")
                startActivity<TestActivity>()
            }
    }

    fun cancle() {
        dimissDialog()
    }
}