package com.cnpeng.android2.b_work

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.b_work.b01_maxLines_tv.MaxLinesTvActivity
import com.cnpeng.android2.b_work.b02_bottom_pop.BottomPopActivity
import com.cnpeng.android2.b_work.b03_view_flipper.ViewFlipperActivity
import com.cnpeng.android2.b_work.b04_flow_layout.FlowImplActivity
import kotlinx.android.synthetic.main.activity_work_demo.*

class WorkDemoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mActivity: WorkDemoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_demo)

        initCommonVariables()
        initClickEvent()

    }

    private fun initCommonVariables() {
        mActivity = this

    }

    private fun initClickEvent() {
        tv_maxLinesAct.setOnClickListener(this)
        tv_bottomPop.setOnClickListener(this)
        tv_flipper.setOnClickListener(this)
        tv_flow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val id = v?.id
        when (id) {
            R.id.tv_maxLinesAct -> intent.setClass(mActivity, MaxLinesTvActivity::class.java)
            R.id.tv_bottomPop -> intent.setClass(mActivity, BottomPopActivity::class.java)
            R.id.tv_flipper -> intent.setClass(mActivity, ViewFlipperActivity::class.java)
            R.id.tv_flow -> intent.setClass(mActivity, FlowImplActivity::class.java)
        }
        startActivity(intent)
    }
}
