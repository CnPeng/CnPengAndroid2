package com.cnpeng.utils.CustomCalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/18:下午8:28
 * <p>
 * 说明：自定义日历--该自定义使用的是组合系统控件的方式
 */

public class CustomCalendar extends LinearLayout implements CusCalendarRvAdapter.ItemClickListener, 
        CusCalendarRvAdapter.ItemLongClickListener {

    private Calendar             curCalendar;   //当前的日历对象
    private Date                 curDate;       //当前的日期对象
    private Context              context;       //上下文
    //    private List<Date>           datesToShow;   //int型日期的集合，用来初始化适配器
    private List<DateBean>       datesToShow;   //int型日期的集合，用来初始化适配器
    private CusCalendarRvAdapter calendarAdapter;

    public CustomCalendar(Context context) {
        //        super(context);
        this(context, null);
    }

    public CustomCalendar(Context context, AttributeSet attrs) {
        //        super(context, attrs);
        this(context, attrs, 0);
    }

    public CustomCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 将布局文件填充到自定义控件
     */
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.custom_calendar, this);   //源布局，填充到哪里去，是否添加到root根布局中
        //layoutInflater.inflate(R.layout.custom_calendar, this，true);   //最后一个参数要传入true，否则不会显示视图

        curCalendar = Calendar.getInstance();        //获取日历对象

        initHeaderSubject();
        initDateRv();
    }

    /**
     * 初始化展示具体日期的RecyclerView
     */
    private void initDateRv() {
        RecyclerView rv_date = (RecyclerView) findViewById(R.id.rv_cusCalendar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);   //用7列的GV展示内容
        rv_date.setLayoutManager(gridLayoutManager);

        //先把界面展示出去，然后再去组织数据
        datesToShow = new ArrayList<>();
        calendarAdapter = new CusCalendarRvAdapter(context, datesToShow);
        rv_date.setAdapter(calendarAdapter);

        getDatesToShow();

        //设置条目点击监听
        calendarAdapter.setOnItemClickListener(this);
        calendarAdapter.setOnItemLongClickListener(this);
    }

    /**
     * 获取当前月份的日历中需要展示的日期
     */
    private void getDatesToShow() {

        Calendar calendarToShow = (Calendar) curCalendar.clone();
        calendarToShow.set(Calendar.DAY_OF_MONTH, 1);    //将日历调成当月第一天
        int preDaysOfWeek = calendarToShow.get(Calendar.DAY_OF_WEEK) - 1;   //获取当月第一天在它所在的周的位置，-1获得它前面还有几天
        LogUtils.e("preDaysOfWeek", preDaysOfWeek + "");
        int maxCellNum = 7 * 6; //最多需要6行能完全展示当月日历，每行有7天，所以最多有7*6个格

        calendarToShow.add(Calendar.DAY_OF_MONTH, -preDaysOfWeek);//将日期前移到日历中要展示的第一天（也就是说，上月末和本月第一天在同一周的几天中的第一天）

        if (datesToShow.size() > 0) {   //开始while循环之前，先清空已有的数据
            datesToShow.clear();
        }
        while (datesToShow.size() < maxCellNum) {   //遍历获取要展示的日期
            Date dateToShow = calendarToShow.getTime();
            int monthToAdd = dateToShow.getMonth();    //获取要添加到集合中日期所在的月份
            int curMonth = curDate.getMonth();    //获取当前的所展示的日历的月份

            if (datesToShow.size() >= 28 && monthToAdd != curMonth) {//取完当月的数据之后，不再取下个月的数据;因为每个月最少有28天，所以以28为条件
                break;
            }

            DateBean dateBean = new DateBean();
            dateBean.date = dateToShow;
            datesToShow.add(dateBean);  //将每一个要展示的日期对象从日历对象中取出来加入集合
            calendarToShow.add(Calendar.DAY_OF_MONTH, 1);   //每添加一个就后移
        }
        if (calendarAdapter != null) {
            calendarAdapter.updateDatesToShow(datesToShow, curDate);
        }
    }

    /**
     * 初始化最顶部展示年月的条目，并初始化左右按钮的点击事件
     */

    private void initHeaderSubject() {
        setHeaderTextDate();    //设置初始进入页面时的日期

        ImageView iv_preMonth = (ImageView) findViewById(R.id.iv_preMonth);
        iv_preMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curCalendar.add(Calendar.MONTH, -1);  //跳到上一个月
                setHeaderTextDate();
                getDatesToShow();       //触发点击事件之后，同时更新顶部日期和具体的日历
            }
        });
        ImageView iv_nextMonth = (ImageView) findViewById(R.id.iv_nextMonth);
        iv_nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curCalendar.add(Calendar.MONTH, 1);  //跳到下一个月
                setHeaderTextDate();
                getDatesToShow();
            }
        });
    }

    /**
     * 将当前日历中的时间设置到顶部的TextView中
     */
    private void setHeaderTextDate() {
        curDate = curCalendar.getTime();        //获取当前日历中正在展示的日期对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 ");
        String date_header = sdf.format(curDate);  //格式化当前日期为字符串
        TextView tv_header = (TextView) findViewById(R.id.tv_headerCalendar);
        tv_header.setText(date_header);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(context, "条目被点击了+" + position, Toast.LENGTH_SHORT).show();
        //        datesToShow.get(position).isChecked=true;

        for (int i = 0; i < datesToShow.size(); i++) {
            DateBean dateBean = datesToShow.get(i);
            dateBean.isChecked = i == position;
        }

        calendarAdapter.updateDatesToShow(datesToShow, curDate); //通知更新
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(context, "条目被长按了", Toast.LENGTH_SHORT).show();
    }
}
