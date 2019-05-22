package com.cnpeng.a_10_textView_01;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

/**
 * Created by CnPeng on 2017/1/16. TextView 的基本使用
 */

public class TextView01Activtiy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview01);

        final CheckedTextView ctv = (CheckedTextView) findViewById(R.id.ctv_01);
        ctv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean preStatus = ctv.isChecked();  //原状态
                LogUtils.e("点击前的选中状态",preStatus+"");
                ctv.setChecked(!preStatus);     //状态取反

                if (preStatus) {   //如果原先已经选中，点击后变成非选中。点击隐藏图标
                    ctv.setCheckMarkDrawable(null);
                    ctv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    ctv.setCheckMarkDrawable(R.drawable.icon);
                    ctv.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
    }
}
