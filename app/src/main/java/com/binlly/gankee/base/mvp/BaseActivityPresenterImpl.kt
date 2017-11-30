package com.binlly.gankee.base.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle

abstract class BaseActivityPresenterImpl<out V: BaseView>(val context: Context,
                                                          private val mView: V):
        BaseActivityPresenter {

    fun V(): V = this.mView

    override fun handleIntent(intent: Intent): Boolean = true

    override fun onCreate() {
        //do nothing
    }

    override fun onResume() {
        //do nothing
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //do nothing
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun onPause() {
        //do nothing
    }

    override fun onDestroy() {
        //do nothing
    }
}
