package com.binlly.gankee.ext

import com.google.gson.Gson
import java.lang.reflect.Type
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by yy on 2017/8/23.
 */

fun String.md5(): String {
    return try {
        val sb = StringBuilder()
        MessageDigest.getInstance("md5").digest(toByteArray()).map { it.toInt() and 0xff }.map {
            it.toString(16)
        }.forEach {
            if (it.length == 1) {
                sb.append("0$it")
            } else {
                sb.append(it)
            }
        }
        sb.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        ""
    }
}

fun <T> String.fromJson(clazz: Class<T>): T {
    return Gson().fromJson(this, clazz)
}

fun <T> String.fromJson(type: Type): T {
    return Gson().fromJson(this, type)
}