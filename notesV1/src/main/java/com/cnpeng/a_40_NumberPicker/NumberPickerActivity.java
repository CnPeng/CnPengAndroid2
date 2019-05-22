package com.cnpeng.a_40_NumberPicker;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/1:上午9:01
 * <p>
 * 说明：NumberPicker数值选择器的使用
 */

public class NumberPickerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numberpicker);

        final TextView textView= (TextView) findViewById(R.id.tv_numberPicker);
        
        NumberPicker numberPicker= (NumberPicker) findViewById(R.id.numberPicker01);
        numberPicker.setMaxValue(50);   //设置最大值
        numberPicker.setMinValue(0);    //设置最小值
        numberPicker.setValue(25);      //设置默认值
        
        //设置监听器
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textView.setText("当前的数值是："+newVal);
            }
        });
    }
}
