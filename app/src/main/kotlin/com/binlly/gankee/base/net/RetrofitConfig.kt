package com.binlly.gankee.base.net

import com.binlly.gankee.BuildConfig

/**
 * Created by yy on 2017/8/23.
 */
object RetrofitConfig {
    private const val BASE_URL: String = "http://gank.io/api/"
    private const val BASE_URL_DEBUG: String = "http://gank.io/api/"

    const val PREFERENCE_KEY_MOCK_HOST: String = "mock_host"

    fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) BASE_URL_DEBUG else BASE_URL
    }

    val debugHost: String
        get() = BASE_URL_DEBUG

    val onlineHost: String
        get() = BASE_URL
}