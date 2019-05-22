package com.cnpeng.a_30_SeekBar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/24.
 * <p>
 * 显示在标题栏上的progressBar
 */

public class SeekBarActivtiy extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        init();
    }

    private void init() {
        final ImageView ivSeekBar = (ImageView) findViewById(R.id.iv_seekbar);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar_01);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度改变的时候，动态的设置图片透明度--使用setImageAlpha()
                ivSeekBar.setImageAlpha(progress);
            } 

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
