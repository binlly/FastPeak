package com.binlly.gankee.base.logger

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by yy on 2017/8/31.
 */


var logable = true
var logLevel = LogLevel.DEBUG


class Logger {

    private var TAG = "CUSTOM_LOGGER"
    private var header: String = ""

    fun init(clazz: Class<*>): Logger {
        TAG = clazz.simpleName
        return this
    }

    /**
     * 支持用户自己传tag，可扩展性更好
     * @param tag
     */
    fun init(tag: String): Logger {
        TAG = tag
        return this
    }

    /**
     * header是自定义的内容，可以放App的信息版本号等，方便查找和调试
     * @param tag
     */
    fun header(header: String): Logger {
        this.header = header
        return this
    }


    // E
    fun e(msg: String?) {
        if (!checkLogableAndLevel(LogLevel.ERROR)) return
        log(msg, level = LogLevel.ERROR)
    }

    fun e(tag: String, msg: String?) {
        if (!checkLogableAndLevel(LogLevel.ERROR)) return
        log(msg, tag = tag, level = LogLevel.ERROR)
    }

    fun e(msg: String?, tr: Throwable) {
        if (!checkLogableAndLevel(LogLevel.ERROR)) return
        log(msg, tr = tr, level = LogLevel.ERROR)
    }


    // W
    fun w(msg: String?) {
        if (!checkLogableAndLevel(LogLevel.WARN)) return
        log(msg, level = LogLevel.WARN)
    }

    fun w(tag: String, msg: String?) {
        if (!checkLogableAndLevel(LogLevel.WARN)) return
        log(msg, tag = tag, level = LogLevel.WARN)
    }

    fun w(msg: String?, tr: Throwable) {
        if (!checkLogableAndLevel(LogLevel.WARN)) return
        log(msg, tr = tr, level = LogLevel.WARN)
    }


    // I
    fun i(msg: String?) {
        if (!checkLogableAndLevel(LogLevel.INFO)) return
        log(msg, level = LogLevel.INFO)
    }

    fun i(tag: String, msg: String?) {
        if (!checkLogableAndLevel(LogLevel.INFO)) return
        log(msg, tag = tag, level = LogLevel.INFO)
    }

    fun i(msg: String?, tr: Throwable) {
        if (!checkLogableAndLevel(LogLevel.INFO)) return
        log(msg, tr = tr, level = LogLevel.INFO)
    }


    // D
    fun d(msg: String?) {
        if (!checkLogableAndLevel(LogLevel.DEBUG)) return
        log(msg, level = LogLevel.DEBUG)
    }

    fun d(tag: String, msg: String?) {
        if (!checkLogableAndLevel(LogLevel.DEBUG)) return
        log(msg, tag = tag, level = LogLevel.DEBUG)
    }

    fun d(msg: String?, tr: Throwable) {
        if (!checkLogableAndLevel(LogLevel.DEBUG)) return
        log(msg, tr = tr, level = LogLevel.DEBUG)
    }


    // V
    fun v(msg: String?) {
        if (!checkLogableAndLevel(LogLevel.VERBOSE)) return
        log(msg, level = LogLevel.VERBOSE)
    }

    fun v(tag: String, msg: String?) {
        if (!checkLogableAndLevel(LogLevel.VERBOSE)) return
        log(msg, tag = tag, level = LogLevel.VERBOSE)
    }

    fun v(msg: String?, tr: Throwable) {
        if (!checkLogableAndLevel(LogLevel.VERBOSE)) return
        log(msg, tr = tr, level = LogLevel.VERBOSE)
    }

    private fun checkLogableAndLevel(level: LogLevel): Boolean {
        return if (logable) level.value >= logLevel.value else false
    }

    fun json(map: Map<*, *>?) {
        if (map != null) {

            try {
                val jsonObject = JSONObject(map)
                var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
            } catch (e: JSONException) {
                e("Invalid Json")
            }

        }
    }

    fun json(list: List<*>?) {
        if (list != null) {

            try {
                val jsonArray = JSONArray()

                list.map { it ->
                    val objStr = Gson().toJson(it)
                    val jsonObject = JSONObject(objStr)
                    jsonArray.put(jsonObject)
                }

                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
            } catch (e: JSONException) {
                e("Invalid Json")
            }
        }
    }

    /**
     * 将任何对象转换成json字符串进行打印
     */
    fun json(obj: Any?) {

        if (obj == null) {
            d("object is null")
            return
        }

        try {
            val objStr = Gson().toJson(obj)
            val jsonObject = JSONObject(objStr)
            var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            message = message.replace("\n".toRegex(), "\n║ ")
            val s = getMethodNames()
            println(String.format(s, message))
        } catch (e: JSONException) {
            e("Invalid Json")
        }

    }

