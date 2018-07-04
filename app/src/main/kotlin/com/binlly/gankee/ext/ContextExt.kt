package com.binlly.gankee.ext

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.getScreenWidth(): Int {
    val metric = DisplayMetrics()
    (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}

fun Context.getScreenHeight(): Int {
    val metric = DisplayMetrics()
    (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metric)
    return metric.heightPixels
}