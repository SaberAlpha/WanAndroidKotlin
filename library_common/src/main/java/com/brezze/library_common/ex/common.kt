package com.brezze.library_common.ex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.call() {
    this.value = null
}