package com.cnpeng.android2.b_work.b05_open_lamp

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.utils.cameraManager
import kotlinx.android.synthetic.main.activity_open_or_close_lamp.*

class OpenOrCloseLampActivity : AppCompatActivity() {
    var mIsLampOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_or_close_lamp)

        initClickEvent()
    }

    private fun initClickEvent() {
        bt_lamp.setOnClickListener {

            // TODO: CnPeng 2019/1/11 4:30 PM 最好是检测一下是否有摄像头

            if (23 <= Build.VERSION.SDK_INT) {
                //CnPeng 2019/1/11 4:17 PM API23 之后可以使用Camera2包下的 CameraManager. torch [tɔːtʃ] 火炬火把
                try {
                    if (mIsLampOpen) {
                        //CnPeng 2019/1/11 4:32 PM 第一个参数取值"0"/"1" ，其他值会导致手电筒无效
                        cameraManager.setTorchMode("1", false)
                        mIsLampOpen = false
                        bt_lamp.text = "打开手电筒"
                    } else {
                        cameraManager.setTorchMode("1", true)
                        mIsLampOpen = true
                        bt_lamp.text = "关闭手电筒"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                // TODO: CnPeng 2019/1/11 4:22 PM API 23之前开启和关闭Lamp

            }
        }
    }
}