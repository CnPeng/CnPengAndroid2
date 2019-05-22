package com.cnpeng.a_44_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.a_40_NumberPicker.NumberPickerActivity;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/14:上午8:26
 * <p>
 * 说明：Notification通知的基本使用
 */

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
    }

    private void init() {
        Button button = (Button) findViewById(R.id.bt_sendNotification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击按钮之后发送通知
                Context context = NotificationActivity.this;
                //1 获取notificationManager对象
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //2 创建通知被点击之后的事件
                Intent intent = new Intent(context, NumberPickerActivity.class);
                PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

                //3 创建通知对象并设置通知内容
                Notification notification = new Notification.Builder(context).setSmallIcon(R.drawable.hot_green)   
                        //显示在状态栏的小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.daomeixiong))     
                        //下拉后在NotificationDrawer中展示的大图标
                        .setContentTitle("这是通知的标题").setContentText("这是通知的文本内容").setTicker("这是显示在状态栏的ticker")
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)   //使用默认样式的闪光灯和声音
                        .setWhen(System.currentTimeMillis())        //时间戳
                        .setVibrate(new long[]{50, 100, 150, 200, 250, 300})     //自定义震动频率
                        .setAutoCancel(true)    //触摸之后自动消失
                        .setContentIntent(pi).build();
                //4 发送通知(第一个参数通知的标识码，第二个参数通知对象)
                nm.notify(0, notification);
            }
        });
    }
}

