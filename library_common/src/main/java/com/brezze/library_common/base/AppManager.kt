package com.brezze.library_common.base

import android.app.Activity
import androidx.fragment.app.Fragment
import java.util.*

class AppManager private constructor() {

    private lateinit var activityStack: Stack<Activity>

    private lateinit var fragmentStack: Stack<Fragment>

    companion object {
        val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppManager() }
    }

    fun getActivityStack(): Stack<Activity> {
        return activityStack
    }

    fun getFragmentStack(): Stack<Fragment> {
        return fragmentStack
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) = (activityStack ?: Stack<Activity>())?.add(activity)

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity) = activityStack?.remove(activity)

    /**
     * 是否有activity
     */
    fun isActivity(): Boolean = activityStack.empty().not()

    /**
     * 获取当前Activity
     */
    fun currentActivity(): Activity = activityStack?.lastElement()

    /**
     * 结束当前Activity
     */
    fun finishActivity(activity: Activity) {
        if (activity?.isFinishing) activity.finish()
    }

    /**
     * 结束指定类名Acitvity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity(){
        for (activity in activityStack){
            finishActivity(activity)
        }
        activityStack.clear()
    }

    /**
     * 获取指定的Activity
     */
    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null)
            for (activity in activityStack) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        return null
    }
}