package com.cnpeng.android2.a_book1.chapter7

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cnpeng.android2.R
import com.cnpeng.android2.a_book1.chapter7.adapter.VpActAdapter
import com.cnpeng.android2.a_book1.chapter7.bean.AlipayBaseFuncItem
import kotlinx.android.synthetic.main.activity_view_pager.*
import org.jetbrains.anko.toast
import java.io.File

class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        initViewPager()
    }

    private fun initViewPager() {
        vp_vpAct.currentItem = 0
        vp_vpAct.adapter = VpActAdapter(AlipayBaseFuncItem.funcItemList, this)

        vp_vpAct.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                toast("当前选中的页面标题为：${AlipayBaseFuncItem.funcItemList[position].title}")
            }
        })

        pts_vpTest.tabIndicatorColor = resources.getColor(R.color.colorAccent)
        pts_vpTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        pts_vpTest.setTextColor(Color.BLUE)
    }
}
