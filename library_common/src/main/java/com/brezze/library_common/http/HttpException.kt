package com.brezze.library_common.http

class HttpException(errorMsg: String) : Throwable(message = errorMsg)