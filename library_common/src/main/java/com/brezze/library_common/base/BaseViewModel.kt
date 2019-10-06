package com.brezze.library_common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.brezze.library_common.bus.event.SingleLiveEvent
import com.trello.rxlifecycle2.LifecycleProvider

class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {

    private lateinit var uc: UIChangeLiveData<*>
    private lateinit var lifecycle: LifecycleProvider<*>

    /**
     * 注入RxLifecycle生命周期
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = lifecycle
    }

    fun getLifecycleProvider(): LifecycleProvider<*> {
        return lifecycle
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }

    class UIChangeLiveData<T> : SingleLiveEvent<T>() {

    }
}