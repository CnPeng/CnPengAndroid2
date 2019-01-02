package com.cnpeng.android2.a_book1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.a_book1.chapter7.*
import kotlinx.android.synthetic.main.activity_book_one.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * CnPeng 2018/12/28 7:45 PM
 * 功用：
 * 说明：该包中的内容对应《Kotlin从零到精通Android开发》
 */
class BookOneActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_one)

        initClickEvent()
    }

    private fun initClickEvent() {
        tv_corLayout.setOnClickListener(this)
        tv_toolBar.setOnClickListener(this)
        tv_appBarLayout.setOnClickListener(this)
        tv_collapsing.setOnClickListener(this)
        tv_aliPay.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        val viewID = v?.id ?: 0

        when (viewID) {
            R.id.tv_corLayout -> startActivity<CoordinatorLayoutTestActivity>()
            R.id.tv_toolBar -> startActivity<ToolBarDemoActivity>()
            R.id.tv_appBarLayout -> startActivity<AppBarLayoutActivity>()
            R.id.tv_collapsing -> startActivity<CollapsingToolBarActivity>()
            R.id.tv_aliPay -> startActivity<ScrollAlipayActivity>()
            else -> toast("暂未实现点击事件")
        }
    }
}
