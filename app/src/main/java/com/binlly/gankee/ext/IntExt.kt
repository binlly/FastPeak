package com.binlly.gankee.ext

import android.util.TypedValue
import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/8/23.
 */

fun Int.dp2px(): Int {
    return TypedValue.applyDimension(1,
            this.toFloat(),
            Services.app().resources.displayMetrics).toInt()
}