package com.brezze.library_common.http

class BaseResponse<T>(
    var errorCode: Int,
    var message: String,
    var totalCount: Int,
    var data: T
) {
    fun isOk(): Boolean = errorCode == 1
}