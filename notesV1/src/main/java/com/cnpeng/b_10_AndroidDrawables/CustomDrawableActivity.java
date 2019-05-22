package com.cnpeng.b_10_AndroidDrawables;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.c_00_CommonCustomView.CustomRectRoundDrawable;
import com.cnpeng.c_00_CommonCustomView.CustomRoundDrawable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/18:下午5:56
 * <p>
 * 说明：用来展示自定义的圆角和圆形drawble
 */

public class CustomDrawableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdrawable);

        ImageView iv_cusDrawable = (ImageView) findViewById(R.id.iv_cusDrawable);
        iv_cusDrawable.setImageDrawable(new CustomRoundDrawable(this, R.drawable.daomeixiong));

        ImageView iv_cusDrawable2 = (ImageView) findViewById(R.id.iv_cusDrawable2);
        iv_cusDrawable2.setImageDrawable(new CustomRectRoundDrawable(this, R.drawable.daomeixiong));
    }
}
