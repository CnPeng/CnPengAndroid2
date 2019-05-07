package com.cnpeng.android2.d_mine.a07_item_touch_helper

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.activity_item_touch.*
import org.jetbrains.anko.dip

/**
 * 作者：CnPeng
 * 时间：2019-05-07
 * 功用：
 * 其他：
 */
class ItemTouchActivity : AppCompatActivity() {

    private lateinit var mBinding: com.cnpeng.android2.databinding.ActivityItemTouchBinding
    private lateinit var mActivity: ItemTouchActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_touch)

        initRecyclerView()
    }

    private fun initRecyclerView() {

        rv.layoutManager = LinearLayoutManager(mActivity);
        val adapter = ItemTouchAdapter();
        rv.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //CnPeng 2019-05-07 12:04 直接侧滑并删除
               adapter.removeItem(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(rv)
    }
}

class ItemTouchAdapter : RecyclerView.Adapter<ItemTouchAdapter.ItemTouchHolder>() {

    private val strList: MutableList<String> = mutableListOf()

    init {
        for (i in 0..30) {
            strList.add("这是第 $i 个条目")
        }
    }

    fun removeItem(position: Int) {
        strList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTouchHolder {
        val textView = TextView(parent.context)
        textView.layoutParams = ViewGroup.LayoutParams(-1, parent.context.dip(60))
        textView.gravity = Gravity.CENTER

        return ItemTouchHolder(textView)
    }

    override fun getItemCount(): Int {
        return strList.size
    }

    override fun onBindViewHolder(holder: ItemTouchHolder, position: Int) {
        holder.setData(strList[position])
    }

    inner class ItemTouchHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {

        fun setData(str: String) {
            val tv = itemView as TextView
            tv.text = str
        }
    }
}
