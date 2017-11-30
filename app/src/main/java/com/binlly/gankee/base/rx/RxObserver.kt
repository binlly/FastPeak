package com.binlly.gankee.base.rx

import com.binlly.gankee.BuildConfig
import com.binlly.gankee.base.mvp.BaseView
import com.binlly.gankee.base.net.ApiException
import com.binlly.gankee.base.net.Net
import com.binlly.gankee.ext.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketTimeoutException


/**
 * Author:  yy
 * Date:    2016/5/3
 * Desc:    对Subscriber进行包装，简化开发代码
 */
abstract class RxObserver<T> constructor(private val baseView: BaseView? = null,
                                         checkNetwork: Boolean = true): Observer<T> {

    private var checkNetwork = true
    private var disposables: CompositeDisposable

    override fun onSubscribe(d: Disposable) {
        disposables.add(d)
        baseView?.showLoading("")
        if (checkNetwork) if (!Net.isAvailable()) {
            ToastUtils.showToast("无网络，请检查网络连接")
        }
    }

    init {
        this.checkNetwork = checkNetwork
        disposables = CompositeDisposable()
    }

    override fun onNext(result: T) {

    }

    override fun onComplete() {
        baseView?.hideLoading()
    }

    override fun onError(e: Throwable) {
        if (BuildConfig.DEBUG) e.printStackTrace()

        when (e) {
            is SocketTimeoutException -> handleSocketTimeoutException(e)
            is HttpException -> handleHttpException(e)
            is ApiException -> handleApiException(e)
        }

        // RxJava中的Subscriber执行onError后并不会执行onComplete,所以这里要调用一下
        onComplete()
    }

    private fun handleSocketTimeoutException(e: Throwable) {
        ToastUtils.showToast("您的网络状况较差")
    }

    /**
     * 统一需要拦截处理的错误在此处理，根据不同的Api异常做相应的处理并提示用户
     *
     * @param e
     */
    private fun handleApiException(e: ApiException) {
        when (e.code) {
            ApiException.ERROR_CODE_INVALID_AUTHENTICATION_TOKEN -> handleTokenFail()
            ApiException.ERROR_CODE_INVALID_AUTHENTICATION_REFRESH_TOKEN -> handleRefreshTokenFail()
            ApiException.ERROR_CODE_TOAST -> e.message?.let { ToastUtils.showToast(it) }
            else -> onHandleApiException(e)
        }
    }

    /**
     * 除了特殊处理，所有关于后端弹出的错误信息都需要重写此方法，而不是onError
     *
     * @param e
     */
    fun onHandleApiException(e: ApiException) {

    }

    /**
     * 处理refresh token授权失败
     */
    private fun handleRefreshTokenFail() {
        ToastUtils.showToast("登录过期,请重新登录")
        logout()
    }

    private fun logout() {

    }

    /**
     * 处理token授权失败 刷新令牌
     */
    private fun handleTokenFail() {
        // AccountRepo.requestTokenRefresh(new RxSubscriber<RefreshTokenResult>() {
        //     @Override public void onNext(RefreshTokenResult token) {
        //         String sToken = token.token.token;
        //         String sRefreshToken = token.token.refresh_token;
        //         if (!TextUtils.isEmpty(sToken) && !TextUtils.isEmpty(sRefreshToken)) {
        //             ConfigUtil.saveToken(sToken);
        //             ConfigUtil.saveRefreshToken(sRefreshToken);
        //         } else {
        //             handleRefreshTokenFail();
        //         }
        //     }
        //
        //     @Override public void onError(Throwable e) {
        //         super.onError(e);
        //         handleRefreshTokenFail();
        //     }
        // });
    }


    /**
     * 根据不同的Http异常做出相应的处理并给用户相应的提示
     * 目前只对部分常见异常做出了处理，后续会继续完善
     *
     * @param e
     */
    private fun handleHttpException(e: HttpException) {
        when (e.code()) {
            400 -> ToastUtils.showToast("错误请求")
            408 -> ToastUtils.showToast("请求超时，请检查网络后重试…")
            500 -> ToastUtils.showToast("服务器开小差了，请稍后重试…")
            else -> ToastUtils.showToast(e.message())

        }
    }

    fun cancel() {
        disposables.clear()
    }
}
