package com.binlly.gankee.ext

import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/10/16.
 * Note：未经测试，比如添加的对象和移除的是否是同一个对象
 */
fun <R> (() -> R).withDelay(delay: Long = 0L) {
    Services.postDelayed(delay) { this.invoke() }
}

fun <R> (() -> R).releaseDelay() {
    Services.removeRunnable { this }
}