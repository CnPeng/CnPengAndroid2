package com.cnpeng.android2.d_mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cnpeng.android2.R
import com.cnpeng.android2.databinding.ActivityTempBinding
import kotlinx.android.synthetic.main.activity_temp.*
import java.io.File

class TempActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityTempBinding = DataBindingUtil.setContentView(this, R.layout.activity_temp)

        binding.tvTemp.text = "啥FUCK"

        initClickEvent()
    }

    private fun initClickEvent() {
        tv_getFile.setOnClickListener {

            val fileNames: MutableList<String> = mutableListOf()

            var fileNamesStr = ""

            val mPath = "/Users/cnpeng/CnPeng/00_LearningNotes"
            val fileTree: FileTreeWalk = File(mPath).walk()
            fileTree.maxDepth(8) //需遍历的目录层级为1时，表示无需检查子目录
                    .filter { it.isFile } //只挑选文件，不处理文件夹
                    .filter { it.extension == "md" } //选择扩展名为txt的文本文件
                    .forEach {
                        //循环处理符合条件的文件
                        fileNames.add(it.name)
                        fileNamesStr = "$fileNamesStr || ${it.name}"
                    }

            tv_temp.text = fileNamesStr
        }
    }
}





