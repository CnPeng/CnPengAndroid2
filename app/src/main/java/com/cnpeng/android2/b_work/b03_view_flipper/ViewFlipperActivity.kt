package com.cnpeng.android2.b_work.b03_view_flipper

import android.graphics.Color
import android.os.Bundle
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_view_flipper.*

/**
 * CnPeng 2018/11/30 12:14 PM
 * 功用：仿淘宝客户端中的 淘宝头条滚动广告
 * 说明：
 * - 1、
 * - 2、除了这两种方式之外，还可以考虑使用RecyclerView 或 ViewPager实现 该效果，代码省略
 */
class ViewFlipperActivity : AppCompatActivity() {
    private lateinit var mActivity: ViewFlipperActivity
    var strList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_flipper)

        initCommonVariables()
        initViewFlipper()
        initAdapterViewFlipper()
    }

    private fun initCommonVariables() {
        mActivity = this

        for (i in 1..15) {
            strList.add("这是第" + i + "个元素")
        }
    }

    private fun initAdapterViewFlipper() {

        //CnPeng 2018/11/30 2:21 PM 添加view数据
        for ((index, str) in strList.withIndex()) {
            val textView = TextView(mActivity)
            textView.text = str

            if (0 == index % 2) {
                textView.setTextColor(Color.parseColor("#FFFF0000"))
                //CnPeng 2018/11/30 1:38 PM 这种方式也可以
                //textView.setTextColor(Color.RED)
            } else {
                textView.setTextColor(ContextCompat.getColor(mActivity, R.color.c_666666))
            }
            viewFlipper.addView(textView)
        }

//        setViewFlipperAnimation()
    }

    /**
     * CnPeng 2018/11/30 2:22 PM
     * 功用：设置ViewFlipper的切换动画
     * 说明：
     */
    private fun setViewFlipperAnimation() {
        val inAnimation = TranslateAnimation(0f, 0f, 100f, 0f)
        inAnimation.duration = 1500
        inAnimation.start()

        val outAnimation = TranslateAnimation(0f, 0f, 0f, -100f)
        outAnimation.duration = 1500
        outAnimation.start()

        viewFlipper.inAnimation = inAnimation
        viewFlipper.outAnimation = outAnimation
    }

    private fun initViewFlipper() {


    }
}
