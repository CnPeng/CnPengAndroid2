package com.cnpeng.android2.utils

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.iflytek.cloud.Setting
import com.iflytek.cloud.SpeechUtility


/**
 * CnPeng 2019/1/8 11:45 AM
 * 功用：三方库的初始化工具类
 * 说明：
 */
class TripleLibInitUtils(var context: Context) {

    fun initTripleLib() {
        //CnPeng 2019/1/8 11:47 AM 讯飞语音
        SpeechUtility.createUtility(context, "appid=59c327cb");

        //CnPeng 2018/12/21 5:25 PM 初始化adMob，示例id: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(context, "ca-app-pub-8994842234959408~1322966018");
    }
}
