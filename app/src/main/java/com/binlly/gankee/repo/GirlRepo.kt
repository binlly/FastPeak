package com.binlly.gankee.repo

import com.binlly.gankee.base.net.RetrofitManager
import com.binlly.gankee.base.rx.IoTransformer
import com.binlly.gankee.business.girl.FeedGirl
import io.reactivex.Observer

object GirlRepo {
    private val TAG = GirlRepo::class.java.simpleName

    private val mService = RetrofitManager.create(GirlService::class.java)
    private val mMockService = RetrofitManager.createMock(GirlService::class.java)
    private val mServiceProxy = DynamicProxy(mService, mMockService).getProxy<GirlService>()

    fun requestGirls(page: Int, observer: Observer<List<FeedGirl>?>) {
        try {
            mServiceProxy.requestGirls(page).compose(IoTransformer()).subscribe(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
