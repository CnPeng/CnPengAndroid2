package com.cnpeng.utils.CustomCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.Date;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/18:下午9:47
 * <p>
 * 说明：自定义日历控件中，用来展示当月中日期的适配器
 */

class CusCalendarRvAdapter extends RecyclerView.Adapter<CusCalendarRvAdapter.DateHolder> {
    Context        context;
    List<DateBean> datesToShow;
    Date           curDate;   //当前日历正在展示的月份（不一定是当前月份）

    /**
     * 自定义更新数据的方法，切换月份时需要使用
     */
    public void updateDatesToShow(List<DateBean> list, Date curDate) {
        this.datesToShow = list;
        this.curDate = curDate;
        notifyDataSetChanged(); //刷新全部数据
    }

    public CusCalendarRvAdapter(Context context, List<DateBean> datesToShow) {
        this.context = context;
        this.datesToShow = datesToShow;
    }

    @Override
    public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_customcalendar, parent, false);
        return new DateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DateHolder holder, final int position) {
        //1 展示整体的日历数据
        DateBean dateBean = datesToShow.get(position);

        Date date = dateBean.date;
        int monthOfDate = date.getMonth();  //获取条目中日期对象所在的月份
        int curMonth = curDate.getMonth();  //获取当前日历中正在展示的月份
        if (monthOfDate == curMonth) {      //月份一致，展示数据
            int dayOfMonth = date.getDate();
            holder.tv_dateItem.setText(String.valueOf(dayOfMonth));
        } else {    //月份不一致，用空串替代数据，如果不加这句，即便有了上面的判断，还是会显示上月的数据。没想明白为啥
            holder.tv_dateItem.setText("");
        }

        //2 个性化当天(这里拿到的是真实的当天的日期),背景标红，字体标白
        boolean isToday = getIsToday(date);
        if (isToday) {
            holder.tv_dateItem.setBackgroundResource(R.color.f7c653);
            holder.tv_dateItem.setTextColor(context.getResources().getColor(R.color.ffffff));
        }

        //3 对外暴露条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                    //                    itemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(v, holder.getLayoutPosition());
                    //                    itemLongClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });

        //4 个性化被点击条目
        if (dateBean.isChecked) {   //选中时设置为灰色
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.e7e7e6));
        } else {    //未选中时设置为透明，此处必须设置，如果不设置的话，会出现选中28 时同时改变了28 和2 的背景色的问题（具体的不一定就是28和2 ，但问题就是本来只选了一个，但是有两个变了背景色）
            holder.itemView.setBackgroundResource(android.R.color.transparent);
        }
    }

    /**
     * 判断某个条目是都是当前时间的当天
     */
    private boolean getIsToday(Date date) {
        int yearOfDate = date.getYear();//获取条目中日期对象所在的年
        int dayOfDate = date.getDate();//获取条目中日期对象所在的天
        int monthOfDate = date.getMonth();  //获取条目中日期对象所在的月份
        Date today = new Date();
        int month_today = today.getMonth();
        int year_today = today.getYear();
        int day_today = today.getDate();
        return day_today == dayOfDate && month_today == monthOfDate && year_today == yearOfDate;
    }

    @Override
    public int getItemCount() {
        return datesToShow.size();
    }

    class DateHolder extends RecyclerView.ViewHolder {
        TextView tv_dateItem;

        DateHolder(View itemView) {
            super(itemView);
            tv_dateItem = (TextView) itemView.findViewById(R.id.tv_cusCalendarCell);
        }
    }

    /**
     * RV的条目点击事件接口
     */
    public interface ItemClickListener {    //定义点击事件监听器
        public void onItemClick(View view, int position);   //内部对外暴露响应点击事件的view和position，具体事件由外部处理
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {  //对外暴露设置点击监听的方法
        this.itemClickListener = itemClickListener;
    }

    ItemClickListener itemClickListener;

    /**
     * RV条目的长按事件接口
     */
    public interface ItemLongClickListener {    //定义点击事件监听器
        public void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    ItemLongClickListener itemLongClickListener;
}
