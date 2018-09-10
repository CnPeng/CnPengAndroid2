package com.cnpeng.android2.b02_bottomPop

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_bottom_pop.*

/**
 * 作者：CnPeng
 * 时间：2018/9/7
 * 功用：页面底部弹出的Pop,弹出和收起时具有相应的动画效果
 * 其他：
 */
class BottomPopActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mActicity: BottomPopActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_pop)

        tv_showBottomPop.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val viewId = v?.id
        when (viewId) {
            R.id.tv_showBottomPop -> showBottomPopWithAnim()
        }
    }

    /**
     * 作者：CnPeng
     * 时间：2018/9/7 上午11:00
     * 功用：从页面底部弹出pop,并且展示动画效果
     * 说明：
     */
    private fun showBottomPopWithAnim() {

        val popView = layoutInflater.inflate(R.layout.pop_bottom, null)

        //        val popWidth = getScreenWidth() * 4 / 5
        val popWidth = getScreenWidth()
        //        val popHeight = getScreenHeight() * 3 / 10
        val popHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        val bottomPop = PopupWindow(popView, popWidth, popHeight)

        bottomPop.setBackgroundDrawable(resources.getDrawable(R.drawable.popupwindow_background))
        bottomPop.isOutsideTouchable = true
        bottomPop.animationStyle = R.style.style_bottomPop

        bottomPop.showAtLocation(ll_parent, Gravity.BOTTOM, 0, 100);
    }


    private fun getScreenHeight(): Int {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        return point.y
    }

    /**
     * 作者：CnPeng
     * 时间：2018/9/7 上午11:06
     * 功用：获取屏幕宽度
     * 说明：
     * // TODO: CnPeng 2018/9/7 上午11:15 查看一下kotlin中一个方法返回多个值的知识点
     */
    private fun getScreenWidth(): Int {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        return point.x
    }
}