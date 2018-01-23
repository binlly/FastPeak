package com.binlly.gankee.base

import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Build.VERSION
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.binlly.gankee.base.logger.Logger
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException


/**
 * Created by yy on 2017/8/31.
 */

const val NETWORKTYPE_WIFI = 0
const val NETWORKTYPE_2G = 1
const val NETWORKTYPE_3G = 2
const val NETWORKTYPE_4G = 3
const val NETWORKTYPE_UNKNOWN = 4

object Device {

    private val logger = Logger()

    init {
        logger.init(this::class.java)
    }

    fun getIMSI(context: Context?): String {
        return if (context == null) {
            ""
        } else {
            var imsi: String? = ""

            try {
                val e = context.getSystemService("phone") as TelephonyManager
                imsi = e.subscriberId
            } catch (var3: Exception) {
                logger.e("get IMSI error: " + var3)
            }

            if (imsi == null) "" else imsi
        }
    }

    fun getLocalMacAddress(context: Context?): String {
        if (context == null) {
            return ""
        } else {
            var mac = ""

            try {
                val e = context.getSystemService("wifi") as WifiManager
                val info = e.connectionInfo
                val result = info.macAddress
                mac = result ?: "00:00:00:00:00:00"
                mac = mac.replace("\u0000".toRegex(), "")
                mac = mac.replace("null".toRegex(), "")
            } catch (var5: Exception) {
                logger.e("get local mac error " + var5)
            }

            return if (TextUtils.isEmpty(mac)) "00:00:00:00:00:00" else mac
        }
    }

    fun getAPIV(context: Context?): String {
        if (context == null) {
            logger.e("context=null return APIV 0")
            return "0"
        } else {
            val manager = context.getPackageManager()

            try {
                val e = manager.getApplicationInfo(context.getPackageName(), 128)
                val metaData = e.metaData
                if (metaData != null) {
                    val apiv = metaData.getInt("APIV").toString()
                    return if (!TextUtils.isEmpty(apiv)) apiv else "0"
                }
            } catch (var5: Exception) {
                logger.e("obtain app APIV error", var5)
            }

            return "0"
        }
    }

    fun getAppVersion(context: Context?): String? {
        if (context == null) {
            return null
        } else {
            val manager = context.getPackageManager()

            try {
                val e = manager.getPackageInfo(context.getPackageName(), 0)
                return e.versionName
            } catch (var3: Exception) {
                logger.e("obtain app version error", var3)
                return null
            }
        }
    }

    fun getWifiSSID(context: Context): String {
        val wifiManager = context.getSystemService("wifi") as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.ssid
    }

    fun getWifiBSSID(context: Context): String {
        val wifiManager = context.getSystemService("wifi") as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.bssid
    }

    fun getAppStatus(context: Context): String {
        try {
            val e = context.getSystemService("activity") as ActivityManager
            val cn = (e.getRunningTasks(1)[0] as RunningTaskInfo).topActivity
            val packageName = cn.packageName
            return if (packageName == context.getPackageName()) "active" else "background"
        } catch (var4: Exception) {
            return "other"
        }
    }

    fun getNetworkType(context: Context): String {
        try {
            val networkInfo = getNetworkInfo(context)
            if (networkInfo != null && (networkInfo.isAvailable || networkInfo.isConnected)) {
                val type = getNetworkType(context, networkInfo)
                return getNetwrokTypeStr(type)
            }
        } catch (var31: Exception) {
            logger.e("check network error " + var31)
        }

        return "UNKNOWN"
    }

    fun getIpAddress(context: Context): String {
        val wifiManager = context.getSystemService("wifi") as WifiManager
        return if (wifiManager.isWifiEnabled) getWifiIp(wifiManager) else getLocalIp()
    }

    fun getDeviceBrand(): String {
        return Build.BRAND.replace(" ".toRegex(), "_")
    }

    fun getDeviceMid(): String {
        return Build.MODEL.replace(" ".toRegex(), "_")
    }

    fun getNetSubType(context: Context?): String {
        val info = getNetworkInfo(context)
        if (info != null && context != null) {
            val subType = info.subtype
            var type = "unknown"
            when (subType) {
                1 -> type = "GPRS"
                2 -> type = "EDGE"
                3 -> type = "UMTS"
                4 -> type = "CDMA"
                5, 6 -> type = "EVDO"
                8 -> type = "HSDPA"
                9 -> type = "HSUPA"
                10 -> type = "HSPA"
                13 -> type = "LTE"
            }

            return type
        } else {
            return "unknown"
        }
    }

    fun getICCID(context: Context?): String? {
        if (context == null) {
            return null
        } else {
            val builder = StringBuilder()

            try {
                if (VERSION.SDK_INT >= 22) {
                    val e = SubscriptionManager.from(context)
                    val infos = e.activeSubscriptionInfoList
                    if (infos != null) {
                        val var4 = infos.iterator()

                        while (var4.hasNext()) {
                            val info = var4.next() as SubscriptionInfo
                            builder.append(info.iccId).append(",")
                        }

                        builder.deleteCharAt(builder.length - 1)
                    }
                } else {
                    val e1 = context.getSystemService("phone") as TelephonyManager
                    builder.append(e1.simSerialNumber)
                }
            } catch (var6: Exception) {
                logger.e("getICCID error" + var6)
            }

            return builder.toString()
        }
    }

    private fun getNetworkInfo(context: Context?): NetworkInfo? {
        context ?: return null
        val connectMgr = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectMgr.activeNetworkInfo
    }

    private fun getWifiIp(wifiManager: WifiManager): String {
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return intToIp(ipAddress)
    }

    private fun intToIp(i: Int): String {
        return (i and 255).toString() + "." + (i shr 8 and 255) + "." + (i shr 16 and 255) + "." + (i shr 24 and 255)
    }

    private fun getLocalIp(): String {
        try {
            val en = NetworkInterface.getNetworkInterfaces()

            while (en.hasMoreElements()) {
                val networkInterface = en.nextElement() as NetworkInterface
                val enumIpAddr = networkInterface.getInetAddresses()

                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement() as InetAddress
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (var4: SocketException) {
        }

        return "0.0.0.0"
    }

    private fun getNetworkType(context: Context?, info: NetworkInfo?): Int {

        var type = NETWORKTYPE_UNKNOWN

        if (info == null || context == null) {
            return type
        }

        if (info.type == ConnectivityManager.TYPE_WIFI) {
            type = NETWORKTYPE_WIFI
        } else if (info.type == ConnectivityManager.TYPE_MOBILE) {

            type = NETWORKTYPE_3G

            val subType = info.subtype

            when (subType) {
                TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A -> type = NETWORKTYPE_3G
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA -> type = NETWORKTYPE_2G
                TelephonyManager.NETWORK_TYPE_LTE -> type = NETWORKTYPE_4G
                else -> {
                }
            }

            logger.d("network type：" + subType)
        }

        return type
    }

    private fun getNetwrokTypeStr(networkType: Int): String {

        var result = "UNKNOWN"

        when (networkType) {
            NETWORKTYPE_2G -> result = "2G"
            NETWORKTYPE_3G -> result = "3G"
            NETWORKTYPE_4G -> result = "4G"
            NETWORKTYPE_WIFI -> result = "WIFI"
            else -> {
            }
        }

        return result
    }

    class AppStatus {
        companion object {
            const val ACTIVE = "active"
            const val BACKGROUND = "background"
            const val OTHER = "other"
        }
    }
}