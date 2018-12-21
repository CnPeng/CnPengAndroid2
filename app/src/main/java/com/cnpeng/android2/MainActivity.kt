package com.cnpeng.android2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.b_work.WorkDemoActivity
import com.cnpeng.android2.d_mine.MyDemoActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

/**
 * CnPeng 2018/8/16 10:01 AM
 * 功用：主页
 * 说明：
 * - 1、a系列包名为读书笔记
 * - 2、b系列为工作用DEMO
 * - 3、c系列为零散的博客阅读笔记
 * - 4、d系列为自主学习的代码内容
 */
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

        //CnPeng 2018/12/21 5:25 PM 初始化adMob，示例id: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-8994842234959408~1322966018");

        initClickListener()
    }

    private fun initClickListener() {
        tv_mineDemo.setOnClickListener(this)
        tv_workDemo.setOnClickListener(this)
        tv_bookDemo.setOnClickListener(this)
        tv_blogDemo.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val viewID = v?.id

        when (viewID) {
            R.id.tv_mineDemo -> intent.setClass(mActivity, MyDemoActivity::class.java)
            R.id.tv_workDemo -> intent.setClass(mActivity, WorkDemoActivity::class.java)
            // TODO: CnPeng 2018/11/30 10:51 AM bookDemo 和 BlogDemo还么有增加跳转事件
            R.id.tv_bookDemo -> Toast.makeText(mActivity, "暂无内容", Toast.LENGTH_SHORT).show()
            R.id.tv_blogDemo -> Toast.makeText(mActivity, "暂无内容", Toast.LENGTH_SHORT).show()
        }

        if (null != intent.component) {
            startActivity(intent)
        }
    }
}
