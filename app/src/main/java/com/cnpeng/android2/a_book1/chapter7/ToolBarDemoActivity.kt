package com.cnpeng.android2.a_book1.chapter7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_tool_bar_demo.*

class ToolBarDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_bar_demo)

        setSupportActionBar(toolbar2)

        toolBarBaseUse()
    }

    private fun toolBarBaseUse() {
        toolbar2.setNavigationOnClickListener {
            //点击 navigationIcon 后: 隐藏toolBar并关闭当前页面
            supportActionBar?.hide()
            finish()
        }
    }
}
