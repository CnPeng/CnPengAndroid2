package com.cnpeng.android2.b_work.b03_view_flipper

import android.graphics.Color
import android.os.Bundle
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
    }

    private fun initViewFlipper() {


    }
}
