package com.cnpeng.android2.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R

open class BaseActivity : AppCompatActivity() {

    var mIsNetAvailable: Boolean = true
    var mNetTypeName: String = NetType.WIFI.name
    var mNetReceiver: NetWorkChangeReceiver = NetWorkChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)

        initNetChangeReceiver()
        mIsNetAvailable = isNetAvailable()
        mNetTypeName = getNetTypeName()
    }

    private fun initNetChangeReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(mNetReceiver, intentFilter)
    }

    inner class NetWorkChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            mIsNetAvailable = isNetAvailable()
            mNetTypeName = getNetTypeName()
        }
    }

    override fun onResume() {
        super.onResume()
        mIsNetAvailable = isNetAvailable()
        mNetTypeName = getNetTypeName()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mNetReceiver)
    }
}
