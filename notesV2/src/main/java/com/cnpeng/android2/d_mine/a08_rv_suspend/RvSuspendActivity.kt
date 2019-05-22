package com.cnpeng.android2.d_mine.a08_rv_suspend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R

/**
 * CnPeng 2019-05-22 17:04
 * 功用：实现 RecyclerView 的条目悬浮
 * 说明：
 *
 * 参考链接：
 *
 * [Android轻松实现RecyclerView悬浮条](https://www.jianshu.com/p/fe69a53502ab)
 * [android上使用RecyclerView实现顶部悬浮标题效果的Sticky Title View](https://blog.csdn.net/hua631150873/article/details/76048865)
 * [RecyclerView实现顶部悬浮、字母排序、过滤搜索最优雅的方式](https://blog.csdn.net/silenceoo/article/details/77839683)
 */
class RvSuspendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_suspend)
    }
}
