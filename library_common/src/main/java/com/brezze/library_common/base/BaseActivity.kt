package com.brezze.library_common.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.brezze.library_common.utils.MaterialDialogUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : RxAppCompatActivity(),
    IBaseActivity {

    protected lateinit var binding: V
    protected var viewModel: VM? = null
    private var viewModelId: Int? = null
    private lateinit var dialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //页面接受的参数方法
        initParam()
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState)
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack()
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        //注册RxBus
        viewModel?.registerRxBus()
    }

    override fun onDestroy() {
        super.onDestroy()
        //解除ViewModel生命周期感应
        viewModel?.let {
            lifecycle.removeObserver(it)
            it.removeRxBus()
        }
        binding?.let {
            it.unbind()
        }
    }


    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClass: Class<VM>
            val type = javaClass.genericSuperclass
            if (type is ParameterizedType) {
                modelClass = type.actualTypeArguments[1] as Class<VM>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel::class.java as Class<VM>
            }
            viewModel = createViewModel(this, modelClass)
        }
        //关联viewModel
        viewModelId?.let { binding.setVariable(it, viewModel) }
        //让ViewModel拥有View的生命周期感应
        viewModel?.let { lifecycle.addObserver(it) }
        //注入RxLifecycle生命周期
        viewModel?.injectLifecycleProvider(this)
    }

    fun refreshLayout() {
        ((viewModel != null) and (viewModelId != null)).apply {
            binding.setVariable(viewModelId!!, viewModel)
        }
    }

    fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }


    abstract fun initVariableId(): Int?

    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    fun initViewModel(): VM? {
        return null
    }

    override fun initParam() {

    }

    override fun initData() {

    }

    override fun initViewObservable() {

    }

    /**
     * dialog展示
     */
    fun showDialog(title: String) {
        if (dialog != null) {
            dialog.show()
        } else {
            val builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true)
            dialog = builder.show()
        }
    }

    /**
     * 取消
     */
    fun dismissDialog() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    private fun registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel?.getUC()?.showDialogEvent?.observe(this,
            Observer { t -> showDialog(t) })
        //加载对话框消失
        viewModel?.getUC()?.dismissDialogEvent?.observe(this, Observer {
            dismissDialog()
        })
        //关闭界面
        viewModel?.getUC()?.finishEvent?.observe(this, Observer {
            finish()
        })
        //关闭上一层
        viewModel?.getUC()?.onBackPressedEvent?.observe(this, Observer {
            onBackPressed()
        })
    }
}