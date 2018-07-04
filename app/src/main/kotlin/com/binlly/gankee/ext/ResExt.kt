package com.binlly.gankee.ext

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/11/17.
 */


fun View.getColor(colorResId: Int): Int = getColor(resources, colorResId)

fun View.getString(colorResId: Int): String = getString(resources, colorResId)

fun Activity.getColorExt(colorResId: Int): Int = getColor(resources, colorResId)
fun Activity.getStringExt(colorResId: Int): String = getString(resources, colorResId)

fun Fragment.getColor(colorResId: Int): Int = getColor(resources, colorResId)
fun Fragment.getString(colorResId: Int): String = getString(resources, colorResId)

private fun getColor(resources: Resources, colorResId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Services.app.getColor(colorResId)
    } else {
        resources.getColor(colorResId)
    }
}

private fun getString(resources: Resources, stringResId: Int): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Services.app.getString(stringResId)
    } else {
        resources.getString(stringResId)
    }
}