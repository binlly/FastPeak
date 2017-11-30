package com.binlly.gankee.base.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.binlly.gankee.base.logger.Logger
import com.binlly.gankee.base.widget.LoadingProgressDialog
import org.greenrobot.eventbus.EventBus


/**
 * Created by yy on 2017/8/23.
 */
abstract class BaseActivity: AppCompatActivity(), BaseView {

    val logger = Logger()

    private lateinit var delegateView: DelegateView

    private val loadingDia: LoadingProgressDialog by lazy { LoadingProgressDialog(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        beforeInject()

        super.onCreate(savedInstanceState)

        //http://stackoverflow.com/questions/6356467/activity-stack-ordering-problem-when-launching-application-from
        // -android-app-inst
        //比如我打开应用到C，正常的启动流程是：A -> B ->C，在C界面按HOME键返回到桌面，如果长按HOME
        //，在最近打开应用列表中重新打开该应用，能够恢复到刚才退出的界面，但如果是点击该应用的桌面图标重新打开该应用，
        //则会显示一下A后再才会进入C，这个问题的解决方案如下

        if (isInstallerLaunchAndNeedFinish()) {
            finish()
            return
        }

        if (!handleIntent(intent)) {
            finish()
            return
        }

        logger.init(this::class.java)

        if (isNeedEventBus()) EventBus.getDefault().register(this)

        delegateView = DelegateView(this)
        delegateView.addContent(getContentView())
        if (isNeedToolbar()) {
            delegateView.showToolbar()
            getToolbar().title = customTitle()
        } else {
            delegateView.hideToolbar()
        }
        setContentView(delegateView)

        initView(savedInstanceState)
    }

    open fun handleIntent(intent: Intent): Boolean = true

    /**
     * 在注入之前做一些初始化，比如从intent解析注入依赖的数据
     */
    open fun beforeInject() {

    }

    /**
     * 比如我打开应用到C，正常的启动流程是：A -> B ->C，在C界面按HOME键返回到桌面，如果长按HOME
     * ，在最近打开应用列表中重新打开该应用，能够恢复到刚才退出的界面，但如果是点击该应用的桌面图标重新打开该应用，
     * 则会显示一下A后再才会进入C，这个问题的解决方案如下
     *
     * @return
     */
    open fun isInstallerLaunchAndNeedFinish(): Boolean = false

    /**
     * 是否需要注册EventBus
     *
     * @return
     */
    open fun isNeedEventBus(): Boolean = false

    override fun onDestroy() {
        loadingDia.dismiss()
        if (isNeedEventBus()) EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    abstract fun getContentView(): Int

    open fun isNeedToolbar(): Boolean = false

    open fun getToolbar(): Toolbar = delegateView.getToolbar()

    open fun customTitle(): String = ""

    /**
     * 初始化数据
     * 根据数据设置view内容
     */
    abstract fun initView(savedInstanceState: Bundle?)

    override fun showLoading(info: String?) {
        if (loadingDia.isShowing) return
        loadingDia.setTextInfo(info)
        loadingDia.setCancelable(true)
        loadingDia.setCanceledOnTouchOutside(false)
        loadingDia.show()
    }

    override fun hideLoading() {
        if (isFinishing) return
        if (loadingDia.isShowing) loadingDia.hide()
    }

    override fun showLoading() {
        showLoading(null)
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