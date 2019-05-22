package com.cnpeng.a_12_RadioButtonAndCheckBox;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/16. RadioButton和CheckBox 的基本使用
 */

public class RadioButtonAndCheckBoxActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rbandcb);

        final TextView tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result.setText("性别男");

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String str = checkedId == R.id.rb_male ? "性别男" : "性别女";
                tv_result.setText(str);
            }
        });
    }
}

