package com.cnpeng.android2.a_book1.chapter7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.a05_extract_rv_adapter.CommonRvAdapter
import kotlinx.android.synthetic.main.activity_app_bar_layout.*
import kotlinx.android.synthetic.main.item_extract.view.*

class AppBarLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_layout)

        rv_appBarTest.layoutManager = LinearLayoutManager(this)

        val mutableList = mutableListOf<String>()
        for (i in 0..15) {
            mutableList.add("${i}——ToolBar+AppBarLayout+Rv的滚动")
        }

        rv_appBarTest.adapter = CommonRvAdapter<String>(R.layout.item_extract, mutableList) { view, str ->
            view.tv_extractItemDesc.text = str
        }
    }
}
