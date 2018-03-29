package com.binlly.gankee.ext

import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP
import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/8/23.
 */

fun Int.dp2px(): Int {
    return TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), Services.app().resources.displayMetrics).toInt()
}

fun Int.sp2px(): Int {
    return TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), Services.app().resources.displayMetrics).toInt()
}