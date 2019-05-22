package com.cnpeng.android2.a_book1.chapter7.bean

import com.cnpeng.android2.R

/**
 * 作者：CnPeng
 * 时间：2019/1/2
 * 功用：
 * 其他：
 */
data class AlipayBaseFuncItem(var title: String, var picID: Int) {

    companion object {
        val funcItemList: MutableList<AlipayBaseFuncItem>
            get() {
                val defaultList = mutableListOf<AlipayBaseFuncItem>()
                defaultList.add(AlipayBaseFuncItem("扫一扫", R.drawable.big_scan))
                defaultList.add(AlipayBaseFuncItem("付款", R.drawable.big_pay))
                defaultList.add(AlipayBaseFuncItem("聊天", R.drawable.big_chat))
                defaultList.add(AlipayBaseFuncItem("支付码", R.drawable.big_qrcode))
                return defaultList
            }
    }
}