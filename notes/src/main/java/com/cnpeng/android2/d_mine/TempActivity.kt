package com.cnpeng.android2.d_mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cnpeng.android2.R
import com.cnpeng.android2.databinding.ActivityTempBinding

class TempActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityTempBinding = DataBindingUtil.setContentView(this, R.layout.activity_temp)

        binding.tvTemp.text = "啥FUCK"
    }


    fun getMax(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    fun cusFunc(){
        val a=getMax(3,5)
    }
}





