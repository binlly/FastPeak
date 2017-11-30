package com.binlly.gankee.base.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by yy on 2017/8/25.
 */
abstract class BaseMvpFragment<P: BaseFragmentPresenter>: BaseFragment(),
        BaseMvpView<P>,
        HasSupportFragmentInjector {

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var P: P

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun P(): P = P

    override fun handleArguments(arg: Bundle?) {
        P.handleArgument(arg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        P.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        P.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        P.onSaveInstanceState(outState)
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