package com.cnpeng.z_utils

import android.content.Context
import android.widget.Toast

/**
 * 作者：CnPeng
 * 时间：2018/12/10
 * 功用：
 * 其他：工具类文件
 */

class ExtendsUtils {

    /**
     * CnPeng 2018/12/10 9:44 AM
     * 功用：为Context类扩展Toast工具方法
     * 说明：
     * -1、依据kotlin中默认参数的使用说明，如果只是弹一个短时Toast，直接传入msg即可；如果弹长Toast，则传递第二个参数
     *
     * 哈哈哈哈，其实Anko中已经扩展了这个函数！！
     */
    public fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}