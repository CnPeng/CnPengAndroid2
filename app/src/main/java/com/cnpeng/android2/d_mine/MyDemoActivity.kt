package com.cnpeng.android2.d_mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.a01_chips.ChipActivity
import com.cnpeng.android2.d_mine.a02_flexboxlayout.FlexboxActivity
import com.cnpeng.android2.d_mine.a03_admob.AdMobActivity
import kotlinx.android.synthetic.main.activity_my_demo.*

class MyDemoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mActivity: MyDemoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_demo)

        initCommonVariables()
        initClickEvent()
    }

    private fun initCommonVariables() {
        mActivity = this
    }

    private fun initClickEvent() {
        tv_chip.setOnClickListener(this)
        tv_flexbox.setOnClickListener(this)
        tv_adMob.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val id = v?.id

        when (id) {
            R.id.tv_chip -> intent.setClass(mActivity, ChipActivity::class.java)
            R.id.tv_flexbox -> intent.setClass(mActivity, FlexboxActivity::class.java)
            R.id.tv_adMob -> intent.setClass(mActivity, AdMobActivity::class.java)
        }
        startActivity(intent)
    }
}
