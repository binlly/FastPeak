package com.binlly.gankee.base.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by yy on 2017/8/24.
 */
abstract class BaseMvpActivity<P: BaseActivityPresenter>: BaseActivity(),
        BaseMvpView<P>,
        HasFragmentInjector,
        HasSupportFragmentInjector {

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject lateinit var P: P

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = frameworkFragmentInjector

    override fun P(): P = P

    override fun handleIntent(intent: Intent): Boolean = P.handleIntent(intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        P.onCreate()
    }

    override fun onResume() {
        super.onResume()
        P.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        P.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(inState: Bundle) {
        super.onRestoreInstanceState(inState)
        P.onSaveInstanceState(inState)
    }

    override fun onPause() {
        super.onPause()
        P.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        P.onDestroy()
    }
}