package com.binlly.gankee.base.rx

import com.binlly.gankee.base.net.ApiException
import com.binlly.gankee.base.net.HttpResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Author:  yy
 * Date:    2016/8/27
 * Desc:    将HttpResult<T>的observable转化为<T>的observable
 * 在同一个线程产生、消费事件
 *
 * @param <T>
 **/
class SyncTransformer<T>: ObservableTransformer<HttpResult<T>, T> {
    @Throws(ApiException::class) override fun apply(
            upstream: Observable<HttpResult<T>>
    ): ObservableSource<T?> {
        return upstream.map { httpResult ->
            // val status_code = httpResult.status.status_code
            // val status_reason = httpResult.status.status_reason
            // if (status_code != 0) {
            //     if (status_code in 2000..2999) {
            //         status_reason?.let { ToastUtils.showToast(it) }
            //     }
            //     throw ApiException(status_code, status_reason)
            // }
            // httpResult.result

            if (httpResult.error) {
                throw ApiException()
            }
            httpResult.results
        }
    }
}