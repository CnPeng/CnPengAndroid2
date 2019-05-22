package com.cnpeng.a_39_DatePickerDialogAndTimerPickerDialg;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.Calendar;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/28:下午5:58
 * <p>
 * 说明：DatePickerDialogAndTimerPickerDialog的基本使用
 */

public class DatePickerDialogAndTimerPickerDialogActvitiy extends AppCompatActivity implements View.OnClickListener {

    private Button bt_showTPD;
    private Button bt_showDPD;
    Context context = this;
    private int      year;
    private int      month;
    private int      day;
    private TextView tv_date;
    private TextView tv_time;
    private int      hour;
    private int      minute;
    private Button   bt_showDPD_year_month;
    private TextView tv_showDPD_year_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpd_tpd);

        bt_showDPD = (Button) findViewById(R.id.bt_showDPD);
        bt_showTPD = (Button) findViewById(R.id.bt_showTPD);
        bt_showDPD_year_month = (Button) findViewById(R.id.bt_showDPD_Year_Month);

        bt_showDPD.setOnClickListener(this);
        bt_showTPD.setOnClickListener(this);
        bt_showDPD_year_month.setOnClickListener(this);

        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_showDPD_year_month = (TextView) findViewById(R.id.tv_showDPD_year_month);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_showDPD:   //展示日期选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener
                        () {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_date.setText("日期是：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;
            case R.id.bt_showDPD_Year_Month:
                //原文参考自：http://blog.csdn.net/lintcgirl/article/details/50476684
                //使用这种方式 在展示的时候只有 年和月，但是实际的日历对象中，依旧有天，只是天没有展示在选择界面！！
                DatePickerDialog dlg = new DatePickerDialog(new ContextThemeWrapper
                        (DatePickerDialogAndTimerPickerDialogActvitiy.this, android.R.style
                                .Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_showDPD_year_month.setText("日期是：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    }
                }, year, month, day) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        LinearLayout mSpinners = (LinearLayout) findViewById(getContext().getResources()
                                .getIdentifier("android:id/pickers", null, null));
                        if (mSpinners != null) {
                            NumberPicker mMonthSpinner = (NumberPicker) findViewById(getContext().getResources()
                                    .getIdentifier("android:id/month", null, null));
                            NumberPicker mYearSpinner = (NumberPicker) findViewById(getContext().getResources()
                                    .getIdentifier("android:id/year", null, null));
                            mSpinners.removeAllViews();

                            if (mYearSpinner != null) {
                                mSpinners.addView(mYearSpinner);
                            }
                            if (mMonthSpinner != null) {
                                mSpinners.addView(mMonthSpinner);
                            }

                        }
                        View dayPickerView = findViewById(getContext().getResources().getIdentifier("android:id/day",
                                null, null));
                        if (dayPickerView != null) {
                            dayPickerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        super.onDateChanged(view, year, month, day);
                        setTitle("请选择日期");  ////如果没有通过dlg.setTitle() 设置标题，这句代码不生效；如果有了dlg.setTitle(),如果不在此处设置，title处将展示的是选中的年月日周几。
                    }
                };
                dlg.setTitle("请选择日期");////初始化时的title，设置了之后，onDateChanged中的title才会生效
                dlg.show();


                break;
            case R.id.bt_showTPD:   //展示时间选择器
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener
                        () {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv_time.setText("时间是：" + hourOfDay + "时" + minute + "分");
                    }
                }, hour, minute, true);
                timePickerDialog.show();
                break;
        }
    }
}
