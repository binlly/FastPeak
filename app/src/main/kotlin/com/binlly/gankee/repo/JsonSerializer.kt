package com.binlly.gankee.repo


import com.google.gson.Gson
import com.vincentbrison.openlibraries.android.dualcache.CacheSerializer

/**
 * Created by binlly on 2016/11/1.
 */

class JsonSerializer<T>(private val clazz: Class<T>): CacheSerializer<T> {

    override fun fromString(data: String): T {
        return Gson().fromJson(data, clazz)
    }

    override fun toString(`object`: T): String {
        return Gson().toJson(`object`)
    }
}