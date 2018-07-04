package com.binlly.gankee.base.mvp

import android.content.Intent
import android.os.Bundle

interface BaseActivityPresenter: BasePresenter {
    fun handleIntent(intent: Intent): Boolean

    fun onCreate()

    fun onResume()

    fun onSaveInstanceState(outState: Bundle)

    fun onRestoreInstanceState(inState: Bundle)

    fun onPause()

    fun onDestroy()
}
