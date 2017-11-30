package com.binlly.gankee.base.net

import com.binlly.gankee.BuildConfig
import com.binlly.gankee.repo.RemoteRepo
import com.binlly.gankee.service.Services
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Author:  yy
 * Date:    2016/4/30
 * Desc:    Retrofit工具类
 */
object RetrofitManager {

    private val okHttpClient = createOkHttpClient()
    private var serverRetrofit = createServerRetrofit(RetrofitConfig.getBaseUrl())
    private var mockRetrofit = createServerRetrofit(RemoteRepo.mockHost)

    /**
     * 创建OkHttp
     *
     * @return
     */
    private fun createOkHttpClient(): OkHttpClient {
        // SSLSocketFactory socketFactory = getSocketFactory();
        val cacheFile = File(Services.app().cacheDir, "okhttp_cache")
        val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong()) //100Mb缓存
        val cacheControlInterceptor = CacheControlInterceptor()
        val builder = OkHttpClient.Builder().addInterceptor(cacheControlInterceptor).cache(
                cache).followRedirects(true)
        if (BuildConfig.DEBUG) {
            val bodyInterceptor = HttpLoggingInterceptor()
            bodyInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return builder.addInterceptor(bodyInterceptor).build()
        }

        // builder.addInterceptor(new Interceptor() {
        //     @Override
        //     public Response intercept(Chain chain) throws IOException {
        //         Request request = chain.request();
        //         return chain.proceed(request);
        //     }
        // });

        // if (null != socketFactory) {
        //     return builder.sslSocketFactory(socketFactory).addInterceptor(interceptor).build();
        // }
        return builder.build()
    }

    private fun reCreateRetrofit(serverUrl: String) {
        serverRetrofit = createServerRetrofit(serverUrl)
    }

    private fun reCreateMockRetrofit(mockHost: String) {
        mockRetrofit = createRetrofit(mockHost)
    }

    /**
     * 创建Server服务器对应的Retrofit
     *
     * @return
     */
    private fun createServerRetrofit(serverUrl: String): Retrofit {
        return createRetrofit(serverUrl)
    }

    /**
     * 创建retrofit实例
     */
    fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(
                GsonConverterFactory.create()).addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()).build()
    }

    /**
     * 创建Server服务器指定的api接口的实现
     *
     * @param clazz
     * @param <T>
     * @return
    </T> */
    fun <T> create(clazz: Class<T>): T {
        return serverRetrofit.create(clazz)
    }

    fun <T> createMock(clazz: Class<T>): T {
        return mockRetrofit.create(clazz)
    }
}
