package com.cnpeng.utils

import android.content.Context
import android.hardware.camera2.CameraManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.ConnectivityManager.*

/**
 * 作者：CnPeng
 * 时间：2019/2/19
 * 功用：
 * 其他：
 */

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


val Context.cameraManager: CameraManager
    get() = getSystemService(Context.CAMERA_SERVICE) as CameraManager

val Context.networkInfo: NetworkInfo?
    get() {
        return connectivityManager.activeNetworkInfo ?: null
    }

/**
 * CnPeng 2019/2/19 22:18 AM
 * 功用：判断网络是否可用
 * 说明：
 */
fun Context.isNetAvailable(): Boolean {
    return null != networkInfo && networkInfo!!.isConnected && (isNetTypeMobile() || isNetTypeWifi())
}

/**
 * CnPeng 2019/1/9 9:13 AM
 * 功用：获取网络类型名称
 * 说明：
 * 返回值具体参考：{@link ConnectivityManager.getNetworkTypeName() }， 由于networkInfo.typename返回的太细了，所以。。。
 */
fun Context.getNetTypeName(): String {
    //    return networkInfo.typeName
    if (null == networkInfo) {
        return NetType.OTHER.name
    }

    when (networkInfo!!.type) {
        ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_MOBILE_MMS, ConnectivityManager.TYPE_MOBILE_SUPL, ConnectivityManager.TYPE_MOBILE_DUN, ConnectivityManager.TYPE_MOBILE_HIPRI -> return NetType.MOBILE.name
        ConnectivityManager.TYPE_WIFI -> return NetType.WIFI.name
        else -> return NetType.OTHER.name
    }
}

/**
 * CnPeng 2019/1/9 9:38 AM
 * 功用：是否是非WIFI网络——数据流量
 * 说明：
 */
fun Context.isNetTypeMobile(): Boolean {
    if (null == networkInfo) {
        return false
    }

    when (networkInfo!!.type) {
        ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_MOBILE_MMS, ConnectivityManager.TYPE_MOBILE_SUPL, ConnectivityManager.TYPE_MOBILE_DUN, ConnectivityManager.TYPE_MOBILE_HIPRI -> return true
        else -> return false
    }
}


/**
 * CnPeng 2019/1/9 9:38 AM
 * 功用：是否是WIFI
 * 说明：
 */
fun Context.isNetTypeWifi(): Boolean {

    return null != networkInfo && ConnectivityManager.TYPE_WIFI == networkInfo!!.type
}

/**
 * CnPeng 2019/1/9 10:10 AM
 * 功用：网络类型枚举
 * 说明：
 */
enum class NetType {
    WIFI,
    //所有的移动网络类型，包括2G、3G、4G、5G 等
    MOBILE,
    //除了WIFI、MOBILE 之外的情况，也包括无网络状态
    OTHER
}