package com.cnpeng.android2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat.setFactory2
import com.cnpeng.android2.a_book1.BookOneActivity
import com.cnpeng.android2.b_work.WorkDemoActivity
import com.cnpeng.android2.d_mine.MyDemoActivity
import com.cnpeng.android2.utils.TripleLibInitUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

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

        cusViewByDelegate()

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

        //CnPeng 2019/1/8 3:54 PM 统一管理三方库的初始化
        TripleLibInitUtils(this).initTripleLib()

        initClickListener()
    }

    /**
     * CnPeng 2019/1/14 8:49 PM
     * 功用：在调用setContentView之前个性化view的外观
     * 说明：该段内容参考：https://juejin.im/post/5bcd6f1551882577e71c8c88?utm_source=gold_browser_extension
     *
     * 根据上面链接说的 https://juejin.im/post/5b9682ebe51d450e543e3495 中所谓不再定义shape/slector文件，就是利用了
     * 下面的原理，遍历了attrs，如果是自定义的attr，则直接通过代码编写 shape/selector 饭后设置给对应的view ，这样就减少了
     * shape/selector 的编写，也减少了自定义view的数量
     */
    private fun cusViewByDelegate() {
        setFactory2(LayoutInflater.from(this), object : LayoutInflater.Factory2 {
            override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
                val delegate = delegate
                val view = delegate.createView(parent, name, context!!, attrs!!)

                if (null != view && view is TextView) {
                    //CnPeng 2019/1/14 8:36 PM 此处做个性化处理
                    view.setTextColor(Color.WHITE)
                }

                //CnPeng 2019/1/14 8:47 PM 也可以根据配置的属性动态改变view的展示外观，此时需要在 attrs.xml 中先声明属性，然后在xml 中引用
                //   int n = attrs.getAttributeCount();
                //   for (int i = 0; i < n; i++) {
                //       Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                //   }

                return view
            }

            override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
                return onCreateView(null, name, context, attrs)
            }
        });
    }

    private fun initClickListener() {
        tv_mineDemo.setOnClickListener(this)
        tv_workDemo.setOnClickListener(this)
        tv_bookDemo1.setOnClickListener(this)
        tv_blogDemo.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val viewID = v?.id

        when (viewID) {
            R.id.tv_mineDemo -> startActivity<MyDemoActivity>()
            R.id.tv_workDemo -> startActivity<WorkDemoActivity>()
            R.id.tv_bookDemo1 -> startActivity<BookOneActivity>()
            else -> Toast.makeText(mActivity, "暂无内容", Toast.LENGTH_SHORT).show()
        }
    }
}
