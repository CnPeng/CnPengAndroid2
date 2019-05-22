package com.cnpeng.a_04_ImageBrowser_V1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/12. 
 * 
 * 简易图片浏览器-
 */

public class ImageBrowserActivityV1 extends AppCompatActivity {

    private int i;  //初始化统计变量，记录当前被点击了多少次，间接确定该显示第几张照片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagebrowser_v1);
        
        init();
    }
    private void init() {
        //初始化图片数组
        final int [] images=new int[]{
                R.drawable.act_attentioned,
                R.drawable.hot_green,
                R.drawable.icon,
                R.drawable.main_pop_school,
                R.drawable.mainfocus,
                R.drawable.student_greet_image};
        
        final ImageView iv= (ImageView) findViewById(R.id.iv_browserV1);
        iv.setImageResource(images[i]); 
        
        //点击事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(images[++i %images.length]);//重点是这里，实现无限循环
            }
        });
    }
}
