package com.cnpeng.b_32_largeImage;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.b_32_largeImage.view.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：CnPeng
 * 时间：2018/6/8 上午8:10
 * 功用：大图展示
 * 说明：该内容不是自己写的，是临时直接拷贝的鸿洋的代码，按照这种实现方式，虽然能加载大图，但是，滑动的时候特别的卡端，并不能说是大图展示的最佳方案
 */
public class LargeImageViewActivity extends AppCompatActivity {
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

        mLargeImageView = (LargeImageView) findViewById(R.id.id_largetImageview);
        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            mLargeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
