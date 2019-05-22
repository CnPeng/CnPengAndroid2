package com.cnpeng.a_15_chronometer;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/17.
 * <p>
 * Chronometer计时器的使用
 */

public class ChronometerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        final Button bt_start = (Button) findViewById(R.id.bt_startChronometer);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime()); //获取当前时间作为基准时间
                // chronometer.setFormat("00:%s");     //以 00:00:00 时分秒的形式展示计时，后面的 %S 不能省略
                // chronometer.setFormat("0%s");       //以 000：00  分秒的形式展示计时
                chronometer.setFormat("%s");           //以 00：00 分秒的形式展示计时，默认就是这种格式
                chronometer.start();    //开始计时

                bt_start.setEnabled(false);     //调用这个继承自TextView的setEnable方法后，设置false 将变成灰色不可点击状态！！
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - chronometer.getBase() > 5 * 1000) {   //当前时间减去基准时间，超过2s停止计时
                    chronometer.stop();
                    bt_start.setEnabled(true);
                }
            }
        });
    }
}
