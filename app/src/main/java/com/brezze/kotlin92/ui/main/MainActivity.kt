package com.brezze.kotlin92.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brezze.kotlin92.BR
import com.brezze.kotlin92.R
import com.brezze.kotlin92.databinding.ActivityMainBinding
import com.brezze.library_common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {
    override fun initVariableId(): Int? = R.layout.activity_main

    override fun initContentView(savedInstanceState: Bundle?): Int = BR.viewModel

    override fun initData() {
        super.initData()
    }
}
