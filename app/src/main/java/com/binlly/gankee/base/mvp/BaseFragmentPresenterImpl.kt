package com.binlly.gankee.base.mvp

import android.content.Context
import android.os.Bundle

open class BaseFragmentPresenterImpl<out V: BaseView>(val context: Context, private val mView: V):
        BaseFragmentPresenter {

    fun V(): V = mView

    override fun handleArgument(bundle: Bundle?) {
        //do something in subclass
    }

    override fun onCreate(inState: Bundle?) {
        //do something in subclass
    }

    override fun onResume() {
        //do something in subclass
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        //do something in subclass
    }

    override fun onPause() {
        //do something in subclass
    }

    override fun onDestroy() {
        //do something in subclass
    }
}
