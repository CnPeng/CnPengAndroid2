package com.cnpeng.a_29_progressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CnPeng on 2017/1/24.
 * <p>
 * ProgressBar的使用
 */

public class ProgressBarActivity extends Activity {

    private int count = 0;

    private ProgressBar pbHAndroid;
    private ProgressBar pbHAppcompat;
    private Timer       incrementTimer;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {  //非空判断防止空指针
                pbHAndroid.setProgress(count);
                pbHAppcompat.setProgress(count);
                LogUtils.e("进度已经被设置", "当前进度：" + count);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        init();
    }

    private void init() {
        pbHAndroid = (ProgressBar) findViewById(R.id.pb_h_android);
        pbHAppcompat = (ProgressBar) findViewById(R.id.pb_h_appcompat);

        //开启定时任务，模拟耗时操作
        incrementTimer = new Timer();
        incrementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (count < 100) {
                    Message message = handler.obtainMessage();
                    message.obj = ++count;    //消息体,必须要将这个count 发送出去，否则，count是不会变得
                    handler.sendMessage(message);
                    LogUtils.e("执行定时任务", "" + count);
                }
            }
        }, 10, 50);      // schedule() 方法的三个参数含义：定时任务，什么时候开始执行，执行频率


        //点击按钮之后，回退进度
        Button btSubsProgress = (Button) findViewById(R.id.bt_subsProgress);
        btSubsProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击之后，先关闭上一个定时器
                if (incrementTimer != null) {
                    incrementTimer.cancel();
                    incrementTimer = null;
                }

                //开启新的递减的计时器
                Timer subsTimer = new Timer();
                subsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (count > 0) {
                            Message msg = handler.obtainMessage();
                            msg.obj = --count;
                            handler.sendMessage(msg);
                        }
                    }
                }, 10, 50);
            }
        });
    }
}
