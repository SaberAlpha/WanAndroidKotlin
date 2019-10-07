package com.brezze.library_common.base

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.brezze.library_common.bus.event.SingleLiveEvent
import com.brezze.library_common.ex.call
import com.trello.rxlifecycle2.LifecycleProvider

open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {
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


    /**
     * 关闭界面
     */
    fun finish() {
        finishEvent.call()
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        onBackPressedEvent.call()
    }

    /**
     * 显示dialog
     */
    fun showDialog() {
        showDialogEvent.value = "显示"
    }

    /**
     * 取消dialog
     */
    fun dimissDialog() {
        dismissDialogEvent.call()
    }

    /**
     * toast显示
     */
    fun toast(title: String) {
        toastEvent.value = title
    }

    /**
     * 页面跳转
     */
    inline fun <reified T : Activity> startActivity(vararg params: Pair<String, Any?>) {
        val map = mapOf(CLASS to T::class.java, BUNDLE to params)
        startActivityEvent.value = map
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

    val showDialogEvent: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val dismissDialogEvent: MutableLiveData<Void> by lazy { MutableLiveData<Void>() }

    val toastEvent: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val startActivityEvent: MutableLiveData<Map<String, Any>> by lazy { MutableLiveData<Map<String, Any>>() }

    val finishEvent: MutableLiveData<Void> by lazy { MutableLiveData<Void>() }

    val onBackPressedEvent: MutableLiveData<Void> by lazy { MutableLiveData<Void>() }

    companion object {
        val CLASS: String = "CLASS"
        val CANONICAL_NAME: String = "CANONICAL_NAME"
        val BUNDLE: String = "BUNDLE"
    }
}

