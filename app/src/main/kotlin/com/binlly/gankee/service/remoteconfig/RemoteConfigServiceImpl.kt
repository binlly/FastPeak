package com.binlly.gankee.service.remoteconfig

import com.binlly.gankee.BuildConfig
import com.binlly.gankee.base.rx.RxObserver
import com.binlly.gankee.config.AppConfigModel
import com.binlly.gankee.ext.ToastUtils
import com.binlly.gankee.repo.RemoteRepo


/**
 * Created by binlly on 16/8/18.
 * 拉取的远程通用配置的解析并提供相关服务
 */
class RemoteConfigServiceImpl private constructor(): RemoteConfigService {

    override var config: AppConfigModel? = null
    override var mockRouter: Map<String, Int> = mapOf()

    override fun saveConfig(configModel: AppConfigModel) {
        config = configModel
    }

    companion object {
        private var INSTANCE: RemoteConfigServiceImpl? = null

        fun registerInstance(): RemoteConfigServiceImpl {
            return INSTANCE?.let { INSTANCE } ?: RemoteConfigServiceImpl()
        }
    }

    // 拉取mock服务器配置
    override fun pullMockConfig(listener: () -> Unit) {
        if (!BuildConfig.DEBUG) return //Debug模式才能设置Mock服务

        RemoteRepo.requestMock(object: RxObserver<RemoteMockModel>() {
            override fun onNext(result: RemoteMockModel) {
                super.onNext(result)
                mockRouter = result.router
                listener?.invoke()
                ToastUtils.showToast("拉取配置成功")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                e.message?.let {
                    ToastUtils.showToast(e.message!!)
                }
            }
        })
    }

    /**
     * key request url
     * return true: 从mock服务器请求  false: 从正常服务器请求
     */
    override fun isMock(key: String): Boolean {
        return 1 == mockRouter[key]
    }

    override fun getRouter(): Map<String, Int> {
        return mockRouter
    }
}
