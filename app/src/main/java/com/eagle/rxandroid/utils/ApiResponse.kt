package com.eagle.rxandroid.utils


class ApiResponse(status: ApiStatus, var data: String?, var error: Throwable?) {
    var status: ApiStatus? = status

    fun loading(): ApiResponse {
        return ApiResponse(ApiStatus.LOADING, null, null)
    }

    fun success(data: String?): ApiResponse {
        return ApiResponse(ApiStatus.SUCCESS, data, null)
    }

    fun error(error: Throwable?): ApiResponse {
        return ApiResponse(ApiStatus.ERROR, null, error)
    }
}