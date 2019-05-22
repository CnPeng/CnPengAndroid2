package com.cnpeng.a_09_gridLayout_caculater;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/13.
 * <p>
 * 使用网格布局实现计算器界面
 */

public class CaculaterActivity extends AppCompatActivity {
    String[] chars = new String[]{"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", ".", "0", "=", "+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculater);
        init();

    }

    private void init() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gl_caculater);
        for (int i = 0; i < chars.length; i++) {
            Button btn = new Button(this);
            btn.setText(chars[i]);
            btn.setTextSize(40);    //单位px
            btn.setPadding(10, 35, 10, 35);

            GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2); //放在哪一行
            GridLayout.Spec colSpec = GridLayout.spec(i % 4);   //放在哪一列
            //GridLayout.LayouParams(spec,spec) 默认宽高是包裹内容，所以，看到的结果将是，最后一行和最后一列是拉伸的。
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            params.setGravity(Gravity.FILL);   //决定了最后一行和最后一列的拉伸

            gridLayout.addView(btn, params);
        }
    }
}
