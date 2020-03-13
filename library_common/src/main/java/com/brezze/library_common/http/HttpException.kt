package com.brezze.library_common.http

class HttpException(var code:Int,var errorMsg: String) : Throwable(message = errorMsg)