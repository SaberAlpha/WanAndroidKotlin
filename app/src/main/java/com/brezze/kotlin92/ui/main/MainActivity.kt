package com.brezze.kotlin92.ui.main

import android.os.Bundle
import com.brezze.kotlin92.BR
import com.brezze.kotlin92.R
import com.brezze.kotlin92.databinding.ActivityMainBinding
import com.brezze.library_common.base.BaseActivity
import com.brezze.library_common.widget.status_view.StatusView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {
    override fun initVariableId(): Int? = BR.viewModel

    override fun initContentView(savedInstanceState: Bundle?): Int =R.layout.activity_main

    override fun initData() {
        super.initData()

        statusView.addDelegate(StatusView.Status.EMPTY,R.layout.view_status_list_empty){

        }

        statusView.empty {

        }


    }
}
