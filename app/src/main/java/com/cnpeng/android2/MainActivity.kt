package com.cnpeng.android2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.a01_chips.ChipActivity
import com.cnpeng.android2.b01_maxLinesTv.MaxLinesTvActivity
import com.cnpeng.android2.b02_bottomPop.BottomPopActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * CnPeng 2018/8/29 下午8:33 参考 https://juejin.im/post/5b50b017f265da0f7b2f649c 启动页优化，防止白屏
         *
         * 其思路是，在 Manifest 中为该activity配置一个设置了 windowBackground 的 theme, 这样在启动后完成绘制前的
         * 这一段时间就会展示这个 windowBackground.当 进入activity后，再重置theme
         */
        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_main)
        mActivity = this

        initClickListener()
    }

    private fun initClickListener() {
        tv_chip.setOnClickListener(this)
        tv_maxLinesAct.setOnClickListener(this)
        tv_bottomPop.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val viewID = v?.id

        when (viewID) {
            R.id.tv_chip -> intent.setClass(mActivity, ChipActivity::class.java)
            R.id.tv_maxLinesAct -> intent.setClass(mActivity, MaxLinesTvActivity::class.java)
            R.id.tv_bottomPop -> intent.setClass(mActivity, BottomPopActivity::class.java)
        }
        startActivity(intent)
    }

}
