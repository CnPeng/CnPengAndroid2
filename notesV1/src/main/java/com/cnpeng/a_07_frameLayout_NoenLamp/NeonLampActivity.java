package com.cnpeng.a_07_frameLayout_NoenLamp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CnPeng on 2017/1/13.
 * <p>
 * 使用FrameLayout实现霓虹灯效果
 */

public class NeonLampActivity extends AppCompatActivity {
    int[]  bkColors      = new int[]{R.color.colorAccent, R.color.abec06, R.color.fbf302, R.color.colorPrimaryDark};
    int[]  viewIds       = new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
    View[] tvs           = new View[viewIds.length]; //准备一个与ids等长的数组用来接收具体的view
    int    curStartColor = 0;    //统计是第几次更换背景，以及本次更换背景时从哪个颜色开始（要加上tv索引并取余）
    private int MSG_CHANGE_BK = 1;  //消息标识
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (MSG_CHANGE_BK == msg.what) {
                for (int i = 0; i < tvs.length; i++) {  //遍历集合，更换所有tv的背景
                    tvs[i].setBackgroundResource(bkColors[(i + curStartColor) % bkColors.length]);
                }
                curStartColor++;
            }
            // super.handleMessage(msg); //父类空实现，可以直接拿掉
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neonlamp);

        init();
    }

    private void init() {
        //获取具体的view对象添加到数组中
        for (int i = 0; i < viewIds.length; i++) {
            tvs[i] = findViewById(viewIds[i]);
        }

        // 定义计时器执行延时定时任务       
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_CHANGE_BK);    //发送消息去更换背景
            }
        }, 0, 200);  //任务，什么时候开始执行，一次执行多长时间
    }
}

