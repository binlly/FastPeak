package com.binlly.gankee.repo

import com.binlly.gankee.base.PREFERENCE_NAME_REMOTE_CONFIG
import com.binlly.gankee.base.Preference
import com.binlly.gankee.base.net.ReqParams
import com.binlly.gankee.base.net.RetrofitManager
import com.binlly.gankee.base.rx.IoTransformer
import com.binlly.gankee.ext.simpleName
import com.binlly.gankee.repo.service.RemoteService
import com.binlly.gankee.service.Services
import com.binlly.gankee.service.remoteconfig.RemoteMockModel
import io.reactivex.Observer
import retrofit2.Retrofit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by yy on 2017/10/10.
 */
object RemoteRepo {
    const val PREFERENCE_KEY_HOST = "host"

    var mockHost: String by MockHostDelegate()
    var mRetrofit: Retrofit = RetrofitManager.createRetrofit(mockHost)
    var mService = mRetrofit.create(RemoteService::class.java)

    fun requestMock(observer: Observer<RemoteMockModel>) {
        val params = ReqParams(simpleName)
        try {
            mService.requestMock(params.getFieldMap()).compose(IoTransformer()).subscribe(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class MockHostDelegate: ReadWriteProperty<Any?, String> {
        private var host: String by Preference(Services.app,
                PREFERENCE_NAME_REMOTE_CONFIG,
                RemoteRepo.PREFERENCE_KEY_HOST,
                "http://127.0.0.1")

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return host
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            if (host == value) return
            host = value
            RemoteRepo.mRetrofit = RetrofitManager.createRetrofit(host)
            RemoteRepo.mService = RemoteRepo.mRetrofit.create(RemoteService::class.java)
            Services.remoteConfig().pullMockConfig({}) //改变地址之后重新拉取并更新mock配置
        }
    }
}


