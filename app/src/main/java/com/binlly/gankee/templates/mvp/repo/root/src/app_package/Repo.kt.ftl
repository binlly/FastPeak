package ${packageName}

import ${applicationPackage}.base.net.ReqParams
import ${applicationPackage}.base.net.RetrofitManager
import ${applicationPackage}.base.rx.IoTransformer
import io.reactivex.Observer

object ${apiService}Repo {
    private val TAG = ${apiService}Repo::class.java.simpleName

    private val mService = RetrofitManager.create(${apiService}Service::class.java)
    private val mMockService = RetrofitManager.createMock(${apiService}Service::class.java)
    private val mServiceProxy = DynamicProxy(mService, mMockService).getProxy<${apiService}Service>()

    fun request(observer: Observer<${apiService}Model>) {
        val params = ReqParams(TAG)
        try {
            mServiceProxy.request(params.getFieldMap()).compose(IoTransformer()).subscribe(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
