package com.cnpeng.android2.d_mine.a07_item_touch_helper

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cnpeng.android2.R
import com.cnpeng.android2.databinding.ActivityItemTouchBinding
import com.cnpeng.android2.databinding.ItemItemTouchBinding
import kotlinx.android.synthetic.main.activity_item_touch.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.dip
import org.jetbrains.anko.error

/**
 * 作者：CnPeng
 * 时间：2019-05-07
 * 功用：实现仿 QQ 的侧滑删除
 * 其他：
 * [Android侧滑-RecyclerView轻松实现高效的侧滑菜单](https://juejin.im/entry/5b90cf67f265da0af774f72f)
 * [RecycleView实现QQ侧滑效果](https://blog.csdn.net/u012292247/article/details/79772973)
 */
class ItemTouchActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityItemTouchBinding
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

        val itemTouchHelper = ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            val delBtWidth = dip(70)
            /**
             * 当前滑动距离
             */
            var scrollDistance = 0;
            var isNeedRecover = true;
            var isCanScrollLeft = false;
            var isCanScrollRight = false;


            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //CnPeng 2019-05-07 12:04 直接侧滑并删除
                //                adapter.removeItem(viewHolder.adapterPosition)
                //                swipeDirection=direction;
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                AnkoLogger("dX移动距离").error { dX }
                AnkoLogger("actionState").error { actionState }
                AnkoLogger("isCurrentlyActive").error { isCurrentlyActive }


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val currentScroll = viewHolder.itemView.scrollX;
                    if (dX < 0 && isCanScrollLeft && currentScroll <= delBtWidth) {
                        val dX2: Int = if (Math.abs(dX) <= delBtWidth) dX.toInt() else -delBtWidth
                        if (!isNeedRecover) {
                            var newScroll = delBtWidth + dX2
                            newScroll = if (newScroll <= currentScroll) currentScroll else newScroll
                            viewHolder.itemView.scrollTo(newScroll, 0);
                        } else {
                            viewHolder.itemView.scrollTo(-dX2, 0);
                            scrollDistance = dX2;
                        }

                    } else if (dX > 0 && isCanScrollLeft) {
                        //可以左滑的情况下往右滑，恢复item位置
                        viewHolder.itemView.scrollTo(0, 0);
                        scrollDistance = 0;

                    } else if (dX > 0 && isCanScrollRight && currentScroll >= 0) {
                        var dX3 = 0
                        if (!isNeedRecover) {
                            dX3 = if (Math.abs(dX) <= Math.abs(currentScroll)) dX.toInt() else currentScroll;
                            viewHolder.itemView.scrollTo(dX3, 0);
                        } else {
                            dX3 = if (Math.abs(dX) <= delBtWidth) dX.toInt() else delBtWidth
                            viewHolder.itemView.scrollTo(delBtWidth - dX3, 0);
                            scrollDistance = dX3;
                        }
                    } else if (dX < 0 && isCanScrollRight) {
                        //可以右滑的情况下往左滑，恢复item位置
                        viewHolder.itemView.scrollTo(delBtWidth, 0);
                        scrollDistance = delBtWidth;
                    }

                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

                //                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //                    // 向左侧滑
                //                    viewHolder.itemView.scrollTo(
                //                            if (Math.abs(dX) <= delBtWidth) -dX.toInt() else delBtWidth, 0);
                //
                //                } else {
                //                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                //                }
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    isNeedRecover = true;
                    scrollDistance = 0;
                    isCanScrollLeft = false;
                    isCanScrollRight = false;
                    if (viewHolder!!.itemView.scrollX > 0)
                        isCanScrollRight = true;
                    else
                        isCanScrollLeft = true;
                } else {
                    //ACTION_UP会首先进入这里，然后再执行recover animation
                    if (Math.abs(scrollDistance) >= delBtWidth / 2) {
                        isNeedRecover = false;
                    }
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)

                if (viewHolder.itemView.scrollX >= delBtWidth / 2)
                    viewHolder.itemView.scrollTo(delBtWidth, 0);
                else
                    viewHolder.itemView.scrollTo(0, 0);

            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                //设置滑动删除最大距离，1.5代表是itemview宽度的1.5倍,目的是不让它删除
                return 1.5f
            }

            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                //设置滑动速度，目的是不让它进入onSwiped
                return defaultValue * 100;
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
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemItemTouchBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_item_touch, parent, false)

        return ItemTouchHolder(itemBinding.root, itemBinding)
    }

    override fun getItemCount(): Int {
        return strList.size
    }

    override fun onBindViewHolder(holder: ItemTouchHolder, position: Int) {
        holder.itemBinding.tvDesc.text = (strList[position])
    }

    inner class ItemTouchHolder(itemView: View, val itemBinding: ItemItemTouchBinding) : RecyclerView.ViewHolder(itemView) {

    }
}
