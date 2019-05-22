package com.cnpeng.android2.d_mine.a05_extract_rv_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * 作者：CnPeng
 * 时间：2018/12/28
 * 功用：通用的RvAdapter
 * 其他：
 *
 * @param itemLayoutId 条目视图对应的 layoutID
 * @param itemList 条目bean集合。考虑到更新的问题，所以定义为 MutableList
 * @param initItemView 函数参数，由外部提供具体实现. 这是kotlin高阶函数的表现形式
 */
class CommonRvAdapter<T>(var itemLayoutId: Int,
                         private var itemList: MutableList<T>,
                         private val initItemView: (View, T) -> Unit) : RecyclerView.Adapter<CommonRvAdapter.BaseItemHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonRvAdapter.BaseItemHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(itemLayoutId, parent, false)
        return BaseItemHolder(itemView, initItemView)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonRvAdapter.BaseItemHolder<T>, position: Int) {
        holder.setDataToItem(itemList[position])
    }

    /**
     * CnPeng 2018/12/28 5:36 PM
     * 功用：
     * 说明：
     * - 因为setDataToItem的参数为条目Bean，类型不明确，所以定义为T，所以BaseItemHolder就必须是一个模板类，所以 CommonRvAdapter 也必须是一个模板类
     * - 不能声明为inner，否则 CommonRvAdapter 中引用该Holder的地方会报错，提示：One type argument excepted for class BaseItemHolder<T>
     * - 此处实现了LayoutContainer接口，那么 CommonRvAdapter 构造中传入的方法内部就可以直接通过 view.子View的ID 获取子View对象
     */
    class BaseItemHolder<in T>(override val containerView: View, val initItem: (View, T) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setDataToItem(itemBean: T) {
            initItem(containerView, itemBean)
        }
    }

    /**
     * CnPeng 2018/12/28 6:20 PM
     * 功用：更新列表中的数据
     * 说明：
     */
    fun updateData(newDataList: List<T>) {
        itemList.clear()
        itemList.addAll(newDataList)
        notifyDataSetChanged()
    }
}