package com.cnpeng.android2.b_work

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.b_work.b01_maxLines_tv.MaxLinesTvActivity
import com.cnpeng.android2.b_work.b02_bottom_pop.BottomPopActivity
import com.cnpeng.android2.b_work.b03_view_flipper.ViewFlipperActivity
import com.cnpeng.android2.b_work.b04_flow_layout.FlowImplActivity
import com.cnpeng.android2.b_work.b05_open_lamp.OpenOrCloseLampActivity
import kotlinx.android.synthetic.main.activity_work_demo.*
import org.jetbrains.anko.startActivity

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
        tv_lampSwitch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.tv_maxLinesAct -> startActivity<MaxLinesTvActivity>()
            R.id.tv_bottomPop -> startActivity<BottomPopActivity>()
            R.id.tv_flipper -> startActivity<ViewFlipperActivity>()
            R.id.tv_flow -> startActivity<FlowImplActivity>()
            R.id.tv_lampSwitch -> startActivity<OpenOrCloseLampActivity>()
        }
    }
}
