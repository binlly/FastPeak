package com.binlly.gankee.base.net

import android.text.TextUtils

/**
 * {
 * "code": 0,
 * "data": {},
 * "message": ""
 * }
 *
 *
 * Http Status Code 为 200 并且返回内容的code不为0时 抛出APIException
 */
class ApiException(detailMessage: String = "未知错误"): RuntimeException(detailMessage) {

    var code: Int = 0

    constructor(code: Int, message: String?): this(getApiExceptionMessage(code, message)) {
        this.code = code
    }

    companion object {
        const val ERROR_CODE_INVALID_AUTHENTICATION_TOKEN = -1999 //token 过期
        const val ERROR_CODE_INVALID_AUTHENTICATION_REFRESH_TOKEN = -1999 //onReload token 过期
        const val ERROR_CODE_NO_PERMISSION = 1004
        const val ERROR_CODE_TOAST = 2001

        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，再显示给用户
         *
         * @param code
         * @return
         */
        private fun getApiExceptionMessage(code: Int, msg: String?): String {
            var message = msg ?: "出错了，请稍后重试 ($code)"
            when (code) {
                ERROR_CODE_INVALID_AUTHENTICATION_REFRESH_TOKEN -> message = "登录过期，请重新登录"

                else -> if (TextUtils.isEmpty(message)) {
                    message = "出错了，请稍后重试 ($code)"
                }
            }
            return message
        }
    }
}