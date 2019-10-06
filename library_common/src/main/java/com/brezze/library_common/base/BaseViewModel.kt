package com.brezze.library_common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.brezze.library_common.bus.event.SingleLiveEvent
import com.trello.rxlifecycle2.LifecycleProvider
import java.util.*

open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {

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

    fun getUC(): UIChangeLiveData<*> {
        if (null == uc) {
            uc = UIChangeLiveData<Objects>()
        }
        return uc
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uc.finishEvent.call()
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        uc.onBackPressedEvent.call()
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

        val showDialogEvent: SingleLiveEvent<String> by lazy {
            createLiveData(
                showDialogEvent
            )
        }

        val dismissDialogEvent: SingleLiveEvent<Void> by lazy {
            createLiveData(
                dismissDialogEvent
            )
        }

        val startActivityEvent: SingleLiveEvent<Map<String, Object>> by lazy {
            createLiveData(
                startActivityEvent
            )
        }

        val startContainerActivityEvent: SingleLiveEvent<Map<String, Object>> by lazy {
            createLiveData(
                startContainerActivityEvent
            )
        }

        val finishEvent: SingleLiveEvent<Void> by lazy {
            createLiveData(
                finishEvent
            )
        }

        val onBackPressedEvent: SingleLiveEvent<Void> by lazy {
            createLiveData(
                onBackPressedEvent
            )
        }

        private fun <T> createLiveData(liveData: SingleLiveEvent<T>): SingleLiveEvent<T> {
            var liveData = liveData
            if (liveData == null) {
                liveData = SingleLiveEvent<T>()
            }
            return liveData
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
        }
    }
}

