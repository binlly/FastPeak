package com.binlly.gankee.base.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binlly.gankee.R
import com.binlly.gankee.base.logger.Logger
import com.binlly.gankee.base.widget.LoadingPage
import com.binlly.gankee.base.widget.LoadingProgressDialog
import org.greenrobot.eventbus.EventBus

/**
 * Created by yy on 2017/8/25.
 */
abstract class BaseFragment: Fragment(), BaseView {

    val logger = Logger()

    private val loadingDia: LoadingProgressDialog by lazy { LoadingProgressDialog(context!!) }
    private lateinit var loadingPage: LoadingPage

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        logger.init(this::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments(arguments)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        loadingPage = object: LoadingPage(context!!) {
            override fun createLoadingView(): View {
                return this@BaseFragment.loadingView
            }

            override fun createEmptyView(): View {
                return this@BaseFragment.emptyView
            }

            override fun createErrorView(): View {
                return this@BaseFragment.errorView
            }

            override fun createSucceedView(): View {
                return getContentView()
            }
        }
        return loadingPage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreFragmentState(savedInstanceState)

        if (isNeedEventBus()) EventBus.getDefault().register(this)

        initView()
    }

    abstract fun handleArguments(arg: Bundle?)

    /**
     * 恢复viewState
     *
     * @param savedInstanceState
     */
    open fun restoreFragmentState(savedInstanceState: Bundle?) {

    }

    /**
     * 加载布局文件
     *
     * @return
     */
    open fun getContentView(): View {
        return layoutInflater.inflate(getContentViewId(), null)
    }


    override fun onDestroyView() {
        loadingDia.dismiss()
        if (isNeedEventBus()) EventBus.getDefault().unregister(this)

        super.onDestroyView()
    }

    override fun showLoading() {
        showLoading(null)
    }

    /**
     * 显示加载进度对话框
     */
    override fun showLoading(info: String?) {
        loadingDia.setTextInfo(info)
        loadingDia.setCancelable(true)
        loadingDia.setCanceledOnTouchOutside(false)
        loadingDia.show()
    }

    /**
     * 隐藏加载进度对话框
     */

    override fun hideLoading() {
        activity?.let {
            if (activity!!.isFinishing) return else loadingDia.dismiss()
        } ?: return
    }

    abstract fun getContentViewId(): Int

    protected abstract fun initView()

    /**
     * 加载失败时重新加载
     * 刷洗页面
     */
    open fun onReload() {

    }

    /**
     * 是否需要注册EventBus
     *
     * @return
     */
    open fun isNeedEventBus(): Boolean {
        return false
    }

    fun setPageLoading() {
        loadingPage.setLoading()
    }

    fun setPageEmpty() {
        loadingPage.setEmpty()
    }

    fun setPageError() {
        loadingPage.setError()
    }

    fun setPageSuccess() {
        loadingPage.setSucceed()
    }

    /**
     * 创建加载中页面
     *
     * @return
     */
    private val loadingView: View
        get() = layoutInflater.inflate(R.layout.loading_page_loading, null)

    /**
     * 内容为空的布局id
     * 自定义空布局是可以在fragment中重写次方法
     *
     *
     * 默认点击图片可以重新加载
     *
     * @return
     */
    private val emptyView: View
        get() {
            val view = layoutInflater.inflate(R.layout.loading_page_empty, null)
            view.findViewById<View>(R.id.tv_empty).setOnClickListener {
                loadingPage.setLoading()
                this@BaseFragment.onReload()
            }
            return view
        }

    /**
     * 加载失败页面
     *
     * @return
     */
    protected //点击重新加载
    val errorView: View
        get() {
            val view = layoutInflater.inflate(R.layout.loading_page_error, null)
            view.findViewById<View>(R.id.tv_error).setOnClickListener {
                loadingPage.setLoading()
                this@BaseFragment.onReload()
            }
            return view
        }


    fun v(s: String?) {
        logger.v(s)
    }

    fun v(tag: String, s: String?) {
        logger.v(tag, s)
    }

    fun d(s: String?) {
        logger.v(s)
    }

    fun d(tag: String, s: String?) {
        logger.v(tag, s)
    }

    fun i(s: String?) {
        logger.v(s)
    }

    fun i(tag: String, s: String?) {
        logger.v(tag, s)
    }

    fun w(s: String?) {
        logger.v(s)
    }

    fun w(tag: String, s: String?) {
        logger.v(tag, s)
    }

    fun e(s: String?) {
        logger.v(s)
    }

    fun e(tag: String, s: String?) {
        logger.v(tag, s)
    }
}
