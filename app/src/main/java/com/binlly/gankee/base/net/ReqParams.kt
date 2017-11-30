package com.binlly.gankee.base.net

import android.util.Log
import com.google.gson.Gson
import java.util.*

/**
 * Created by yy on 2015/7/14.
 * 网络请求参数
 * 封装了SessionId和AccessToken,version
 * 封装请求签名
 */
class ReqParams(private val TAG: String) {

    private val queryMap: MutableMap<String, String>
    private val fieldMap: MutableMap<String, Any>
    private val signMap: MutableMap<String, String>

    init {
        queryMap = HashMap()
        signMap = HashMap()
        fieldMap = HashMap()
    }

    /**
     * 添加参数到 bodyParam
     *
     * @param key
     * @param value
     */
    fun addParam(key: String, value: Any?) {
        if (null != value) {
            //            value = value.trim();
            fieldMap.put(key, value)
        }
    }

    /**
     * 添加参数到 query
     *
     * @param key
     * @param value
     */

    fun addQuery(key: String, value: Number?) {
        value ?: return
        addQuery(key, value.toString())
    }

    fun addQuery(key: String, value: String) {
        var value = value
        value = value.trim { it <= ' ' }
        queryMap.put(key, value)
        signMap.put(key, value)
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    fun getFieldMap(): Map<String, String> {
        val map = HashMap<String, String>()

        map.put("param", Gson().toJson(fieldMap))

        // TODO: 2017/8/23 统一添加公共头
        // map.put("headers", new Gson().toJson(DeviceUtil.getCommonHeader()));

        Log.d(TAG + "-param--", map["param"])
        if (printHeaders) Log.w(TAG + "-headers--", map["headers"])

        return map
    }

    fun getQueryMap(): Map<String, String> {
        if (queryMap.containsKey("s")) {
            queryMap.remove("s")
        }
        // queryMap.put("s", getSignature());
        return queryMap
    }

    companion object {
        val GET = "GET"
        val POST = "POST"
        val PUT = "PUT"
        val DELETE = "DELETE"

        private val printHeaders = false
    }
}
