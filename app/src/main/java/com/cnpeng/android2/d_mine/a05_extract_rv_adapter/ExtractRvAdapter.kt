package com.cnpeng.android2.d_mine.a05_extract_rv_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cnpeng.android2.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_extract.view.*

/**
 * 作者：CnPeng
 * 时间：2018/12/28
 * 功用：
 * 其他：
 */
class ExtractRvAdapter(private var itemList: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_extract, parent, false)
        return ExtractViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExtractViewHolder).setDataToItem(itemList[position])
    }

    /**
     * - 《Kotlin从0到精通Android开发》在7.1.4中说此处不要加 inner 前缀，否则报错“java.lang.NoSuchMethodError: No virtual method _$_findCachedViewByID，实测未复现。
     * - 实现LayoutContainer接口后，主构造函数中的参数是自动加进去的——不能改变参数名称！！！默认自动加入时View后面会有？，但与Rv.ViewHolder构造冲突，去掉即可。
     * - 实现LayoutContainer的目的是省略findViewByID
     * - 要想使用LayoutContainer也需要从module的gradle的android节点中增加：androidExtensions {experimental = true}——启用@Parcelize 注解时也需要这个节点
     */
    inner class ExtractViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun setDataToItem(itemBean: String) {
            //因为实现了 LayoutContainer 接口，所以，不用findViewById
            itemView.tv_extractItemDesc.text = itemBean
        }
    }
}