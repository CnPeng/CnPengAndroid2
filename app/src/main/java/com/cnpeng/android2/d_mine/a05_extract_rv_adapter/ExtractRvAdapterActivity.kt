package com.cnpeng.android2.d_mine.a05_extract_rv_adapter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_extract_rv_adapter.*
import kotlinx.android.synthetic.main.item_extract.view.*

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

        //rv_extract.adapter = ExtractRvAdapter(itemList)

        // 当函数的最后一个参数是lambda表达式时，需要挪到小括号外面。此处可以参考《KotlinForAndroid读书笔记》中的 "简化setOnCliclListener"
        // rv_extract.adapter = CommonRvAdapter<String>(R.layout.item_extract,
        //         itemList,
        //         { view: View, s: String ->
        //             initRvItem(view, s)
        //         }
        // )

        //构造CommonRvAdapter时，省略了其后的类型<String>
        rv_extract.adapter = CommonRvAdapter(R.layout.item_extract, itemList
        ) { view: View, s: String ->
            initRvItem(view, s)
        }
    }

    private fun initRvItem(view: View, s: String) {

        //因为 CommonRvAdapter 中的嵌套类 BaseItemHolder 实现了 LayoutContainer 接口，所以此处可以使用 view.子View的ID 获取子View对象
        view.tv_extractItemDesc.text = s

        // TODO: CnPeng 2018/12/28 6:03 PM 条目的点击、长按，都可以从这里实现。
    }
}
