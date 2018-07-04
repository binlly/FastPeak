package com.binlly.gankee.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

import com.binlly.gankee.service.Services


abstract class LoadingPage(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0):
        FrameLayout(context, attrs, defStyle) {

    private val mLoadingView: View? by lazy { createLoadingView() } //转圈的view
    private val mErrorView: View? by lazy { createErrorView() } //错误的view
    private val mEmptyView: View? by lazy { createEmptyView() } //空的view
    private val mSucceedView: View? by lazy { createSucceedView() } //成功的view

    private var mState: Int = 0 //默认的状态


    init {
        init()
    }

    private fun init() {
        //初始化状态
        mState = STATE_UNLOADED

        if (null != mLoadingView) {
            addView(mLoadingView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }

        if (null != mErrorView) {
            addView(mErrorView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }

        if (null != mEmptyView) {
            addView(mEmptyView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }

        if (null != mSucceedView) {
            addView(mSucceedView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }
        showSafePagerView()
    }

    /**
     * 确保更新页面在主线程
     */
    private fun showSafePagerView() {
        Services.post(Runnable { showPagerView() })
    }

    /**
     * 更新页面显示
     */
    private fun showPagerView() {
        if (null != mLoadingView) {
            mLoadingView!!.visibility = if (mState == STATE_UNLOADED || mState == STATE_LOADING) View.VISIBLE else View.GONE
        }
        if (null != mErrorView) {
            mErrorView!!.visibility = if (mState == STATE_ERROR) View.VISIBLE else View.GONE
        }
        if (null != mEmptyView) {
            mEmptyView!!.visibility = if (mState == STATE_EMPTY) View.VISIBLE else View.GONE
        }

        if (mSucceedView != null) {
            mSucceedView!!.visibility = if (mState == STATE_SUCCEED) View.VISIBLE else View.GONE
        }
    }

    /**
     * 创建数据加载中时的页面
     *
     * @return
     */
    protected abstract fun createLoadingView(): View?

    /**
     * 创建加载数据为空时的页面
     *
     * @return
     */
    protected abstract fun createEmptyView(): View?

    /**
     * 创建加载数据失败时的页面
     *
     * @return
     */
    protected abstract fun createErrorView(): View?

    /**
     * 创建加载成功时的页面
     *
     * @return
     */
    protected abstract fun createSucceedView(): View?


    /**
     * 设置加载中
     */
    fun setLoading() {
        mState = STATE_LOADING
        showSafePagerView()
    }

    /**
     * 设置加载失败
     */
    fun setError() {
        mState = STATE_ERROR
        showSafePagerView()
    }

    /**
     * 设置数据为空
     */
    fun setEmpty() {
        mState = STATE_EMPTY
        showSafePagerView()
    }

    /**
     * 设置加载成功
     */
    fun setSucceed() {
        mState = STATE_SUCCEED
        showSafePagerView()
    }

    companion object {
        //加载默认的状态
        private const val STATE_UNLOADED = 1
        //加载的状态
        private const val STATE_LOADING = 2
        //加载失败的状态
        private const val STATE_ERROR = 3
        //加载空的状态
        private const val STATE_EMPTY = 4
        //加载成功的状态
        private const val STATE_SUCCEED = 5
    }
}
