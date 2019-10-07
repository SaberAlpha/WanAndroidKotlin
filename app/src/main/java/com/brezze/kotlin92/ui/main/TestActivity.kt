package com.brezze.kotlin92.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brezze.kotlin92.R
import org.jetbrains.anko.toast

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val s = intent.extras?.get("key")?.toString()
        toast("key值是$s")
    }
}
