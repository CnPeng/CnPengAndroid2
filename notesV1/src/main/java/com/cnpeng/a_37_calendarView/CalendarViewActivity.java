package com.cnpeng.a_37_calendarView;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/28:上午10:56
 * <p>
 * 说明： CalendarView 日历视图的基本使用
 */

public class CalendarViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarview);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView_01);

        //设置日期改变时的监听器
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //此处注意：返回的year 和 day 是准确的，但是 month是从0 开始计数的，所以在使用month时要手动+1。
                Toast.makeText(CalendarViewActivity.this, "您的生日是" + year + (month + 1) + dayOfMonth, Toast
                        .LENGTH_SHORT).show();
            }
        });
    }
}
