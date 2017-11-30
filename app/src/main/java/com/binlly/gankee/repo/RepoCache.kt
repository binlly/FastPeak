package com.binlly.gankee.repo

import com.binlly.gankee.base.net.RetrofitConfig
import com.binlly.gankee.ext.fromJson
import com.binlly.gankee.ext.md5
import com.binlly.gankee.ext.toJson
import com.binlly.gankee.service.Services
import com.vincentbrison.openlibraries.android.dualcache.Builder

/**
 * Created by binlly on 16/9/20.
 * 缓存网络数据
 */

object RepoCache {
    private const val appVersion = 1
    private const val MAX_RAM_SIZE = 10 * 1024 * 1024
    private const val MAX_DISK_SIZE = 50 * 1024 * 1024
    private val jsonSerializer = JsonSerializer(String::class.java)

    private val BASE_KEY = RetrofitConfig.getBaseUrl()

    private val mCache = Builder<String>(BASE_KEY, appVersion).enableLog().useSerializerInRam(
            MAX_RAM_SIZE, jsonSerializer).useSerializerInDisk(MAX_DISK_SIZE, true, jsonSerializer,
            Services.app()).build()

    fun put(key: String, o: Any) {
        mCache.put((BASE_KEY + key).md5(), o.toJson())
    }

    operator fun <T> get(key: String, clazz: Class<T>): T {
        val json = mCache.get((BASE_KEY + key).md5())
        return json.fromJson(clazz)
    }

    fun delete(key: String) {
        mCache.delete(key.md5())
    }

    val ramSize: Long
        get() = mCache.ramUsedInBytes

    val maxRamSize: Long
        get() = MAX_RAM_SIZE.toLong()

    val diskSize: Long
        get() = mCache.diskUsedInBytes

    val maxDiskSize: Long
        get() = MAX_DISK_SIZE.toLong()

    fun clear() {
        mCache.invalidate()
    }
}
