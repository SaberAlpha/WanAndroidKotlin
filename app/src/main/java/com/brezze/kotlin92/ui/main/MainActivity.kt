package com.brezze.kotlin92.ui.main

import android.os.Bundle
import com.brezze.kotlin92.BR
import com.brezze.kotlin92.R
import com.brezze.kotlin92.databinding.ActivityMainBinding
import com.brezze.library_common.base.BaseActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {
    override fun initVariableId(): Int? = BR.viewModel

    override fun initContentView(savedInstanceState: Bundle?): Int =R.layout.activity_main

    override fun initData() {
        super.initData()
    }
}
