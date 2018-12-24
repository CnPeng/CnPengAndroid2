package com.cnpeng.android2.d_mine.a03_admob

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.cnpeng.android2.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_ad_mob.*
import org.jetbrains.anko.px2dip

class AdMobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_mob)

        //CnPeng 2018/12/24 10:29 AM BANNER
        initAdView1()

        //CnPeng 2018/12/24 10:16 AM LARGE_BANNER
        initManualAdView(320, 100, R.id.adView + 1, R.id.adView)

        // CnPeng 2018/12/24 10:29 AM  300x250 MEDIUM_RECTANGLE
        initManualAdView(300, 250, R.id.adView + 2, R.id.adView + 1)

        //CnPeng 2018/12/24 10:16 AM 智能宽高 SMART_BANNER
        initManualAdView(0, 0, R.id.adView + 3, R.id.adView + 2)

        //CnPeng 2018/12/24 10:16 AM 满屏并保持6.4/1的比率——竖屏模式下，只要不超过屏幕宽度，高度可以自定义
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val screenW = point.x

        val viewW = px2dip(screenW).toInt()
        val viewH = (viewW / 6.4).toInt()
        initManualAdView(viewW, viewH, R.id.adView + 4, R.id.adView + 3)

        //CnPeng 2018/12/24 10:33 AM 宽高一致的——竖屏模式下，只要不超过屏幕宽度，高度可以自定义
        initManualAdView(viewW, viewW, R.id.adView + 5, R.id.adView + 4)

        //CnPeng 2018/12/24 10:33 AM 高大于宽的广告——竖屏模式下，只要不超过屏幕宽度，高度可以自定义
        initManualAdView(50, 100, R.id.adView + 6, R.id.adView + 5)

        //CnPeng 2018/12/24 10:38 AM 超过屏幕宽度不能展示
        initManualAdView(viewW + 10, 100, R.id.adView + 7, R.id.adView + 6)
    }

    /**
     * CnPeng 2018/12/24 9:15 AM
     * 功用：手动初始化adview
     * 说明：
     */
    private fun initManualAdView(viewW: Int, viewH: Int, viewId: Int, viewIdToCons: Int) {
        val adView2 = AdView(this)
        adView2.id = viewId
        adView2.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        adView2.adSize = if (viewW == 0 || viewH == 0) AdSize.SMART_BANNER else {
            AdSize(viewW, viewH)
        }

        val adRequest2 = AdRequest.Builder().build()

        val lpW = if (viewH > viewW) {
            -2
        } else {
            -1
        }

        val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(lpW, -2)
        layoutParams.topToBottom = viewIdToCons
        layoutParams.leftToLeft = R.id.cons_parent
        layoutParams.topMargin = 30
        cons_parent.addView(adView2, layoutParams)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adView2.foreground = resources.getDrawable(R.drawable.trans_cornor_rect5px, theme)
        }

        adView2.loadAd(adRequest2)
    }

    private fun initAdView1() {
        val adRequest = AdRequest.Builder().build()

        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

                Log.d("AdMob", "onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                Log.d("AdMob", "onAdFailedToLoad")

            }

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d("AdMob", "onAdClosed")

            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                Log.d("AdMob", "onAdLeftApplication")

            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d("AdMob", "onAdClicked")

            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d("AdMob", "onAdImpression")

            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d("AdMob", "onAdOpened")

            }
        }
    }

    override fun onDestroy() {
        val childCount = cons_parent.childCount
        for (i in 0 until childCount) {
            val adView=cons_parent.getChildAt(i)
            if (adView is AdView){
                //CnPeng 2018/12/24 2:09 PM 官方demo中有销毁的操作，所以照抄
                adView.destroy()
            }
        }

        super.onDestroy()
    }
}