    /**
     * 打印json字符串
     */
    fun json(json: String?) {
        var json = json

        if (json == null || json.isBlank()) {
            d("Empty/Null json content")
            return
        }

        try {
            json = json.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            e("Invalid Json: $json")
        } catch (e: JSONException) {
            e("Invalid Json: $json")
        }

    }

    fun getMethodNames(): String {
        val sElements = Thread.currentThread()
                .stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++
        val builder = StringBuilder()

        if (header.isNotBlank()) {
            builder.append(LoggerPrinter.TOP_BORDER)
                    .append("\r\n")
                    // 添加当前线程名
                    .append("║ header: $header")
                    .append("\r\n")
                    .append(LoggerPrinter.MIDDLE_BORDER)
                    .append("\r\n")
                    // 添加当前线程名
                    .append("║ " + "Thread: " + Thread.currentThread().name)
                    .append("\r\n")
                    .append(LoggerPrinter.MIDDLE_BORDER)
                    .append("\r\n")
                    // 添加类名、方法名、行数
                    .append("║ ")
                    .append(sElements[stackOffset].className)
                    .append(".")
                    .append(sElements[stackOffset].methodName)
                    .append(" ")
                    .append(" (")
                    .append(sElements[stackOffset].fileName)
                    .append(":")
                    .append(sElements[stackOffset].lineNumber)
                    .append(")")
                    .append("\r\n")
                    .append(LoggerPrinter.MIDDLE_BORDER)
                    .append("\r\n")
                    // 添加打印的日志信息
                    .append("║ ")
                    .append("%s")
                    .append("\r\n")
                    .append(LoggerPrinter.BOTTOM_BORDER)
                    .append("\r\n")
        } else {
            builder.append(LoggerPrinter.TOP_BORDER)
                    .append("\r\n")
                    // 添加当前线程名
                    .append("║ " + "Thread: " + Thread.currentThread().name)
                    .append("\r\n")
                    .append(LoggerPrinter.MIDDLE_BORDER)
                    .append("\r\n")
                    // 添加类名、方法名、行数
                    .append("║ ")
                    .append(sElements[stackOffset].className)
                    .append(".")
                    .append(sElements[stackOffset].methodName)
                    .append(" ")
                    .append(" (")
                    .append(sElements[stackOffset].fileName)
                    .append(":")
                    .append(sElements[stackOffset].lineNumber)
                    .append(")")
                    .append("\r\n")
                    .append(LoggerPrinter.MIDDLE_BORDER)
                    .append("\r\n")
                    // 添加打印的日志信息
                    .append("║ ")
                    .append("%s")
                    .append("\r\n")
                    .append(LoggerPrinter.BOTTOM_BORDER)
                    .append("\r\n")
        }

        return builder.toString()
    }

    private fun log(msg: String?, tag: String? = TAG, tr: Throwable? = null, level: LogLevel = LogLevel.DEBUG) {
        if (LogLevel.ERROR.value > logLevel.value) return

        msg ?: return
        with(msg) {
            if (isBlank()) return

            var formatMsg = msg
            if (contains("\n")) formatMsg = msg.replace("\n".toRegex(), "\n║ ")
            val s = getMethodNames()

            tr?.let {
                when (logLevel) {
                    LogLevel.VERBOSE -> Log.v(TAG, String.format(s, formatMsg), tr)
                    LogLevel.DEBUG -> Log.d(TAG, String.format(s, formatMsg), tr)
                    LogLevel.INFO -> Log.i(TAG, String.format(s, formatMsg), tr)
                    LogLevel.WARN -> Log.w(TAG, String.format(s, formatMsg), tr)
                    LogLevel.ERROR -> Log.e(TAG, String.format(s, formatMsg), tr)
                }
            } ?: when (logLevel) {
                LogLevel.VERBOSE -> Log.v(TAG, String.format(s, formatMsg))
                LogLevel.DEBUG -> Log.d(TAG, String.format(s, formatMsg))
                LogLevel.INFO -> Log.i(TAG, String.format(s, formatMsg))
                LogLevel.WARN -> Log.w(TAG, String.format(s, formatMsg))
                LogLevel.ERROR -> Log.e(TAG, String.format(s, formatMsg))
            }
        }
    }
}

enum class LogLevel {
    ERROR {
        override val value: Int
            get() = 0
    },
    WARN {
        override val value: Int
            get() = 1
    },
    INFO {
        override val value: Int
            get() = 2
    },
    DEBUG {
        override val value: Int
            get() = 3
    },
    VERBOSE {
        override val value: Int
            get() = 4
    };

    abstract val value: Int
}