package com.cnpeng.android2.b_work.b03_view_flipper

import android.animation.ObjectAnimator
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
    var mStrList = mutableListOf<String>()

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
            mStrList.add("这是第" + i + "个元素")
        }
    }

    private fun initAdapterViewFlipper() {

        //CnPeng 2018/11/30 2:21 PM 添加view数据
        for ((index, str) in mStrList.withIndex()) {
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

        //setViewFlipperAnimation()
    }

    /**
     * CnPeng 2018/11/30 2:22 PM
     * 功用：设置ViewFlipper的切换动画
     * 说明：这是代码设置的方式，也可以从布局文件中通过 inanimation 、outanimation 属性设置
     * -1、在ViewFlipper中 DEFAULT_INTERVAL 为 3000，即 3秒。
     * -2、在执行inAnimation 和 outAnimation 时，如果两者的duration之和等于我们设置的 interval，则动画能正常展示；否则，动画不能正常展示
     * -3、可以使用动画集合，因为在设置动画时接收的对象为 Animation，而 AnimationSet 是 Animation的子类
     */
    private fun setViewFlipperAnimation() {
        val inAnimation = TranslateAnimation(0f, 0f, 100f, 0f)
        inAnimation.duration = 1500
        //CnPeng 2018/11/30 4:20 PM 此处不需要调用start。viewFlipper在展示child的时候会主动触发动画。但是，调用了在界面上也看不出特殊
        //        inAnimation.start()

        val outAnimation = TranslateAnimation(0f, 0f, 0f, -100f)
        outAnimation.duration = 1500
        //        outAnimation.start()

        viewFlipper.inAnimation = inAnimation
        viewFlipper.outAnimation = outAnimation
    }

    private fun initViewFlipper() {
        val flipperAdapter = AdapterFlipperViewAdapter(mStrList, mActivity)
        adapterViewFlipper.adapter = flipperAdapter

//        setAdapterViewFlipperAnimator()
    }


    /**
     * CnPeng 2018/11/30 4:31 PM
     * 功用：
     * 说明：
     * -1、在AdapterViewFlipper中 DEFAULT_INTERVAL 为 10000，即 10秒。
     * -2、在执行inAnimation 和 outAnimation 时，如果两者的duration之和 小于等于 我们设置的 interval，则动画能正常展示；否则，动画不能正常展示
     * -3、不能使用动画集合，因为设置动画时只接收 ObjectAnimator及其子类，而 AnimatorSet 是其叔叔
     */
    private fun setAdapterViewFlipperAnimator() {
        //CnPeng 2018/11/30 4:14 PM 注意，下面这一行是从Java代码转义过来的，在Java环境下，最后一个参数我们会手动构造一个float[] ,但在kotlin中会报错
        // val animator = ObjectAnimator.ofFloat(adapterViewFlipper, "translationY", arrayOf(100f, 0f))

        //CnPeng 2018/11/30 4:15 PM 最后一个参数是可变数组，我们不需要构造array，直接写array的值即可。
        val inAnimator = ObjectAnimator.ofFloat(adapterViewFlipper, "translationY", 100f, 0f)
        inAnimator.duration = 1500

        //CnPeng 2018/11/30 4:20 PM 此处不需要调用start。viewFlipper在展示child的时候会主动触发动画。如果调用了，执行动画的将是flipper本身
        //        inAnimator.start()

        val outAnimator = ObjectAnimator.ofFloat(adapterViewFlipper, "translationY", 0f, -100f)
        outAnimator.duration = 1500
        //        outAnimator.start()

        adapterViewFlipper.inAnimation = inAnimator
        adapterViewFlipper.outAnimation = outAnimator
    }
}
