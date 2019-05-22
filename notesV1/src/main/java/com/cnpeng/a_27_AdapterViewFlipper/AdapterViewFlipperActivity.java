package com.cnpeng.a_27_AdapterViewFlipper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/22.
 * <p>
 * 使用AdapterViewFlipper 实现自动播放的图片库
 */

public class AdapterViewFlipperActivity extends AppCompatActivity implements View.OnClickListener {
    private AdapterViewFlipper adapterViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapterviewflipper);

        init();
    }

    private void init() {
        Button btPre = (Button) findViewById(R.id.bt_previous);
        Button btNext = (Button) findViewById(R.id.bt_next);
        Button btAuto = (Button) findViewById(R.id.bt_auto);

        btAuto.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btPre.setOnClickListener(this);

        int[] pics = new int[]{R.drawable.gongfuxiongmao, R.drawable.daomeixiong, R.drawable.xiaopohai, R.drawable
                .pikaqiu, R.drawable.act_attentioned, R.drawable.cus_switch, R.drawable.hot_green};

        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapterViewFlipper);
        MyAdapterViewFlipperAdapter adapter = new MyAdapterViewFlipperAdapter(this, pics);
        adapterViewFlipper.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_previous:  //上一页
                adapterViewFlipper.showPrevious();  //展示上一页
                adapterViewFlipper.stopFlipping();  //停止自动播放
                break;
            case R.id.bt_auto:  //开启自动播放
                adapterViewFlipper.startFlipping(); //开启自动播放
                break;
            case R.id.bt_next:  //下一页
                adapterViewFlipper.showNext();  //展示下一页
                adapterViewFlipper.stopFlipping();
                break;
        }
    }
}
