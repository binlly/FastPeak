package com.binlly.gankee.base.rx

import com.binlly.gankee.base.net.ApiException
import com.binlly.gankee.base.net.HttpResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:  yy
 * Date:    2016/5/2
 * Desc:    将HttpResult<T>的observable转化为<T>的observable
 * 在IO线程产生 在主线程消费事件
 *
 * @param <T>
 */
class IoTransformer<T>: ObservableTransformer<HttpResult<T>, T> {
    @Throws(ApiException::class) override fun apply(
            upstream: Observable<HttpResult<T>>
    ): ObservableSource<T?> {
        return upstream.map { httpResult ->
            // if (httpResult.status.status_code != 0) {
            //     throw ApiException(httpResult.status.status_code, httpResult.status.status_reason)
            // }
            // httpResult.result
            if (httpResult.error) {
                throw ApiException()
            }
            httpResult.results
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}