package com.cnpeng.android2.d_mine.a04_actlanuchmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.d_mine.MyDemoActivity
import kotlinx.android.synthetic.main.activity_clear_top_flag.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class ClearTopFlagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clear_top_flag)

        //当前act实例所在任务栈的栈ID
        var taskID = taskId

        tv_actID.text = " onCreate ——页面ID：${this.toString()}"

        tv_backToDemoAct.setOnClickListener {

            when (intent.flags) {
                Intent.FLAG_ACTIVITY_CLEAR_TOP -> {
                    //使用Anko中提供的跳转函数。跳转时携带数据，并制定目标页面的启动模式
                    //val ankoIntent = intentFor<MyDemoActivity>(Pair("KeyName", "keyValue"))
                    //startActivity(ankoIntent.newTask())
                    //startActivityForResult<MyDemoActivity>(0)
                    startActivityForResult<MyDemoActivity>(0, Pair("KeyName", "keyValue"))
                }
                Intent.FLAG_ACTIVITY_NO_HISTORY -> startActivity<ClearTopFlagActivity>()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        tv_actID.text = " onNewIntent ——页面ID：${this.toString()}"
    }
}
