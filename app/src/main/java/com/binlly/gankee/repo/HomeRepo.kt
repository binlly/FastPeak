package com.binlly.gankee.repo

import com.binlly.gankee.base.net.RetrofitManager
import com.binlly.gankee.base.rx.IoTransformer
import com.binlly.gankee.business.home.FeedAll
import io.reactivex.Observer

object HomeRepo {
    private val TAG = HomeRepo::class.java.simpleName

    private val mService = RetrofitManager.create(HomeService::class.java)
    private val mMockService = RetrofitManager.createMock(HomeService::class.java)
    private val mServiceProxy = DynamicProxy(mService, mMockService).getProxy<HomeService>()

    fun requestFeedAll(page: Int = 0, observer: Observer<List<FeedAll>?>) {
        try {
            mServiceProxy.requestFeedAll(page).compose(IoTransformer()).subscribe(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
