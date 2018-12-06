package com.cnpeng.android2.b_work.b04_flow_layout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cnpeng.android2.R
import kotlinx.android.synthetic.main.item_flow_rv.view.*

/**
 * 作者：CnPeng
 * 时间：2018/12/6
 * 功用：流式标签的适配器
 * 其他：
 */
class FlowAdapter(dataList: List<String>) : RecyclerView.Adapter<FlowAdapter.ItemHolder>() {
    private var mDataList = dataList
    var mEmsLines: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_flow_rv, parent, false)

        return ItemHolder(itemView, itemView.tv_content)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val contentStr = mDataList[position]
        holder.textView.text = contentStr

        holder.textView.setEms(1)

        if (0 == position % 2) {
            holder.itemView.setBackgroundColor(Color.BLUE)
        } else {
            holder.itemView.setBackgroundColor(Color.RED)
        }
    }


    class ItemHolder(itemView: View, tv: TextView) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = tv
    }

    public fun setEms(emsLines: Int) {
        mEmsLines = emsLines
    }
}