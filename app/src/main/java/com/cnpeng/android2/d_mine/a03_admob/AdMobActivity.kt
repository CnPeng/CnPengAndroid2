package com.cnpeng.android2.d_mine.a03_admob

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import kotlinx.android.synthetic.main.activity_ad_mob.*

class AdMobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_mob)

        val adRequest = AdRequest.Builder().build()

//        val adSize = AdSize(1080, 200)
//        adView.adSize = adSize

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
}
