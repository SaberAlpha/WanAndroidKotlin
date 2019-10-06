package com.brezze.kotlin92.app

import android.app.Application
import androidx.multidex.MultiDexApplication

class App :MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}