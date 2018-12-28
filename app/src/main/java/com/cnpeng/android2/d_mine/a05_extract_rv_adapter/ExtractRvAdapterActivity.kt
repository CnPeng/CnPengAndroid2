package com.cnpeng.android2.d_mine.a05_extract_rv_adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_extract_rv_adapter.*

class ExtractRvAdapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_rv_adapter)

        initRv()
    }

    private fun initRv() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_extract.layoutManager = linearLayoutManager

        val itemList = mutableListOf<String>()
        for (i in 0..15) {
            itemList.add("这是第${i}个条目")
        }

        rv_extract.adapter = ExtractRvAdapter(itemList)
    }
}
