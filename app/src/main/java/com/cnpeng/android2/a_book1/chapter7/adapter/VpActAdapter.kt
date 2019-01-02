package com.cnpeng.android2.a_book1.chapter7.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.cnpeng.android2.a_book1.chapter7.bean.AlipayBaseFuncItem
import org.jetbrains.anko.dip

/**
 * 作者：CnPeng
 * 时间：2019/1/2
 * 功用：
 * 其他：
 */
class VpActAdapter(private var funcList: MutableList<AlipayBaseFuncItem>, val context: Context) : PagerAdapter() {
    private val itemViews = mutableListOf<View>()

    init {
        //􏱎􏱗􏱰􏳿􏰺􏰶􏰲􏰭􏰐􏰰􏰾􏰬􏰒􏰀􏱏􏱅􏰽􏰎􏰛􏱈􏱂􏱳􏱁􏱪􏱌􏱜􏱕􏱲􏰚􏳇􏰑􏰹􏱍􏱙􏰅 􏱁􏰣􏱪􏱌􏰦􏱲􏰚􏳇􏰷􏰏􏱃􏰅主构造函数只初始化被声明的成员，其他未从主构造中声明的变量可以放在 init 中初始化
        for (it in funcList) {
            val itemView = ImageView(context)
            itemView.setImageResource(it.picID)
            itemView.setBackgroundColor(Color.BLUE)
            itemView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            itemViews.add(itemView)
        }
    }

    //判断页面是否已经加入到适配器
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = itemViews.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(itemViews[position])
        return itemViews[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //此处必须直接销毁view，如果使用container.removeViewAt(position)会出错
        container.removeView(itemViews[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return funcList[position].title
    }
}