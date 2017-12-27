package com.binlly.gankee.ext

import com.google.gson.Gson

/**
 * Created by yy on 2017/8/23.
 */

private val gson by lazy { Gson() }

fun Any.toJson(): String = gson.toJson(this)

val Any.simpleName: String
    get() = this::class.java.simpleName

