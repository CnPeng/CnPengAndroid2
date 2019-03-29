package com.cnpeng.android2.a_book1.chapter7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_coordinator_layout_test.*
import org.jetbrains.anko.toast

class CoordinatorLayoutTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout_test)

        showSnackBar()
    }

    private fun showSnackBar() {
        tv_showSnackBar.setOnClickListener {
            //点击之后显示SnackBar,此时FloatingActionButton上移；SnackBar自动移除之后，FloatingActionButton回到原位置
            Snackbar.make(coor_parent, "SnackBar显示了", Snackbar.LENGTH_SHORT)
                    .setAction("点我试一下") {
                        toast("SnackBar中的超链接被点击了")
                    }
                    .show()
        }
    }
}
