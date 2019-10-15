package com.brezze.kotlin92.ui.main

import android.app.Application
import android.util.Log
import com.brezze.library_common.base.BaseViewModel
import com.brezze.library_common.http.HttpClient
import com.brezze.library_common.utils.RxUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    fun onClickView() {
//        showDialog()
//        Observable.timer(3, TimeUnit.SECONDS)
//            .compose(getLifecycleProvider().bindToLifecycle())
//            .compose(RxUtils.intance.schedulersTransformer())
//            .doOnSubscribe { showDialog() }
//            .doAfterTerminate { dimissDialog() }
//            .subscribe {
//                Log.e("1223", "收到！")
//                toast("订阅收到！")
//                startActivity<TestActivity>()
//            }

        HttpClient.api.banners().compose(getLifecycleProvider().bindToLifecycle())
            .compose(RxUtils.intance.schedulersTransformer())
            .doOnSubscribe { showDialog() }
            .doAfterTerminate { dimissDialog() }
            .subscribe {
                        val orNull = it.getOrNull()
                var desc = orNull?.get(0)!!.desc
                Log.d("banners",desc)
            }
    }

    fun cancle() {
        dimissDialog()
    }
}