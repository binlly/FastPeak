package com.binlly.gankee.base.net

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Author:  yy
 * Date:    2016/5/10
 * Desc:    缓存处理的拦截器
 */
class CacheControlInterceptor: Interceptor {
    @Throws(IOException::class) override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!Net.isAvailable() && "GET" == request.method()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val originalResponse = chain.proceed(request)
        return if (Net.isAvailable()) {
            val cacheControl: String = if ("GET" == request.method()) {
                "max-age=5" //GET请求缓存5秒
            } else {
                request.cacheControl().toString() //读接口上的@Headers里的配置
            }
            originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build()
        } else {
            originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=2419200").removeHeader("Pragma").build()
        }
    }
}
