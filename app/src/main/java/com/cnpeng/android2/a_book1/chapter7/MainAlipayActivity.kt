package com.cnpeng.android2.a_book1.chapter7

import LifeItem
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.a05_extract_rv_adapter.CommonRvAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_scroll_alipay.*
import kotlinx.android.synthetic.main.item_life.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

/**
 * CnPeng 2019/1/2 2:51 PM
 * 功用：仿支付宝首页顶部的滚动和折叠
 * 说明：
 */
class MainAlipayActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_alipay)

        //给控件abl_bar注册一个位置偏移的监听器
        abl_bar.addOnOffsetChangedListener(this)

        rv_content.layoutManager = GridLayoutManager(this, 4)

        rv_content.adapter = CommonRvAdapter(R.layout.item_life, LifeItem.default
        ) { view, item ->
            view.iv_pic.setImageResource(item.pic_id)
            view.tv_title.text = item.title
        }
    }

    /**
     * CnPeng 2019/1/2 2:47 PM
     * 功用：重写AppBarLayout的滚动监听
     * 说明：以AppBarLayout滚动范围的1/2为基准，超过1/2时完全隐层一个，并逐步显示另一个。
     */
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

        val offsetAbs = Math.abs(verticalOffset)
        val totalScrollRange = appBarLayout.totalScrollRange
        val halfTotal = totalScrollRange / 2

        //标题栏的透明度渐变
        val titleAlpha: Float = (offsetAbs * 1.0f / halfTotal)
        AnkoLogger("AppBarLayout滚动信息").error {
            "verticalOffset:$verticalOffset,totalScrollRange:$totalScrollRange,titleAlpha:$titleAlpha"
        }
        //向上滚时，alpha从0-2，向下滚时从2-0
        if (titleAlpha in 0.0..1.0) {
            //渐渐不可见——0为完全透明，不可见；1为完全不透明可见
            tl_expand.alpha = 1 - titleAlpha
            tl_expand.visibility = View.VISIBLE
            tl_collapse.visibility = View.GONE
        } else {
            tl_collapse.visibility = View.VISIBLE
            tl_expand.visibility = View.GONE
            tl_collapse.alpha = titleAlpha - 1
        }

        //默认四大基本常用功能的透明度变化
        base_func.alpha = 1 - offsetAbs * 1.0f / totalScrollRange
    }
}
