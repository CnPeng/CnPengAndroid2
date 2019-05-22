package com.cnpeng.a_17_imageButtonAndZoomButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ZoomControls;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/18.
 * <p>
 * ImageButton和ZoomButton 的使用
 */

public class ImageButtonAndZoomButtonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivandzb);

        ZoomControls zoomControls = (ZoomControls) findViewById(R.id.bt_zoomControls);

        //查看ZoomControls.java 可以得知这两个监听事件
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {      //放大按钮被点击时
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageButtonAndZoomButtonActivity.this, "放大按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageButtonAndZoomButtonActivity.this, "缩小按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
