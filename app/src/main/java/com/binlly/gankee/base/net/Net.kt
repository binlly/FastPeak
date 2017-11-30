package com.binlly.gankee.base.net

import android.content.Context
import android.net.ConnectivityManager
import com.binlly.gankee.service.Services


/**
 * Created by yy on 2017/8/23.
 */
object Net {
    fun isAvailable(): Boolean {
        val mConnectivityManager = Services.app().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) return mNetworkInfo.isAvailable
        return false
    }

    fun isWifi(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }
}