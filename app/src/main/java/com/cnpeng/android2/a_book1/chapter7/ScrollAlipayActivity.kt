package com.cnpeng.android2.a_book1.chapter7

import LifeItem
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.a05_extract_rv_adapter.CommonRvAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_scroll_alipay.*
import kotlinx.android.synthetic.main.item_life.view.*
import kotlinx.android.synthetic.main.life_pay.*
import kotlinx.android.synthetic.main.toolbar_collapse.*
import kotlinx.android.synthetic.main.toolbar_expand.*

/**
 * Created by ouyangshen on 2017/9/3.
 */
class ScrollAlipayActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private var mMaskColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_alipay)

        mMaskColor = resources.getColor(R.color.blue_dark)
        //给控件abl_bar注册一个位置偏移的监听器
        abl_bar.addOnOffsetChangedListener(this)

        rv_content.layoutManager = GridLayoutManager(this, 4)

        rv_content.adapter = CommonRvAdapter(R.layout.item_life, LifeItem.default
        ) { view, item ->
            view.iv_pic.setImageResource(item.pic_id)
            view.tv_title.text = item.title
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

        val offset = Math.abs(verticalOffset)
        val total = appBarLayout.totalScrollRange

        val alphaOut = if (200 - offset < 0) 0 else 200 - offset
        //计算淡入时候的遮罩透明度
        val maskColorIn = Color.argb(offset, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))
        //工具栏下方的频道布局要加速淡入或者淡出
        val maskColorInDouble = Color.argb(offset * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))
        //计算淡出时候的遮罩透明度
        val maskColorOut = Color.argb(alphaOut * 3, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))

        if (offset <= total * 0.45) { //偏移量小于一半，则显示展开时候的工具栏
            tl_expand.visibility = View.VISIBLE
            tl_collapse.visibility = View.GONE
            v_expand_mask.setBackgroundColor(maskColorInDouble)
        } else { //偏移量大于一半，则显示缩小时候的工具栏
            tl_expand.visibility = View.GONE
            tl_collapse.visibility = View.VISIBLE
            v_collapse_mask.setBackgroundColor(maskColorOut)
        }
        v_pay_mask.setBackgroundColor(maskColorIn)
    }


}
