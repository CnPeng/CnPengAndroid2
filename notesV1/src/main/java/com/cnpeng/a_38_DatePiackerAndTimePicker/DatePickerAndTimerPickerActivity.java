package com.cnpeng.a_38_DatePiackerAndTimePicker;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/28:下午3:29
 * <p>
 * 说明：日期选择器的dialog
 */

public class DatePickerAndTimerPickerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarpicker);

        final EditText et_date = (EditText) findViewById(R.id.et_selectedTime);

        TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker_01);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                et_date.setText("时间："+hourOfDay+"时"+minute+"分");
            }
        });
        
//        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker_01);
//
//        //获取当前日期
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        //给日期选择器设置监听（需要传入当前的年月日信息）
//        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                et_date.setText("您选择的日期是：" + year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
//            }
//        });
    }
}
