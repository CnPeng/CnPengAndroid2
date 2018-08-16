package com.cnpeng.android2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cnpeng.android2.a01_chips.ChipActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mActivity = this

        initClickListener()
    }

    private fun initClickListener() {
        tv_chip.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val viewID = v?.id

        when (viewID) {
            R.id.tv_chip -> intent.setClass(mActivity, ChipActivity::class.java)

        }
        startActivity(intent)
    }

}
