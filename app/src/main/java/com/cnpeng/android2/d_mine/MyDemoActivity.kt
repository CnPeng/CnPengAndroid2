package com.cnpeng.android2.d_mine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.a01_chips.ChipActivity
import com.cnpeng.android2.d_mine.a02_flexboxlayout.FlexboxActivity
import com.cnpeng.android2.d_mine.a03_admob.AdMobActivity
import com.cnpeng.android2.d_mine.a04_actlanuchmode.ClearTopFlagActivity
import com.cnpeng.android2.d_mine.a05_extract_rv_adapter.ExtractRvAdapterActivity
import com.cnpeng.android2.d_mine.a06_sound_wirte.SoundWriteActivity
import kotlinx.android.synthetic.main.activity_my_demo.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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
        tv_clearTop.setOnClickListener(this)
        tv_extractRvAct.setOnClickListener(this)
        tv_xfyun.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id

        when (id) {
            R.id.tv_chip ->
                startActivity<ChipActivity>()
            R.id.tv_flexbox ->
                startActivity<FlexboxActivity>()
            R.id.tv_adMob ->
                startActivity<AdMobActivity>()
            R.id.tv_clearTop -> {
                val intent = intentFor<ClearTopFlagActivity>()
                startActivity(intent.clearTop())
            }
            R.id.tv_extractRvAct ->
                startActivity<ExtractRvAdapterActivity>()
            R.id.tv_xfyun -> {
                startActivity<SoundWriteActivity>()
            }

            else -> toast("暂未定义点击事件")
        }
    }
}
