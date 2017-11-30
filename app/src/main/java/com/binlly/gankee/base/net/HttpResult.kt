package com.binlly.gankee.base.net

/**
 * Author:  yy
 * Date:    2016/4/30
 * Desc:    网络请求返回
 */
class HttpResult<T> {
    var results: T? = null
    var error: Boolean = false
}
